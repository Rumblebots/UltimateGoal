package org.firstinspires.ftc.teamcode.skystone.GyroStuff;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Example demonstrating using gyroscope for turning
 * Note that this doesn't actually do anything because I'm too lazy to add motors and shit,
 * but if you did, then this would turn to the specified target angle
 */
@TeleOp (name = "turn to angle", group = "default")
public class TurnToAngle extends LinearOpMode
{
    /*
     * some variables we'll need
     */
    private static BNO055IMU imu;
    private static Orientation getCurrentOrientation ()
    {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.RADIANS);
    }
    private double getCurrentX ()
    {
        return getCurrentOrientation().firstAngle;
    }
    private double getCurrentY ()
    {
        return getCurrentOrientation().secondAngle;
    }
    private double getCurrentZ ()
    {
        return getCurrentOrientation().thirdAngle;
    } // I think this is the one you want to use
    public void runOpMode ()
    {
        waitForStart();
        /*
         * This is where the init function would normally be
         */
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.calibrationDataFile = "CalibrationData.json";
        params.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(params);
        /*
         * This is where the init function would end
         */
        double target = 50;
        while (opModeIsActive())
        {
            double current = getCurrentZ();
            double distance = current - target;
            double power = Range.clip(distance * 0.1, -1, 1);
            // from here, set motor powers for turning
            // ie right side is positive power, left side is negative power
        }
    }
}
