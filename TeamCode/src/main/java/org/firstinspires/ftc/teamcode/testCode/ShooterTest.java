/****
 * Made by Tejas Mehta
 * Made on Tuesday, January 05, 2021
 * File Name: ShooterTest
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org._11253.lib.utils.gen.Toggle;


@TeleOp(name = "Shooter Test", group = "Test")
public class ShooterTest extends OpMode {
    DcMotor shooter1;
    DcMotor shooter2;

    Servo pusher;
    Servo loader;

    Toggle shootToggle = new Toggle();
    Toggle loadToggle = new Toggle();
    Toggle pushToggle = new Toggle();

    @Override
    public void init() {
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
        shooter1 = hardwareMap.get(DcMotor.class, "flywheel1");
        shooter2 = hardwareMap.get(DcMotor.class, "flywheel2");
        shootToggle.state = false;
        loadToggle.state = false;
        pushToggle.state = false;
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

        if (gamepad2.right_bumper) {
            pushToggle.onPress();
        } else {
            pushToggle.onRelease();
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
