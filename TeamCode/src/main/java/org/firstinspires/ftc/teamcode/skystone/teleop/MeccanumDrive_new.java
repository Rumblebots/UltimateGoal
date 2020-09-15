 package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.skystone.RobotCore.Utils;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Sensors.Sensors;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Servos;

import java.util.HashMap;
import java.util.Map;

@TeleOp(name="New Meccanum Drive", group="default")
public class MeccanumDrive_new extends OpMode
{
    public TouchSensor extenderlimit;
    //TODO: Make sure the capstone toggle serve doesn't open on init
    int cpos = 0;
    int rpos = 0;
    private enum Extender
    {
        In,
        Out,
    }
    private boolean inTrans = false;
    private Extender extender = Extender.In;
    private Motors_Drive driveMotors = new Motors_Drive();
    private Motors_Drive.TeleOp driveMotors_TeleOp = driveMotors.new TeleOp();
    private Motors_Drive.Common driveMotors_Common = driveMotors.new Common();

    private Motors_Aux auxMotors = new Motors_Aux();
    private Motors_Aux.TeleOp auxMotors_TeleOp = auxMotors.new TeleOp();
    private Motors_Aux.TeleOpEncoders auxMotors_TeleOpEncoders = auxMotors.new TeleOpEncoders();

    private Servos servos = new Servos();
    private Servos.TeleOp servos_TeleOp = servos.new TeleOp();

    private Sensors sensors = new Sensors();

    private Utils.Toggle grabberToggle = new Utils().new Toggle(false);
    private Utils.Toggle extenderToggle = new Utils().new Toggle(false);
    private Utils.Toggle foundationToggle = new Utils().new Toggle(true);
    private Utils.Toggle capstoneToggle = new Utils().new Toggle(false);
    private Utils.Toggle extensionToggle = new Utils().new Toggle(false);

    private Utils.Shifter lifterShifter = new Utils().new Shifter(1, 9, false);

    /* TODO correctly set up encoder values for certain heights
     * Adjust the second integer value in the following hashmap
     * This should correspond to the encoder values of the stacker while at certain positions.
     * 0 position (default position, 0 encoder value) is where the first block should be placed
     * 1 is technically the second block, 2 is technically the third block... etc.
     */
    private Map<Integer, Integer> Levels = new HashMap<Integer, Integer>()
    {{
        put(0, 000); // 0 position, resting position, mag limit switch active ** FIRST BLOCK IS HERE **
        put(1, 450); // 1 block (second block)
        put(2, 1180); // 2 blocks (third block)
        put(3, 1754); // 3 blocks (fourth block)
        put(4, 2417); // 4 blocks (fifth block)
        put(5, 3181); // 5 blocks (sixth block)
        put(6, 3735); // 6 blocks (seventh block)
        put(7, 4495); // 7 blocks (eighth block)
        put(8, 4934); // 8 blocks (ninth block)
        put(9, 10000); // Encoder value is intentionally out of range to have the lifter extend fully
                       // you probably won't ever get to this gear because it's out of range anyway
                       // TODO find out the highest gear the extender can get to with the two stages
    }};

    private int extenderMode = 0;
    private float motorSpeedDiv = 2;

    /**
     * Init function
     * Sets everything we'll need later up
     */
    public void init ()
    {
        driveMotors.init(hardwareMap);
        auxMotors.init(hardwareMap);
        servos.teleOpInit(hardwareMap);
        sensors.init(hardwareMap);
        auxMotors_TeleOpEncoders.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("current gear", lifterShifter.CurrentGear);
        telemetry.addData("current encoder pos", auxMotors_TeleOpEncoders.GetCurrentPos());
        auxMotors_TeleOpEncoders.ResetEncoders();
        servos.leds.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        extenderlimit = hardwareMap.touchSensor.get("extenderlimit");
    }

    /**
     * Loop function
     * Has everything that's in TeleOp
     * Structure:
     * - Values
     *   - Joystick values
     *   - Sensor values
     * - Drive Motors
     *   - Drive Motors
     *     - Telemetry for drive motors
     *   - Inverted Motors (for stacking)
     * - Gamepad 2 controls
     *   - Stacker
     *     - Manual stacking controls
     *     - Encoder-based stacking
     *     - Telemetry for stacker
     *       - Encoder Position
     *       - Encoder Target Position
     *       - Current Gear
     *   - Intake
     *     - Telemetry for intake
     *       - Current intake power
     *       - Current intake servo power (?)
     *   - Extender
     *     - Manual controls
     *     - Automatic extension / retraction
     *     - Telemetry for extender
     *       - Which magnets have been tripped
     *       - Current servo power
     *   - Gripper
     *     - Automatic sensor-based open / close
     *     - Manual open / close (mapped to A)
     *     - Telemetry for gripper
     *       - Servo position
     *   - Foundation Grabber
     *     - Raise / lower foundation grabber
     *     - Telemetry for servo position
     *   - Capstone Toggle
     *     - Release the capstone
     * - Autonomous controls in TeleOp
     *   - Internal color sensors
     *     - Move the block inside with a servo
     *     - Telemetry on sensor values
     *   - TODO Automatic Alignment
     *     - Press a button and automatically align
     */
    public void loop ()
    {
        float gamepad1_leftStickY = gamepad1.left_stick_y;
        float gamepad1_leftStickX = gamepad1.left_stick_x;
        float gamepad1_rightStickX = gamepad1.right_stick_x;
        Map<String, Integer> s2Vals = sensors.getInside2SensorVals();
        Map<String, Integer> s1Vals = sensors.getInside1SensorVals();

        {
            // Gamepad 1 control
            {
                // Drive motors
                if (gamepad1.right_trigger > 0)
                {
                    motorSpeedDiv = 1;
                }
                else if (gamepad1.left_trigger > 0)
                {
                    motorSpeedDiv = 4;
                }
                else
                {
                    motorSpeedDiv = 2;
                }

                int curve = 3;
                driveMotors_TeleOp.UpdateMotors(
                        -gamepad1_leftStickY,
                        gamepad1_leftStickX,
                        gamepad1_rightStickX,
                        motorSpeedDiv,
                        curve
                );
            }

            {
                // Dpad drive controls (useful for backwards stacking)
                if (gamepad1.dpad_up)
                {
                    driveMotors_Common.MeccanumDirection("BACKWARD", 0.4);
                }
                else if (gamepad1.dpad_left)
                {
                    driveMotors_Common.MeccanumDirection("RIGHT", 0.4);
                }
                else if (gamepad1.dpad_down)
                {
                    driveMotors_Common.MeccanumDirection("FORWARD", 0.4);
                }
                else if (gamepad1.dpad_right)
                {
                    driveMotors_Common.MeccanumDirection("LEFT", 0.4);
                }
            }
        }




        {
            // Gamepad 2 controls
            {
                // Move stacker / lifter
                if (sensors.lifterTouchSensor.IsSensorActive()) extenderMode = 0;
                if (gamepad2.dpad_down)
                {
                    extenderMode = 1;
                    lifterShifter.CurrentGear = 1;
                }
                if (gamepad2.right_trigger > 0 && !gamepad2.y) // up
                {
                    auxMotors_TeleOp.SetStackerDirection(-gamepad2.right_trigger);
                    extenderMode = 0;
                }
                else if (gamepad2.left_trigger > 0 && !gamepad2.y) // down
                {
                    if (!sensors.lifterTouchSensor.IsSensorActive()) auxMotors_TeleOp.SetStackerDirection(gamepad2.left_trigger);
                    else
                    {
                        auxMotors_TeleOp.SetStackerDirection(0);
                        extenderMode = 0;
                    }
                    extenderMode = 0;
                    telemetry.addData("SACTIVE", sensors.lifterTouchSensor.IsSensorActive());
                }
                else if (extenderMode == 1)
                {
                    auxMotors_TeleOp.SetStackerDirection(1.0);
                }
                else
                {
                    if (!gamepad2.y) auxMotors_TeleOp.SetStackerDirection(0);
                }

                {
                    // Automatic stacker & lifter controls
                    /* TODO finish adding auto-stacking-levels
                     * Finish implementing auto-stacking
                     * Needs to be tweaked so it moves to target position
                     * Add functionality where it speeds up / slows down the stacker as it
                     * approaches the target value in order to get exactly where it needs to go
                     * See AdjustMotorPower() in Motors_Aux's TeleOpEncoders class for more information.
                     */
                    {
                        // Gear Shifting
                        if (gamepad2.dpad_up)
                        {
                            lifterShifter.Shift(Utils.Shift.Up);
                            auxMotors_TeleOpEncoders.SetTargetPos(Levels.get(lifterShifter.CurrentGear));
                        }
                        else if (gamepad2.dpad_down)
                        {
                            lifterShifter.Shift(Utils.Shift.Down);
                            auxMotors_TeleOpEncoders.SetTargetPos(Levels.get(lifterShifter.CurrentGear));
                        }
                        else lifterShifter.Released();
                    }

                    {
                        // Moving the lifter
                        if (gamepad2.y) auxMotors_TeleOpEncoders.AdjustMotorPower(cpos);
                    }
                }
                telemetry.addData("current gear", lifterShifter.CurrentGear);
                telemetry.addData("current encoder pos", cpos);
                telemetry.addData("target pos", auxMotors_TeleOpEncoders.GetTargetPos());
                telemetry.addData("stacker motor power", auxMotors.stackerVertical1.getPower());
            }

            {
                // Intake controls
                if (gamepad2.right_bumper)
                {
                    auxMotors_TeleOp.SetIntakeDirection(1.0);
                    servos_TeleOp.SetIntakeCRServosDirection(1.0);
                    telemetry.addData("intake direction", 1);
                    telemetry.addData("intake servo direction", 1);
                }
                else if (gamepad2.left_bumper)
                {
                    auxMotors_TeleOp.SetIntakeDirection(-1.0);
                    servos_TeleOp.SetIntakeCRServosDirection(-1.0);
                    telemetry.addData("intake direction", -1);
                    telemetry.addData("intake servo direction", -1);
                }
                else
                {
                    auxMotors_TeleOp.SetIntakeDirection(0);
                    servos_TeleOp.SetIntakeCRServosDirection(0);
                    telemetry.addData("intake direction", 0);
                    telemetry.addData("intake servo direction", 0);
                }

                if (s1Vals.get("red") >= 36) servos.blockWheel.setPower(0.0);
                if (s2Vals.get("red") >= 150) servos.blockWheel.setPower(0.8);
            }

            {
                if (extenderlimit.isPressed())
                {
                    if (gamepad2.dpad_right)
                    {
                        servos.extenderServo.setPower(-0.85);
                    }
                    else if (gamepad2.dpad_left)
                    {
                        servos.extenderServo.setPower(0.85);
                    }
                    else
                    {
                        servos.extenderServo.setPower(0.0);
                    }
                }
                telemetry.addData("extender mode", extender);
                telemetry.addData("in trans?", inTrans);
                telemetry.addData("sensor active?", extenderlimit.isPressed());
                // TODO remove this later
                telemetry.addData("is lifter touch sensor active", sensors.lifterTouchSensor.IsSensorActive());
                telemetry.addData("extender servo power", servos.extenderServo.getPower());
            }
            {
                // Gripper controls
                if (gamepad2.a)
                {
                    grabberToggle.UpdateToggle("ON");
                }
                else
                {
                    grabberToggle.UpdateToggle("OFF");
                }

                if (s1Vals.get("red") >= 36) grabberToggle.OverrideSetToggle("OFF");
                if (s2Vals.get("red") >= 150) grabberToggle.OverrideSetToggle("ON");

                if (grabberToggle.Toggle()) servos.gripperServo.setPosition(0);
                else servos.gripperServo.setPosition(1);

                telemetry.addData("gripper servo pos", servos.gripperServo.getPosition());
            }

            {
                // Foundation grabber controls
                if (gamepad2.x)
                {
                    foundationToggle.UpdateToggle("ON");
                }
                else
                {
                    foundationToggle.UpdateToggle("OFF");
                }

                if (foundationToggle.Toggle()) servos.foundationServo1.setPosition(1);
                else servos.foundationServo1.setPosition(0);
                telemetry.addData("foundation servo pos", servos.foundationServo1.getPosition());
            }

            {
                // Capstone controls
                if (gamepad2.b)
                {
                    capstoneToggle.UpdateToggle("ON");
                }
                else
                {
                    capstoneToggle.UpdateToggle("OFF");
                }

                if (capstoneToggle.Toggle()) servos.capstoneRelease.setPosition(0);
                else servos.capstoneRelease.setPosition(1);
                telemetry.addData("capstone servo pos", servos.capstoneRelease.getPosition());
            }
        }

        {
            // Autonomous in TeleOp functionality
            /* TODO add automatic alignment
             * As soon as distance and color sensors are mounted properly for automatic alignment, continue setting up alignment.
             * If the distance reading from both of the sensors is the same, go forward.
             * If the distance reading is greater on the left, turn right.
             * If the distance reading is greater on the right, turn left.
             * If the color reading is showing up as yellow, stop strafing.
             * If the color reading isn't showing up as yellow, strafe right until it is.
             * Note that this should be configured until the values are correct and it works consistently each and every time.
             */
        }
        telemetry.update();

        /*
        Important stuff regarding encoders yay
         */
        if (sensors.lifterTouchSensor.IsSensorActive())
        {
            cpos = 0;
            rpos = auxMotors_TeleOpEncoders.GetCurrentPos();
        }
        else
        {
            cpos = auxMotors_TeleOpEncoders.GetCurrentPos() - rpos;
        }
    }

//    /**
//     * Loop function
//     * Everything that happens during TeleOp is in here.
//     * Structure of code:
//     * - Drive train
//     * - Stacker
//     * - Intake
//     */
//    public void loopm ()
//    {
//        if (gamepad2.dpad_up)
//        {
//            lifterShifter.Shift(Utils.Shift.Up);
//        }
//        else
//        {
//            lifterShifter.Released();
//        }
//        if (gamepad2.dpad_down)
//        {
//            lifterShifter.Shift(Utils.Shift.Down);
//        }
//        else
//        {
//            lifterShifter.Released();
//        }
//        telemetry.addData("current gear", lifterShifter.CurrentGear);
//        telemetry.addData("current encoder pos", auxMotors_TeleOpEncoders.GetCurrentPos());
//        // auxMotors_TeleOpEncoders.SetTargetPos(Levels.get(lifterShifter.CurrentGear));
//        // auxMotors_TeleOpEncoders.AdjustMotorPower();
//        Map<String, Integer> s2Vals = sensors.getInside2SensorVals();
//        Map<String, Integer> s1Vals = sensors.getInside1SensorVals();
//        telemetry.addData("S1 Val: ", s1Vals.get("argb"));
//        telemetry.addData("S2 Val: ", s2Vals.get("argb"));
//        telemetry.addData("S1 rVal: ", s1Vals.get("red"));
//        telemetry.addData("S1 gVal: ", s1Vals.get("green"));
//        telemetry.addData("S1 bVal: ", s1Vals.get("blue"));
//        telemetry.addData("S2 rVal: ", s2Vals.get("red"));
//        telemetry.addData("S2 gVal: ", s2Vals.get("green"));
//        telemetry.addData("S2 bVal: ", s2Vals.get("blue"));
//        if (s1Vals.get("red") >= 36) {
//            servos.blockWheel.setPower(0.0);
//            grabberToggle.OverrideSetToggle("ON");
//        }
//        if (s2Vals.get("red") >= 150) {
//            servos.blockWheel.setPower(0.8);
//            grabberToggle.OverrideSetToggle("OFF");
//        }
////        if (sensors.getInside1State())
////        {
////           // grabberToggle.OverrideSetToggle("OFF");
////        }
////        if (sensors.getInside2State())
////        {
////            servos.blockWheel.setPower(0.8);
////        }
//
//        /* get gamepad values */
//        float gamepad1_leftStickY = gamepad1.left_stick_y;
//        float gamepad1_leftStickX = gamepad1.left_stick_x;
//        float gamepad1_rightStickX = gamepad1.right_stick_x;
//
//        /* modify speed divider */
//        if (gamepad1.right_trigger > 0)
//        {
//            motorSpeedDiv = 1;
//        }
//        else if (gamepad1.left_trigger > 0)
//        {
//            motorSpeedDiv = 4;
//        }
//        else
//        {
//            motorSpeedDiv = 2;
//        }
//
//        /* set power to the motors, based on game pad 1 values. */
//        driveMotors_TeleOp.UpdateMotors(
//                -gamepad1_leftStickY,
//                gamepad1_leftStickX,
//                gamepad1_rightStickX,
//                motorSpeedDiv,
//                curve
//        );
//
//        /* backwards directionals for use in stacking
//           helpful for more easily stacking blocks.
//           these are all inverted controls */
//        if (gamepad1.dpad_up)
//        {
//            driveMotors_Common.MeccanumDirection("BACKWARD", 0.4);
//        }
//        else if (gamepad1.dpad_left)
//        {
//            driveMotors_Common.MeccanumDirection("RIGHT", 0.4);
//        }
//        else if (gamepad1.dpad_down)
//        {
//            driveMotors_Common.MeccanumDirection("FORWARD", 0.4);
//        }
//        else if (gamepad1.dpad_right)
//        {
//            driveMotors_Common.MeccanumDirection("LEFT", 0.4);
//        }
//
//        /* if there's currently a turn active, create a new Turn with the turn target
//           and alternate turn target, in case it's at 0 or -180. */
////        if (!turns.turnFinished)
////        {
////            Sensors.Turn newTurn = new Sensors().new Turn(turns.turnTarget);
////            Sensors.Turn newTurn_alt = new Sensors().new Turn(turns.turnTarget_alt);
////            if (newTurn.Finished() || newTurn_alt.Finished()) /* if either of the turns have been completed */
////            {
////                turns.turnFinished = true;
////                driveMotors_Common.Brake();
////            }
////            else if (gamepad1_leftStickX > 0.01 || gamepad1_leftStickY > 0.01 || gamepad1_rightStickX > 0.01)
////            {
////                turns.turnFinished = true;
////            }
////            else /* you still need to keep turning the robot */
////            {
////                if (Math.abs(Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target)) < 3)
////                {
////                    motorSpeedDiv = 12;
////                }
////                else if (Math.abs(Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target)) < 5)
////                {
////                    motorSpeedDiv = 5;
////                }
////                else if (Math.abs(Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target)) < 7)
////                {
////                    motorSpeedDiv = 2;
////                }
////                else if (Math.abs(Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target)) < 30)
////                {
////                    motorSpeedDiv = (float) 0.7;
////                }
////                else
////                {
////                    motorSpeedDiv = 1;
////                }
////                telemetry.addData("current position", newTurn.CurrentZ()); // TODO remove telemetry later
////                telemetry.addData("target position", newTurn.Target);
////                telemetry.addData("absolute distance from target", Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target));
////                if (newTurn.Target > 0 && newTurn.Target < 180 && newTurn.CurrentZ() > 0 && newTurn.CurrentZ() < 180)
////                {
////                    /* On the same side */
////                    if (Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target) > 0)
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                    }
////                    else
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(-1, 1 / motorSpeedDiv));
////                    }
////                }
////                else if (newTurn.Target < 0 && newTurn.Target > -180 && newTurn.CurrentZ() < 0 && newTurn.CurrentZ() > -180)
////                {
////                    /* On the same side */
////                    if (Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target) > 0)
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(-1, 1 / motorSpeedDiv));
////                    }
////                    else
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                    }
////                }
////                else if (newTurn_alt.Target > 0 && newTurn_alt.Target < 180 && newTurn_alt.CurrentZ() > 0 && newTurn.CurrentZ() < 180)
////                {
////                    /* On the same side */
////                    if (Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target) > 0)
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                    }
////                    else
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(-1, 1 / motorSpeedDiv));
////                    }
////                }
////                else if (newTurn_alt.Target < 0 && newTurn_alt.Target > -180 && newTurn_alt.CurrentZ() < 0 && newTurn_alt.CurrentZ() > -180)
////                {
////                    /* On the same side */
////                    if (Math.abs(newTurn.CurrentZ()) - Math.abs(newTurn.Target) > 0)
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(-1, 1 / motorSpeedDiv));
////                    }
////                    else
////                    {
////                        driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                    }
////                }
////                else if (newTurn.Target > 0 && newTurn.Target < 180 && newTurn.CurrentZ() < 0)
////                {
////                    /* Different side */
////                    driveMotors_Common.SetAllMotorValues(sensors.Power(-1, 1 / motorSpeedDiv));
////                }
////                else if (newTurn.Target < 0 && newTurn.Target > -180 && newTurn.CurrentZ() > 0)
////                {
////                    /* Different side */
////                    driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                }
////                else
////                {
////                    /* no matched cases, spin and hope something works out */
////                    driveMotors_Common.SetAllMotorValues(sensors.Power(1, 1 / motorSpeedDiv));
////                }
////            }
////        }
//
//        /* set power to the lifer based on inputs from game pad 2 */
//        if (gamepad2.right_trigger > 0)
//        {
//            auxMotors_TeleOp.SetStackerDirection(-gamepad2.right_trigger);
//        }
//        else if (gamepad2.left_trigger > 0)
//        {
//            if (!sensors.lifterTouchSensor.IsSensorActive())
//            {
//                auxMotors_TeleOp.SetStackerDirection(gamepad2.left_trigger);
//            }
//            else
//            {
//                auxMotors_TeleOp.SetStackerDirection(0);
//            }
//        }
//        else
//        {
//            auxMotors_TeleOp.SetStackerDirection(0);
//        }
//
//        /* set power to the intakes based on inputs from game pad 2 */
//        if (gamepad2.right_bumper)
//        {
//            auxMotors_TeleOp.SetIntakeDirection(1.0);
//            servos_TeleOp.SetIntakeCRServosDirection(1.0);
//        }
//        else if (gamepad2.left_bumper)
//        {
//            auxMotors_TeleOp.SetIntakeDirection(-1.0);
//            servos_TeleOp.SetIntakeCRServosDirection(-1.0);
//        }
//        else
//        {
//            auxMotors_TeleOp.SetIntakeDirection(0.0);
//            servos_TeleOp.SetIntakeCRServosDirection(0.0);
//        }
//
//        /* update the toggle for the grabber
//           note that this DOES NOT directly update the grabber */
//        if (gamepad2.a)
//        {
//            grabberToggle.UpdateToggle("ON");
//        }
//        else
//        {
//            grabberToggle.UpdateToggle("OFF");
//        }
//
//        if (gamepad2.x)
//        {
//            foundationToggle.UpdateToggle("ON");
//        }
//        else
//        {
//            foundationToggle.UpdateToggle("OFF");
//        }
//
////        /* use the intake pusher based on inputs from game pad 2 */
////        if (gamepad2.b)
////        {
////            servos_TeleOp.SetServoPosition(servos.intakePush, 1);
////            grabberToggle.UpdateToggle("OFF"); // TODO verify that this is the correct position.
////        }
////        else
////        {
////            servos_TeleOp.SetServoPosition(servos.intakePush, 0);
////        }
//
//        if (gamepad2.y)
//        {
//            extenderToggle.UpdateToggle("ON");
//        }
//        else
//        {
//            extenderToggle.UpdateToggle("OFF");
//        }
//
//        if (gamepad2.b) {
//            capstoneToggle.UpdateToggle("ON");
//        } else {
//            capstoneToggle.UpdateToggle("OFF");
//        }
//
//        if (capstoneToggle.Toggle()) {
//            servos_TeleOp.SetServoPosition(servos.capstoneRelease, 1.0);
//        } else {
//            servos_TeleOp.SetServoPosition(servos.capstoneRelease, 0.0);
//        }
//
//        if (extenderToggle.Toggle())
//        {
//            if (extenderMode == 0)
//            {
//                extenderMode = 1;
//            }
//            else if (extenderMode == 1)
//            {
//                extenderMode = 0;
//            }
//        }
//
//        if (gamepad2.left_stick_x != 0)
//        {
//            if (gamepad2.left_stick_x > 0)
//            {
//                servos_TeleOp.SetCRServoDirection(servos.extenderServo, DcMotorSimple.Direction.FORWARD);
//            }
//            else
//            {
//                servos_TeleOp.SetCRServoDirection(servos.extenderServo, DcMotorSimple.Direction.REVERSE);
//            }
//            servos_TeleOp.SetCRServoPower(servos.extenderServo, 0.85);
//        }
//        // else if (extenderMode == 0)
//        // {
//        // if (/*!sensors.IsExtenderAtMin()*/ true)
//        // {
//        //     servos_TeleOp.SetCRServoDirection(servos.extenderServo, DcMotorSimple.Direction.REVERSE);
//        //     servos_TeleOp.SetCRServoPower(servos.extenderServo, 0.85);
//        // }
//        // }
//        // else if (extenderMode == 1)
//        // {
//        // if (/*!sensors.IsExtenderAtMax()*/ true)
//        // {
//        //     servos_TeleOp.SetCRServoDirection(servos.extenderServo, DcMotorSimple.Direction.FORWARD);
//        //     servos_TeleOp.SetCRServoPower(servos.extenderServo, 0.85);
//        // }
//        // }
//        else
//        {
//            servos_TeleOp.SetCRServoPower(servos.extenderServo, 0.0);
//        }
//
//        if (grabberToggle.Toggle())
//        {
//            servos_TeleOp.SetServoPosition(servos.gripperServo, 0);
//            telemetry.addData("g servo pos", "0");
//        }
//        else
//        {
//            servos_TeleOp.SetServoPosition(servos.gripperServo, 1);
//            telemetry.addData("g servo pos", "1");
//        }
//
//        if (foundationToggle.Toggle())
//        {
//            servos_TeleOp.SetServoPosition(servos.foundationServo1, 1);
//        }
//        else
//        {
//            servos_TeleOp.SetServoPosition(servos.foundationServo1, 0);
//        }
//        telemetry.update(); // TODO remove telemetry later
//    }
}
