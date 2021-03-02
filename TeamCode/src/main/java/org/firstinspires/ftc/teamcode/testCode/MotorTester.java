/****
 * Made by Tejas Mehta
 * Made on Thursday, September 24, 2020
 * File Name: MotorTester
 * Package: org.firstinspires.ftc.teamcode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Tester", group = "Test")
@Deprecated
@Disabled
public class MotorTester extends OpMode {
    public static DcMotor motor1;
    public static DcMotor motor2;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotor.class, "Motor1");
        motor2 = hardwareMap.get(DcMotor.class, "Motor2");
    }

    @Override
    public void loop() {
        motor1.setPower(gamepad1.left_stick_y);
        motor2.setPower(gamepad1.right_stick_y);
    }
}
