/****
 * Made by Tejas Mehta
 * Made on Friday, November 06, 2020
 * File Name: CustomOdometryTest
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.tejasmehta.OdometryCore.OdometryCore;
import org._11253.lib.op.Template;
import org._11253.lib.utils.math.Math;
import org._11253.lib.utils.telem.Telemetry;
import com.tejasmehta.OdometryCore.localization.EncoderPositions;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;

@TeleOp(name = "Odometry Custom Test", group = "Test")
public class CustomOdometryTest extends Template {
    final double WHEEL_DIAMETER = 1.5;
    final double CPR = 1440;
    final double TICKS_TO_INCH = (Math.PI * WHEEL_DIAMETER) / CPR;
    DcMotor motor_right;
    DcMotor motor_left;
    DcMotor motor_back;
    public CustomOdometryTest() {

        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                motor_right = hardwareMap.get(DcMotor.class, "encoder_right");
                motor_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor_left = hardwareMap.get(DcMotor.class, "encoder_left");
                motor_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor_back = hardwareMap.get(DcMotor.class, "encoder_back");
                motor_back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                OdometryCore.initialize(1450, 1.5, 7.83, 7.83, 1);
            }
        });
        onStart.add();
        onStartRun.add();
        run.add(new Runnable() {
            @Override
            public void run() {
                OdometryPosition p = OdometryCore.getInstance().getCurrentPosition(new EncoderPositions(-motor_left.getCurrentPosition(), motor_right.getCurrentPosition(), motor_back.getCurrentPosition()));
                Telemetry.addData(
                    "ODOM_POS",
                    "Odometry Position",
                    ":",
                    " X: " + p.getX() + ", Y: " + p.getY() + ", Heading: " + p.getHeadingDegrees()
                );
                Telemetry.addData( "ODOM_L",
                        "Left Odometry Wheel",
                        ":",
                        " Ticks: " + -motor_left.getCurrentPosition() + " Inches: " + ticksToInches(-motor_left.getCurrentPosition())
                );
                Telemetry.addData( "ODOM_R",
                        "Right Odometry Wheel",
                        ":",
                        " Ticks: " + motor_right.getCurrentPosition() + " Inches: " + ticksToInches(motor_right.getCurrentPosition())
                );
                Telemetry.addData( "ODOM_B",
                        "Back Odometry Wheel",
                        ":",
                        " Ticks: " + motor_back.getCurrentPosition() + " Inches: " + ticksToInches(motor_back.getCurrentPosition())
                );
            }
        });
    }

    double ticksToInches(double ticks) {
        return ticks * TICKS_TO_INCH;
    }
}
