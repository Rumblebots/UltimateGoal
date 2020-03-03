package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Servos;

@Deprecated
@Disabled
@TeleOp(name = "Test", group = "TeleOp")
public class Test extends OpMode {
    public boolean debug = true; // If enabled, all debug messages are shown.

    /**
     * Set up the Motors_Aux interface.
     * This controls all of the functions of the aux motors.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux Motors_Aux_Reference = new Motors_Aux();
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux.TeleOp Motors_Aux = Motors_Aux_Reference.new TeleOp(); // Access TeleOp controls.

    /**
     * Set up the Motors_Drive interface.
     * This controls all of the functions of the drive motors.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive Motors_Drive_Reference = new Motors_Drive();
    private org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.TeleOp Motors_Drive = Motors_Drive_Reference.new TeleOp(); // Access TeleOp controls.

    /**
     * Set up the Servos interface.
     * This controls all of the functions of the servos.
     */
    private org.firstinspires.ftc.teamcode.hardware.v2.Servos Servos_Reference = new Servos();
    private org.firstinspires.ftc.teamcode.hardware.v2.Servos.TeleOp Servos = Servos_Reference.new TeleOp(); // Access TeleOp controls.

    /**
     * Other variables used by the robot.
     * motorSpeedDiv controls how fast the robot can go.
     * inverted controls if the robot drives in reverse or not.
     */
    private int motorSpeedDiv = 2;
    private boolean inverted = false;

    @Override
    public void init() {
        /**
         * Init all of the motors and servos the robot uses.
         * Call the init function from each interface.
         */
        Motors_Aux_Reference.init(hardwareMap);
        Motors_Drive_Reference.init(hardwareMap);
        Servos_Reference.init(hardwareMap);
    }

    //Main loop method (where everything happens)
    @Override
    public void loop() {
        /**
         * Get values from joysticks off gamepad 1,
         * then use the UpdateMotors function from the Motors_Drive interface
         * to set power to the drive motors and then move the robot.
         */
        float Gp1_LeftStickY = gamepad1.left_stick_y,
                Gp1_LeftStickX = gamepad1.left_stick_x,
                Gp1_RightStickX = gamepad1.right_stick_x;
        if (inverted) Motors_Drive.UpdateMotors(-Gp1_LeftStickY, Gp1_LeftStickX, Gp1_RightStickX, motorSpeedDiv);
        else Motors_Drive.UpdateMotors(-Gp1_LeftStickY, Gp1_LeftStickX * -1, Gp1_RightStickX * -1, motorSpeedDiv);

        /**
         * Move the stacker arm depending on what inputs
         * gamepad 2 is getting.
         */
        if (gamepad2.right_trigger > 0) Motors_Aux.SetStackerDirection(gamepad2.right_trigger);
        else if (gamepad2.left_trigger > 0) Motors_Aux.SetStackerDirection(-gamepad2.left_trigger);
        else Motors_Aux.SetStackerDirection(0.0);

        /**
         * Check if the extenderServo should be going out or in.
         * If the left bumper is pressed, go out.
         * If the right bumper is pressed, go in.
         * Update telemetry along the way to ensure there's no issues with this code.
         */
        double leftStickX = Range.clip(gamepad2.left_stick_x, -1.0, 1.0),
                leftStickX_abs = Math.abs(leftStickX);
        telemetry.addData("Left Stick X Value: ", leftStickX + ", " + leftStickX_abs);
        if (!(leftStickX == 0.0))
        {
            Servos.SetCRServoPower(Servos_Reference.extenderServo, leftStickX_abs);
            telemetry.addData("Extender Power: ", leftStickX_abs + " (positive)");
        }
        else
        {
            Servos.SetCRServoPower(Servos_Reference.extenderServo, 0.0);
            telemetry.addData("Extender Power: ", "0.0 (no direction)");
        }
        if (leftStickX > 0.0)
        {
            Servos.SetCRServoDirection(Servos_Reference.extenderServo, DcMotorSimple.Direction.FORWARD);
            telemetry.addData("Extender Direction ", "FORWARD");
        }
        else if (leftStickX < 0.0)
        {
            Servos.SetCRServoDirection(Servos_Reference.extenderServo, DcMotorSimple.Direction.REVERSE);
            telemetry.addData("Extender Direction ", "REVERSE");
        }

        /**
         * Move the intake motors on the front of the robot.
         * Map the controls to gamepad1.
         * Originally, these controls were on gamepad2, but we
         * decided that it would be easier for us to use if the controls were on
         * gamepad1 instead.
         */
        if (gamepad1.right_bumper)
        {
            Motors_Aux.SetIntakeDirection(1.0);
            telemetry.addData("Intake Direction: ", "1.0 (Going In)");
        }
        else if (gamepad1.left_bumper)
        {
            Motors_Aux.SetIntakeDirection(-1.0);
            telemetry.addData("Intake Direction: ", "-1.0 (Going Out)");
        }
        else
        {
            Motors_Aux.SetIntakeDirection(0.0);
            telemetry.addData("Intake Direction: ", "0.0 (Stationary)");
        }

//        /**
//         * Set position of the intake pusher servo
//         */
//        if (!gamepad2.b)
//        {
//            Servos.SetServoPosition(Servos_Reference.intakePush, 0);
//            telemetry.addData("Intake Push Servo Status: ", "0");
//        }
//        else
//        {
//            Servos.SetServoPosition(Servos_Reference.intakePush, 1);
//            telemetry.addData("Intake Push Servo Status: ", "1");
//        }

        /**
         * Also related to the intake - control the intake servos.
         * These are mapped to gamepad 2.
         */
        if (gamepad2.right_bumper) // Right is pressed, go in.
        {
            Servos.SetIntakeCRServosDirection(1.0);
        }
        else if (gamepad2.left_bumper) // Left is pressed, go out.
        {
            Servos.SetIntakeCRServosDirection(-1.0);
        }
        else // Neither buttons are pressed
        {
            Servos.SetIntakeCRServosDirection(0.0);
        }

        /**
         * Set the position of the gripper servo. This one grips the sky stones that are picked up.
         */
        if (!gamepad2.a)
        {
            Servos.SetServoPosition(Servos_Reference.gripperServo, 0);
            telemetry.addData("Gripper Servo Status: ", "0");
        }
        else
        {
            Servos.SetServoPosition(Servos_Reference.gripperServo, 1);
            telemetry.addData("Gripper Servo Status: ", "1");
        }
//
//        /**
//         * Set the position of the capstone release servo. This one releases the capstone. Wow. Who could have guessed.
//         */
//        if (!gamepad2.dpad_up)
//        {
//            Servos.SetServoPosition(Servos_Reference.capstoneRelease, 0);
//            telemetry.addData("Capstone Release Servo Status: ", "0");
//        }
//        else
//        {
//            Servos.SetServoPosition(Servos_Reference.capstoneRelease, 1);
//            telemetry.addData("Capstone Release Servo Status: ", "1");
//        }


        /**
         * Update the telemetry.
         * Useful for debugging purposes.
         * Only update the telemetry if debug mode is enabled.
         */
        if (debug) telemetry.update();
    }
}
