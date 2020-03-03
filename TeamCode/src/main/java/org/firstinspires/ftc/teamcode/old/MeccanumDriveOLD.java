package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.MotorsDrive;

@Deprecated
@Disabled
public class MeccanumDriveOLD extends OpMode {
    private MotorsDrive motors = new MotorsDrive();
    private ServosOLD servos = new ServosOLD();
    private MotorsAuxOLD motorsAuxOLD = new MotorsAuxOLD();
    private int motorSpeedDiv = 2;
    boolean inverted = false;

    @Override
    public void init() {
        motors.init(hardwareMap);
        servos.init(hardwareMap);
        motorsAuxOLD.init(hardwareMap);
    }

    @Override
    public void loop() {
        //Change the speed reduction/gain upon a button press
//        if (gamepad1.a) {
//            motorSpeedDiv = 1;
//        }
//        if (gamepad1.b) {
//            motorSpeedDiv = 2;
//        }
//        if (gamepad1.x){
//            motorSpeedDiv = 3;
//        }
//        if (gamepad1.y) {
//            motorSpeedDiv = 4;
//        }
        if (gamepad1.right_trigger > 0.1) {
            motorSpeedDiv = 1;
        } else if (gamepad1.right_bumper) {
            motorSpeedDiv = 4;
        } else {
            motorSpeedDiv = 2;
        }

        //Inverter
        if (gamepad1.left_trigger > 0.1) {
            inverted = true;
        } else {
            inverted = false;
        }

        //Control stacker motors
        motorsAuxOLD.stackerBotttom.setPower(gamepad2.left_stick_y/2);
        motorsAuxOLD.stackerTop.setPower(-gamepad2.left_stick_y/2);

        servos.blockGrabber.setPower(gamepad2.right_stick_y);

        if (gamepad2.b) {
            motorsAuxOLD.intakeRight.setDirection(DcMotorSimple.Direction.FORWARD);
            motorsAuxOLD.intakeRight.setPower(1.0);
            motorsAuxOLD.intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            motorsAuxOLD.intakeLeft.setPower(1.0);
        } else if (!gamepad2.a) {
            motorsAuxOLD.intakeRight.setPower(0.0);
            motorsAuxOLD.intakeLeft.setPower(0.0);
        }

        if (gamepad2.a) {
            motorsAuxOLD.intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
            motorsAuxOLD.intakeRight.setPower(1.0);
            motorsAuxOLD.intakeLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            motorsAuxOLD.intakeLeft.setPower(1.0);

        } else if (!gamepad2.b) {
            motorsAuxOLD.intakeRight.setPower(0.0);
            motorsAuxOLD.intakeLeft.setPower(0.0);
        }

        if (gamepad2.right_trigger > 0.1) {
            servos.stackerTop.setPower(1.0);
            servos.stackerBottom.setPower(1.0);
        } else if (gamepad2.left_trigger > 0.1) {
            servos.stackerTop.setPower(-1.0);
            servos.stackerBottom.setPower(-1.0);
        } else {
            servos.stackerTop.setPower(0);
            servos.stackerBottom.setPower(0);
        }

        if (gamepad2.right_bumper) {
           servos.intakeLeft.setPower(-1.0);
           servos.intakeRight.setPower(1.0);
        } else if (!gamepad2.left_bumper) {
            servos.intakeLeft.setPower(0.0);
            servos.intakeRight.setPower(0.0);
        }

        if (gamepad2.left_bumper) {
            servos.intakeLeft.setPower(1.0);
            servos.intakeRight.setPower(-1.0);
        } else if (!gamepad2.right_bumper) {
            servos.intakeLeft.setPower(0.0);
            servos.intakeRight.setPower(0.0);
        }

        if (gamepad1.a) {
            servos.autonRed.setPower(1.0);
        } else {
            servos.autonRed.setPower(0.0);
        }

        //needed joystick values for the drive
        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
        float FrontRight = -gamepad1LeftY + gamepad1LeftX + gamepad1RightX;
        float BackRight = -gamepad1LeftY - gamepad1LeftX + gamepad1RightX;
        float BackLeft = gamepad1LeftY - gamepad1LeftX + gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors, and base it off of whether the motors are set to run inverted or not
        if (inverted) {
            motors.motorFrontRight.setPower(-FrontRight/motorSpeedDiv);
            motors.motorFrontLeft.setPower(-FrontLeft/motorSpeedDiv);
            motors.motorBackLeft.setPower(-BackLeft/motorSpeedDiv);
            motors.motorBackRight.setPower(-BackRight/motorSpeedDiv);
        } else {
            motors.motorFrontRight.setPower(FrontRight/motorSpeedDiv);
            motors.motorFrontLeft.setPower(FrontLeft/motorSpeedDiv);
            motors.motorBackLeft.setPower(BackLeft/motorSpeedDiv);
            motors.motorBackRight.setPower(BackRight/motorSpeedDiv);
        }
        telemetry.addData("Speed %: ", Math.round(100.0/motorSpeedDiv) + "%");
        telemetry.update();
    }
}
