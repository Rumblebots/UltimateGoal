package org.firstinspires.ftc.teamcode.skystone.hardware.v2.Sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Class to access the IMU (inertial measurement unit)
 */
public class Gyroscope
{
    public String HwName;
    public static BNO055IMU IMU;
    public static BNO055IMU.Parameters PARAMETERS = new BNO055IMU.Parameters();
    private Orientation TargetOrientation;
    private Orientation CurrentOrientation;
    public Gyroscope (String hwName)
    {
        HwName = hwName;
    }

    /**
     * Set up the hardware map.
     * This only finishes once the gyro has been calibrated.
     * The while loop makes it wait for it to be calibrated.
     * @param hwMap
     */
    public void init (HardwareMap hwMap)
    {
        IMU = hwMap.get(BNO055IMU.class, HwName);
        PARAMETERS.mode = BNO055IMU.SensorMode.IMU;
        PARAMETERS.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        PARAMETERS.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        PARAMETERS.loggingEnabled = false;
        IMU.initialize(PARAMETERS);
        while (!IMU.isGyroCalibrated());
    }

    /**
     * Returns if the gyro has been calibrated or not
     * @return - gyro calibration status, true = yes, false = no
     */
    public boolean IsGyroCalibrated ()
    {
        return IMU.isGyroCalibrated();
    }

    /**
     * Get the current facing of the gyroscope
     * @return values in X Y and Z form
     * Z is usually the value that you'd want to use - it measures current horizontal plane direction
     */
    private Orientation GetFacing ()
    {
        return IMU.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    }

    /**
     * Public function to get current X value
     * @return - current x value
     */
    public double GetCurrentFacingX ()
    {
        return GetFacing().firstAngle;
    }

    /**
     * Public function to get current Y value
     * @return - current y value
     */
    public double GetCurrentFacingY ()
    {
        return GetFacing().secondAngle;
    }

    /**
     * Public function to get current Z value
     * @return - current Z value
     */
    public double GetCurrentFacingZ ()
    {
        return GetFacing().thirdAngle;
    }

    /**
     * Public function that determines target X
     * @return - target X value
     */
    public double GetTargetFacingX ()
    {
        return GetTargetOrientation().firstAngle;
    }

    /**
     * Public function that determines target Y
     * @return - target Y value
     */
    public double GetTargetFacingY ()
    {
        return GetTargetOrientation().secondAngle;
    }

    /**
     * Public function that determines target Z
     * @return - target Z value
     */
    public double GetTargetFacingZ ()
    {
        return GetTargetOrientation().thirdAngle;
    }

    /**
     * Public function that gets the target orientation
     * @return - target orientation, in orientation form
     */
    public Orientation GetTargetOrientation ()
    {
        return TargetOrientation;
    }

    /**
     * Public function that gets the current orientation
     * @return - current orientation
     */
    public Orientation GetCurrentOrientation ()
    {
        return CurrentOrientation;
    }

    /**
     * Public function that sets the new target orientation
     * @param NewTargetOrientation - new target orientation
     */
    public void SetTargetOrientation (Orientation NewTargetOrientation)
    {
        TargetOrientation = NewTargetOrientation;
    }

    /**
     * Set target orientation x value
     * @param a - new x value to set
     */
    public void SetTargetX (double a)
    {
        TargetOrientation.firstAngle = (float) a;
    }

    /**
     * Set target orientation y value
     * @param a - new y value to set
     */
    public void SetTargetY (double a)
    {
        TargetOrientation.secondAngle = (float) a;
    }

    /**
     * Set target orientation z value
     * @param a - new z value to set
     */
    public void SetTargetZ (double a)
    {
        TargetOrientation.thirdAngle = (float) a;
    }

    /**
     * Refresh what the current orientation is
     */
    public void RefreshCurrentOrientation ()
    {
        CurrentOrientation = GetFacing();
    }

    /**
     * Get how far on X value you have to go
     * @return - distance from target
     */
    public double GetGoalX ()
    {
        return GetCurrentFacingX() - GetTargetFacingX();
    }

    /**
     * Get how far on Y value you have to go
     * @return - distance from target
     */
    public double GetGoalY ()
    {
        return GetCurrentFacingY() - GetTargetFacingY();
    }

    /**
     * Get how far on Z value you have to go
     * @return - distance from target
     */
    public double GetGoalZ ()
    {
        return GetCurrentFacingZ() - GetTargetFacingZ();
    }

    /**
     * Return if the current orientation matches the goal one
     * @return - boolean whether it's at the goal orientation
     */
    public boolean IsAtGoalRotation ()
    {
        return true;
    }
}
