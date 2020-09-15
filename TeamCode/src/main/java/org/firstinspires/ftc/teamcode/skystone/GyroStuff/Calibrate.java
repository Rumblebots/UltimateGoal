package org.firstinspires.ftc.teamcode.skystone.GyroStuff;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

/**
 * This OpMode is run to write calibration data from the gyroscope to a json file
 * This data can later be read from other files
 */
@TeleOp (name = "calibrate", group = "default")
public class Calibrate extends OpMode
{
    /*
     * Set up some variables, like our IMU.
     */
    BNO055IMU imu;
    BNO055IMU.Parameters params;
    boolean calib;

    /**
     * Init function, runs when init is clicked in the driver station app
     * This has all the stuff that gets the imu up
     */
    @Override public void init ()
    {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        params = new BNO055IMU.Parameters();
        params.loggingEnabled = true;
        params.loggingTag = "IMU";
        calib = true;
    }

    /**
     * Overrides the app's default start function, inits the imu
     */
    @Override public void start ()
    {
        imu.initialize(params);
        telemetry.clear();
    }

    /**
     * Loop function
     * If the gyro is calibrated to be calibrated then write calibration data
     * Otherwise tell the user that the calibration data is done
     */
    @Override public void loop ()
    {
        if (imu.isGyroCalibrated() && calib)
        {
            telemetry.addLine("writing calibration data");
            telemetry.update();
            BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
            String name = "CalibrationData.json";
            File file = AppUtil.getInstance().getSettingsFile(name);
            ReadWriteFile.writeFile(file, calibrationData.serialize());
            calib = false;
        }
        else telemetry.addLine("finished writing calibration data");
    }

    /**
     * Yeah that's it
     */
    @Override public void stop ()
    {
        telemetry.addLine("yoooooooooooooooooooooooooooooooooo");
        telemetry.update();
    }
}