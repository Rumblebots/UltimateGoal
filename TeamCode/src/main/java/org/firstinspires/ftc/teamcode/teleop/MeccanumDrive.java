package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotCore.Utils;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;
import org.firstinspires.ftc.teamcode.hardware.v2.Servos;

/**
 * New TeleOp code, 12/31/2019.
 * If you're reading this, it'll already be 2020, so...
 * Happy new year!
 * Anyway, if for some reason you'd like to go back to the old code that was being used,
 * I didn't delete any of it, and you can go back to it at any time.
 */
@Disabled
@Deprecated
@TeleOp(name = "Meccanum (use this)", group = "TeleOp")
public class MeccanumDrive extends OpMode
{
    /**
     * Debug features.
     * These are toggle-able during the init phase.
     */
    static class debug
    {
        static boolean debug = true;
        static boolean motors = true;
        static boolean servos = true;
    } // TODO make these all false later.

    /*
     * Set up the Motors_Aux interface.
     * This controls all of the functions of the aux motors.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux Motors_Aux_Reference = new Motors_Aux();
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux.TeleOp Motors_Aux = Motors_Aux_Reference.new TeleOp(); // Access TeleOp controls.

    /*
     * Set up the Motors_Drive interface.
     * This controls all of the functions of the drive motors.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive Motors_Drive_Reference = new Motors_Drive();
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.TeleOp Motors_Drive = Motors_Drive_Reference.new TeleOp(); // Access TeleOp controls.
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.Common Motors_Drive_Common = Motors_Drive_Reference.new Common(); // Access Common controls

    /*
     * Set up the Servos interface.
     * This controls all of the functions of the servos.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Servos Servos_Reference = new Servos();
    private org.firstinspires.ftc.teamcode.hardware.v2.Servos.TeleOp Servos = Servos_Reference.new TeleOp(); // Access TeleOp controls.

    private Sensors Sensors_Reference = new Sensors(); // go to sensors lmao

    /*
     * Set up the toggles for the gripper and something else
     * Toggles construct a new instance of the Toggle class in Utils
     * Also set up a potential shifter. Not sure if I'm going to use it yet, but it's there anyway
     */
    private Utils.Toggle GripperToggle = new Utils().new Toggle(true);
    private Utils.Toggle CapstoneToggle = new Utils().new Toggle(true);
    private Utils.Toggle HolderToggle = new Utils().new Toggle(true);

    private Utils.Toggle DpadUp = new Utils().new Toggle(false);
    private Utils.Toggle DpadLeft = new Utils().new Toggle(false);
    private Utils.Toggle DpadDown = new Utils().new Toggle(false);
    private Utils.Toggle DpadRight = new Utils().new Toggle(false);

    private int ServoDirection = 1;
    private int Curve = 3; // this should be adjusted to the driver's preference

    @Override
    public void init() {
        /*
         * Init all of the motors and servos the robot uses.
         * Call the init function from each interface.
         * @see Motors_Aux_Reference.init();
         * @see Motors_Drive_Reference.init();
         * @see Servos_Reference.init();
         */
        Motors_Aux_Reference.init(hardwareMap);
        Motors_Drive_Reference.init(hardwareMap);
        Servos_Reference.init(hardwareMap);
        Sensors_Reference.init(hardwareMap);

        /*
         * Different smoothing options.
         * A = none - most jerky
         * B = 2x - very basic smoothing, doesn't change the feel of the robot
         * X = 4x - smooth, stops and starts at a good speed
         * Y = 8x - most smooth, slow stops and starts
         */
        if (gamepad1.a || gamepad2.a)
        {
            Curve = 0;
        }
        else if (gamepad1.b || gamepad2.b)
        {
            Curve = 2;
        }
        else if (gamepad1.x || gamepad2.x)
        {
            Curve = 4;
        }
        else if (gamepad1.y || gamepad2.y)
        {
            Curve = 8;
        }

        /*
         * Set up debugging mode.
         * If DPAD UP is pressed on either controller, enable all debug messages.
         * If DPAD DOWN is pressed on either controllers, enable only motor debug messages.
         * If DPAD LEFT is pressed on either controllers, enable only servo debug messages.
         * By default, the debug messages are hidden from view.
         * They're useful for, well, debugging, and that's about it.
         */
        if (gamepad1.dpad_up || gamepad2.dpad_up) // Show all debug messages
        {
            debug.debug = true;
            debug.motors = true;
            debug.servos = true;
            telemetry.addData("Debug Mode", "ALL");
        }
        else if (gamepad1.dpad_down || gamepad2.dpad_down) // Show only motor debug messages
        {
            debug.debug = true;
            debug.motors = true;
            debug.servos = false;
            telemetry.addData("Debug Mode", "MOTORS");
        }
        else if (gamepad1.dpad_left || gamepad2.dpad_left) // Show only servo debug messages
        {
            debug.debug = true;
            debug.motors = false;
            debug.servos = true;
            telemetry.addData("Debug Mode", "SERVOS");
        }
        else
        {
            telemetry.addData("Debug Mode", "DISABLED"); // Don't show any debug messages
        }

//        Servos.SetServoPosition(Servos_Reference.capstoneRelease, 0.5);
        telemetry.update(); // Update the telemetry based on what debug mode is enabled.
    }

    /**
     * This function is the main function of the robot.
     * This is a loop, meaning it repeats many times over.
     * This function is executed many many times per second.
     * Every time the function is executed, it runs through all of the
     * steps listed in the function. The control flow, for reference, is...
     * - Set position to the holder servo based on inputs from Gamepad 2
     * - Set position to the gripper servo based on inputs from Gamepad 2
     * - Change Motor Speed Modifier
     * - Read Values from Gamepad 1 for drive controls
     * - Update the powers each motor should have based on above values
     * - Move the stacker arm / lifter based on inputs from Gamepad 2
     * - Move the extender servo on top of the lifter based on inputs from Gamepad 2
     * - Set power to the intake motors on the front of the robot based on inputs from Gamepad 1
     * - Set position to intake pusher servos based on inputs from Gamepad 2
     * - Set power to the intake servos based on inputs from Gamepad 2
     * - Set position to the capstone release servo based on inputs from Gamepad 2
     */
    @Override
    public void loop() {
        if (gamepad1.dpad_up)
        {
            Motors_Drive_Common.MeccanumDirection("BACKWARD", 0.3);
        }
        else if (gamepad1.dpad_left)
        {
            Motors_Drive_Common.MeccanumDirection("RIGHT", 0.3);
        }
        else if (gamepad1.dpad_down)
        {
            Motors_Drive_Common.MeccanumDirection("FORWARD", 0.3);
        }
        else if (gamepad1.dpad_right)
        {
            Motors_Drive_Common.MeccanumDirection("LEFT", 0.3);
        }
        if (gamepad2.x) HolderToggle.UpdateToggle("ON");
        else HolderToggle.UpdateToggle("OFF");
//        if (HolderToggle.Toggle())
//        {
//            // Activate the holder
//            Servos.SetServoPosition(Servos_Reference.holder, 0);
//        }
//        else
//        {
//            // Deactivate the holder
//            Servos.SetServoPosition(Servos_Reference.holder, 1);
//        }

        /*
         * Check if a certain button has been pressed, and, if so, do cool stuff.
         */
        if (gamepad2.a) GripperToggle.UpdateToggle("ON");
        else GripperToggle.UpdateToggle("OFF");
        if (GripperToggle.Toggle())
        {
            // Open the gripper.
            Servos.SetServoPosition(Servos_Reference.gripperServo, 0);
        }
        else
        {
            // Close the gripper.
            Servos.SetServoPosition(Servos_Reference.gripperServo, 1);
        }

        /*
         * Change motor speed divs
         * This works by checking if either of the triggers are pressed.
         * If one of the triggers is pressed, it updates the speed mofifier.
         * If no triggers are pressed, it sets it back to the default value of 2.
         * Basically, it functions like a SHIFT or CONTROL key on a keyboard.
         */
        float motorSpeedDiv;
        if (gamepad1.right_trigger > 0) motorSpeedDiv = 1;                                 // 100% Speed.
        else if (gamepad1.left_trigger > 0) motorSpeedDiv = 4;                             // 25% Speed.
        else motorSpeedDiv = 2;                                                            // 50% Speed, Default mode.
        if (gamepad1.right_trigger > 0 && gamepad1.left_trigger > 0) motorSpeedDiv = (float) 0.01; // Turbo Mode! Yay!

        /*
         * Get values from joysticks off gamepad 1,
         * then use the UpdateMotors function from the Motors_Drive interface
         * to set power to the drive motors and then move the robot.
         */
        float Gp1_LeftStickY = gamepad1.left_stick_y,
                Gp1_LeftStickX = gamepad1.left_stick_x,
                Gp1_RightStickX = gamepad1.right_stick_x;

        /*
         * Other variables used by the robot.
         * inverted controls if the robot drives in reverse or not.
         * Holding down left trigger makes it inverted, that's useful for backing in.
         */
        double[] f;
        if (!(gamepad1.left_trigger > 0))
        {
            f = Motors_Drive.UpdateMotors(-Gp1_LeftStickY, Gp1_LeftStickX, Gp1_RightStickX, motorSpeedDiv, Curve);
        }
        else
        {
            f = Motors_Drive.UpdateMotors(Gp1_LeftStickY, -Gp1_LeftStickX, -Gp1_RightStickX, motorSpeedDiv, Curve);
        }

        /*
         * If debugging is enabled, four new telemetry entries should be added,
         * one for each motor.
         */
        if (debug.motors)
        {
            telemetry.addLine("Drive Motors");
            telemetry.addData("Front Right Motor", f[0]);
            telemetry.addData("Front Left Motor", f[1]);
            telemetry.addData("Back Right Motor", f[2]);
            telemetry.addData("Back Left Motor", f[3]);
        }

        /*
         * Move the stacker arm depending on what inputs
         * gamepad 2 is getting.
         */
        if (gamepad2.right_trigger > 0)
        {
            Motors_Aux.SetStackerDirection(gamepad2.right_trigger);
        }
        else if (gamepad2.left_trigger > 0)
        {
            if (true)
            {
                Motors_Aux.SetStackerDirection(-gamepad2.left_trigger);
            }
            else
            {
                Motors_Aux.SetStackerDirection(0.0);
            }
        }
        else
        {
            Motors_Aux.SetStackerDirection(0.0);
        }

        /*
         * Check if the extenderServo should be going out or in.
         * If the left bumper is pressed, go out.
         * If the right bumper is pressed, go in.
         * Update telemetry along the way to ensure there's no issues with this code.
         */
        double leftStickX = Range.clip(gamepad2.left_stick_x, -1.0, 1.0),
                leftStickX_abs = Math.abs(leftStickX);
        if (debug.debug) telemetry.addData("Left Stick X Value: ", leftStickX + ", " + leftStickX_abs);
        if (!(leftStickX == 0.0))
        {
            Servos.SetCRServoPower(Servos_Reference.extenderServo, leftStickX_abs);
            if (debug.servos) telemetry.addData("Extender Power: ", leftStickX_abs + " (positive)");
        }
        else
        {
            Servos.SetCRServoPower(Servos_Reference.extenderServo, 0.0);
            if (debug.servos) telemetry.addData("Extender Power: ", "0.0 (no direction)");
        }
        if (leftStickX > 0.0)
        {
            Servos.SetCRServoDirection(Servos_Reference.extenderServo, DcMotorSimple.Direction.FORWARD);
            if (debug.servos) telemetry.addData("Extender Direction ", "FORWARD");
        }
        else if (leftStickX < 0.0)
        {
            Servos.SetCRServoDirection(Servos_Reference.extenderServo, DcMotorSimple.Direction.REVERSE);
            if (debug.servos) telemetry.addData("Extender Direction ", "REVERSE");
        }

        /*
         * Move the intake motors on the front of the robot.
         * Map the controls to gamepad1.
         * Originally, these controls were on gamepad2, but we
         * decided that it would be easier for us to use if the controls were on
         * gamepad1 instead.
         */
        if (gamepad1.right_bumper)
        {
            Motors_Aux.SetIntakeDirection(1.0);
            if (debug.motors)telemetry.addData("Intake Direction: ", "1.0 (Going In)");
        }
        else if (gamepad1.left_bumper)
        {
            Motors_Aux.SetIntakeDirection(-1.0);
            if (debug.motors)telemetry.addData("Intake Direction: ", "-1.0 (Going Out)");
        }
        else
        {
            Motors_Aux.SetIntakeDirection(0.0);
            if (debug.motors) telemetry.addData("Intake Direction: ", "0.0 (Stationary)");
        }

//        /*
//         * Set position of the intake pusher servo
//         */
//        if (!gamepad2.b)
//        {
//            Servos.SetServoPosition(Servos_Reference.intakePush, 0);
//            if (debug.servos) telemetry.addData("Intake Push Servo Status: ", "0");
//        }
//        else
//        {
//            Servos.SetServoPosition(Servos_Reference.intakePush, 1);
//            if (debug.servos) telemetry.addData("Intake Push Servo Status: ", "1");
//        }

        /*
         * Also related to the intake - control the intake servos.
         * These are mapped to gamepad 2.
         */
        if (gamepad2.right_bumper) // Right is pressed, go in.
        {
            Servos.SetIntakeCRServosDirection(1.0);
            Motors_Aux.SetIntakeDirection(1.0);
        }
        else if (gamepad2.left_bumper) // Left is pressed, go out.
        {
            Servos.SetIntakeCRServosDirection(-1.0);
            Motors_Aux.SetIntakeDirection(-1.0);
        }
        else // Neither buttons are pressed
        {
            Servos.SetIntakeCRServosDirection(0.0);
            Motors_Aux.SetIntakeDirection(0.0);
        }

        /*
         * Set the position of the holder servo.
         */ //TODO go see here
        if (gamepad2.dpad_up)
        {
            ServoDirection = 0;
        }
        else if (gamepad2.dpad_down)
        {
            ServoDirection = 1;
        }
//        if (ServoDirection == 0)
//        {
//            Servos.SetServoPosition(Servos_Reference.holder, 1);
//            if (debug.servos) telemetry.addData("Holder Servo Status: ", "1");
//        }
//        else if (ServoDirection == 1)
//        {
//            Servos.SetServoPosition(Servos_Reference.holder, 0);
//            if (debug.servos) telemetry.addData("Holder Servo Status: ", "0");
//        }

        /*
         * Set the position of the capstone release servo. This one releases the capstone. Wow. Who could have guessed.
         */
//        if (gamepad2.x) CapstoneToggle.UpdateToggle("ON");
//        else CapstoneToggle.UpdateToggle("OFF");
//        if (CapstoneToggle.Toggle())
//        {
//            Servos.SetServoPosition(Servos_Reference.capstoneRelease, 0.5);
//            if (debug.servos) telemetry.addData("Capstone Release Servo Status: ", "0.5");
//        }
//        else
//        {
//            Servos.SetServoPosition(Servos_Reference.capstoneRelease, 0);
//            if (debug.servos) telemetry.addData("Capstone Release Servo Status: ", "0");
//        }


        /*
         * Update the telemetry.
         * Useful for debugging purposes.
         * Only update the telemetry if debug mode is enabled.
         */
        if (debug.debug) telemetry.update();
    }
}
