/****
 * Made by Tejas Mehta
 * Made on Tuesday, January 05, 2021
 * File Name: ShooterTest
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org._11253.lib.utils.gen.Toggle;


@TeleOp(name = "Shooter Test", group = "Test")
public class ShooterTest extends OpMode {
    DcMotor shooter1;
    DcMotor shooter2;

    Servo pusher;
    Servo loader;

    Servo intakeMover;
    CRServo intakeServo;
    CRServo upperIntakeServo;
    DcMotor intakeMotor;

    Toggle shootToggle = new Toggle();
    Toggle loadToggle = new Toggle();
    Toggle pushToggle = new Toggle();
    Toggle intakeToggle = new Toggle();

    @Override
    public void init() {
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
        shooter1 = hardwareMap.get(DcMotor.class, "flywheel1");
        shooter2 = hardwareMap.get(DcMotor.class, "flywheel2");
        intakeMover = hardwareMap.get(Servo.class, "intakeMover");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        upperIntakeServo = hardwareMap.get(CRServo.class, "upperIntakeServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        shootToggle.state = false;
        loadToggle.state = false;
        pushToggle.state = false;
        intakeToggle.state = false;
        intakeMover.setPosition(0.35);
    }

    @Override
    public void loop() {
        if (gamepad2.a) {
            shootToggle.onPress();
        } else {
            shootToggle.onRelease();
        }

        if (gamepad2.b) {
            loadToggle.onPress();
        } else {
            loadToggle.onRelease();
        }

        if (gamepad2.x) {
            intakeToggle.onPress();
        } else {
            intakeToggle.onRelease();
        }

        if (gamepad2.right_bumper) {
            pushToggle.onPress();
        } else {
            pushToggle.onRelease();
        }

        if (intakeToggle.state) {
            intakeMotor.setPower(-1.0);
            intakeServo.setPower(-0.8);
            upperIntakeServo.setPower(0.8);
        } else {
            intakeMotor.setPower(0.0);
            intakeServo.setPower(0.0);
            upperIntakeServo.setPower(0.0);
        }

        if (pushToggle.state) {
            pusher.setPosition(0.6);
        } else {
            pusher.setPosition(1);
        }

        if (loadToggle.state) {
            loader.setPosition((180.0-36.0)/180.0);
        } else {
            loader.setPosition(1);
        }

        if (shootToggle.state) {
            shooter1.setPower(1);
            shooter2.setPower(1);
        } else {
            shooter1.setPower(0);
            shooter2.setPower(0);
        }
    }
}
