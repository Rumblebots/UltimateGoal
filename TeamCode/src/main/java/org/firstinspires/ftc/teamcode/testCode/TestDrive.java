/****
 * Made by Tejas Mehta
 * Made on Saturday, December 05, 2020
 * File Name: TestDrive
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org._11253.lib.utils.gen.Toggle;

@TeleOp(name = "Test", group = "Test")
public class TestDrive extends OpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor flywheel1;
    DcMotor flywheel2;
    DcMotor intake;
    CRServo intakeServo;
    Servo loader;
    Servo pusher;
    Toggle t = new Toggle();
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel2");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
    }

    @Override
    public void loop() {
        frontRight.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);
        backRight.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
        frontLeft.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
        backLeft.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);


        if (gamepad2.a) {
            t.onPress();
        } else {
            t.onRelease();
        }
        if (t.state) {
            flywheel1.setPower(1);
            flywheel2.setPower(1);
        } else {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }
        if (gamepad2.right_bumper && !gamepad2.left_bumper) {
            intake.setPower(1);
            intakeServo.setPower(-1);
        } else {
            intake.setPower(0);
            intakeServo.setPower(0);
        }

        if (gamepad2.left_bumper && !gamepad2.right_bumper) {
            intake.setPower(-1);
            intakeServo.setPower(1);
        } else {
            intake.setPower(0);
            intakeServo.setPower(0);
        }
    }
}
