/****
 * Made by Tejas Mehta
 * Made on Friday, November 06, 2020
 * File Name: CustomOdometryTest
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org._11253.lib.op.TeleOp;
import org._11253.lib.op.Template;
import org._11253.lib.utils.math.Math;
import org._11253.lib.utils.telem.Telemetry;
import org.firstinspires.ftc.teamcode.testCode.odometryCoreTest.OdometryPosition;
import org.firstinspires.ftc.teamcode.testCode.odometryCoreTest.math.CoreMath;

public class CustomOdometryTest extends Template {
    final double WHEEL_RADIUS = 1.25;
    final double CPR = 360;
    final double TICKS_TO_INCH = 2 * Math.PI * WHEEL_RADIUS / CPR;
    DcMotor motor_right;
    DcMotor motor_left;
    DcMotor motor_back;
    public CustomOdometryTest() {

        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                motor_right = hardwareMap.get(DcMotor.class, "encoder_right");
                motor_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor_left = hardwareMap.get(DcMotor.class, "encoder_left");
                motor_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor_back = hardwareMap.get(DcMotor.class, "encoder_back");
                motor_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        });
        onStart.add();
        onStartRun.add();
        run.add(new Runnable() {
            @Override
            public void run() {
                OdometryPosition p = CoreMath.getOdometryPosition(ticksToInches(motor_left.getCurrentPosition()), motor_right.getCurrentPosition(), motor_back.getCurrentPosition(), 7.83, 7.83, 5.414);
                Telemetry.addData(
                    "ODOM_POS",
                    "Odometry Position",
                    ":",
                    "X: " + Math.round(p.getX()*100)/100 + ", Y: " + Math.round(p.getY() *100)/100 + ", Heading: " + Math.round(p.getHeading() *100)/100
                );
            }
        });
    }

    double ticksToInches(double ticks) {
        return ticks * TICKS_TO_INCH;
    }
}
