package org.firstinspires.ftc.teamcode.skystone.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.skystone.hardware.MotorsDrive;
@Disabled
@Deprecated
//@Autonomous(name = "Encoder Test", group = "Tests")

public class EncoderTest extends LinearOpMode {
    static final double COUNTS_PER_MOTOR_REV = 537.6;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 6;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.3;
    static final double TURN_SPEED = 0.3;
    MotorsDrive motors = new MotorsDrive();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        motors.init(hardwareMap);
        motors.resetEncoders();
        waitForStart();
        if (opModeIsActive()){
//            encoderDrive(0.2, 20, 20, 4.0);
//            sleep(500);
//            motors.resetEncoders();
//            encoderStrafe(0.3, 10, 10, 4.0);
//            sleep(500);
//            motors.resetEncoders();
//            encoderStrafe(0.3, -20, -20, 4.0);
//            sleep(500);
//            motors.resetEncoders();
            encoderDrive(0.3, 20, 20, 4.0);

        }
    }
    public void encoderStrafe(double speed,
                              double leftInches, double rightInches,
                              double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int offset = 0;
        double newLeft = -leftInches/.6;
        double newRight = -rightInches /.6;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = motors.motorFrontLeft.getCurrentPosition() + (int) (newLeft * COUNTS_PER_INCH);
            newRightTarget = motors.motorFrontRight.getCurrentPosition() + (int) (newRight * COUNTS_PER_INCH);

            motors.setTargetPos(-newLeftTarget, -newRightTarget, newLeftTarget, newRightTarget);

            // Turn On RUN_TO_POSITION
            motors.setRunToPos();
            // reset the timeout time and start motion.
            runtime.reset();
            motors.motorFrontLeft.setPower(Math.abs(speed));
            motors.motorFrontRight.setPower(Math.abs(speed));
            motors.motorBackRight.setPower(Math.abs(speed));
            motors.motorBackLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.motorFrontLeft.isBusy() && motors.motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        motors.motorFrontLeft.getCurrentPosition(),
                        motors.motorFrontRight.getCurrentPosition());
                telemetry.update();
            }
            motors.stopAll();
            motors.stopRunToPos();
            //  sleep(250);   // optional pause after each move
        }
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        double newLeft = -leftInches;
        double newRight = -rightInches;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = motors.motorFrontLeft.getCurrentPosition() + (int) (newLeft * COUNTS_PER_INCH);
            newRightTarget = motors.motorFrontRight.getCurrentPosition() + (int) (newRight * COUNTS_PER_INCH);

            motors.setTargetPos(-newLeftTarget, newRightTarget, -newLeftTarget, newRightTarget);

            // Turn On RUN_TO_POSITION
            motors.setRunToPos();
            // reset the timeout time and start motion.
            runtime.reset();
            motors.motorFrontLeft.setPower(Math.abs(speed));
            motors.motorFrontRight.setPower(Math.abs(speed));
            motors.motorBackRight.setPower(Math.abs(speed));
            motors.motorBackLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.motorFrontLeft.isBusy() && motors.motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        motors.motorFrontLeft.getCurrentPosition(),
                        motors.motorFrontRight.getCurrentPosition());
                telemetry.update();
            }
            motors.stopAll();
            motors.stopRunToPos();
            //  sleep(250);   // optional pause after each move
        }
    }
}
