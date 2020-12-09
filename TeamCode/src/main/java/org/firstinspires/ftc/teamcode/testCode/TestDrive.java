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

@TeleOp(name = "Actual Meccanum", group = "Test")
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
    double multiplier = 0.5;
    Toggle t = new Toggle();
    Toggle loadToggle = new Toggle();
    Toggle pushToggle = new Toggle();
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
        t.state = false;
        loadToggle.state = false;
        pushToggle.state = false;
    }

    @Override
    public void loop() {

        if (gamepad1.left_trigger != 0 && gamepad1.right_trigger == 0) {
            multiplier = 0.25;
        } else {
            multiplier = 0.5;
        }

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger != 0) {
            multiplier = 1;
        } else {
            multiplier = 0.5;
        }

        double fr = gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x;
        double br = gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;
        double fl = -gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;
        double bl = -gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x;
        frontRight.setPower(fr * multiplier);
        backRight.setPower(br * multiplier);
        frontLeft.setPower(fl * multiplier);
        backLeft.setPower(bl * multiplier);

        System.out.println("FRPOW: " + fr);
        System.out.println("BRPOW: " + br);
        System.out.println("FLPOW: " + fl);
        System.out.println("BLPOW: " + bl);


        if (gamepad2.a) {
            t.onPress();
        } else {
            t.onRelease();
        }

        if (gamepad2.b) {
            loadToggle.onPress();
        } else {
            loadToggle.onRelease();
        }

        if (gamepad2.right_bumper) {
            pushToggle.onPress();
        } else {
            pushToggle.onRelease();
        }

        if (pushToggle.state) {
            pusher.setPosition(0);
        } else {
            pusher.setPosition(.5);
        }

        if (loadToggle.state) {
            loader.setPosition(26.0/180);
        } else {
            loader.setPosition(0.3);
        }

//        pusher.setPosition(gamepad2.left_trigger-1);
        System.out.println(gamepad2.left_trigger);
        if (gamepad2.left_trigger != 0 && gamepad2.right_trigger == 0) {
            loader.setPosition(0.3);
            intake.setPower(1);
            intakeServo.setPower(-1);
        } else {
            intake.setPower(0);
            intakeServo.setPower(0);
        }

        if (gamepad2.right_trigger != 0 && gamepad2.left_trigger == 0) {
            loader.setPosition(0.3);
            intake.setPower(-1);
            intakeServo.setPower(1);
        } else {
            intake.setPower(0);
            intakeServo.setPower(0);
        }

        if (t.state) {
            flywheel1.setPower(1);
            flywheel2.setPower(1);
        } else {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }

//        if (gamepad2.right_bumper && !gamepad2.left_bumper) {
//            intake.setPower(1);
//            intakeServo.setPower(-1);
//        } else {
//            intake.setPower(0);
//            intakeServo.setPower(0);
//        }

        if (gamepad2.left_bumper && !gamepad2.right_bumper) {
            intake.setPower(-1);
            intakeServo.setPower(1);
        } else {
            intake.setPower(0);
            intakeServo.setPower(0);
        }

    }
}
