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
import org.firstinspires.ftc.teamcode.ultimategoal.shared.ShooterThread;

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
    CRServo upperIntakeServo;
    Servo intakeMover;
    Servo loader;
    Servo pusher;
    double multiplier = 0.5;
    Toggle t = new Toggle();
    Toggle loadToggle = new Toggle();
    Toggle pushToggle = new Toggle();
    ShooterThread shooterThread;
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel2");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        upperIntakeServo = hardwareMap.get(CRServo.class, "upperIntakeServo");
        intakeMover = hardwareMap.get(Servo.class, "intakeMover");
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
        flywheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        t.state = false;
        loadToggle.state = false;
        pushToggle.state = false;
        intakeMover.setPosition(0.33);
        shooterThread = new ShooterThread(flywheel2);
        shooterThread.start();
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

        double fr = -gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
        double br = -gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        double fl = gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        double bl = gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
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

//        if (gamepad2.b) {
//            loadToggle.onPress();
//        } else {
//            loadToggle.onRelease();
//        }

        if (gamepad2.right_bumper) {
            pusher.setPosition(0.6);
        } else {
            pusher.setPosition(1);
        }
//
//        if (pushToggle.state) {
//        } else {
//            pusher.setPosition(1);
//        }

//        if (loadToggle.state) {
//            loader.setPosition((180.0-36.0)/180.0);
//        } else {
//            loader.setPosition(1);
//        }

        if (gamepad2.left_trigger > gamepad2.right_trigger && gamepad2.left_trigger > 0.3) {
            intake.setPower(1.0);
            intakeServo.setPower(0.8);
            upperIntakeServo.setPower(0.8);
        } else if (gamepad2.right_trigger > gamepad2.left_trigger && gamepad2.right_trigger > 0.3) {
            loader.setPosition(1);
            intake.setPower(-1);
            intakeServo.setPower(-0.8);
            upperIntakeServo.setPower(-0.8);
        } else {
            loader.setPosition((180.0-36.0)/180.0);
            intake.setPower(0);
            intakeServo.setPower(0);
            upperIntakeServo.setPower(0);
        }

        if (t.state) {
            flywheel1.setPower(1);
            flywheel2.setPower(1);
        } else {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }
        telemetry.addData("RPM: ", shooterThread.getSpeed());
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
        shooterThread.stopThread();
    }
}
