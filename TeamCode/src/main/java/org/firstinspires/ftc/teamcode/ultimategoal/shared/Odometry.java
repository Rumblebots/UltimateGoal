package org.firstinspires.ftc.teamcode.ultimategoal.shared;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotor;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.utils.math.Math;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.OdometryWheels;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * (Hopefully) functional odometry which does cool stuff, like
 * odometry things and stuff.
 * <p>
 *     <a href="https://drive.google.com/file/d/1G6-VJjLn1lfbt6sMBetEOPQ2eBSudIti/view"></a>
 *     Last timestamp: 51:18
 * </p>
 */
public class Odometry extends ThreeTrackingWheelLocalizer {
    public Pose2d currentPose = new Pose2d();

    private OdometryWheels wheels;

    /**
     * A HashMap of all of our motors. You can access these by using
     * the getMotor() function and one of the enumerated wheels.
     */
    final HashMap<OdometryWheels.wheels, Motor> motors;

    /**
     * Another HashMap, also of our motors.
     * <p>
     * Rather than using our cool (very cool, indeed, the coolest, even)
     * and swaggy custom motors, these are FTC's default motors. In 
     * addition to being very lame, uncool, and not very swaggy (sadly),
     * these motors give you a much more direct access to the position
     * of the motor's encoder. You don't need it, but RoadRunner
     * (apparently) does, and I don't feel like re-writing an entire 2d
     * motion planning library. 
     * </p>
     */
    final HashMap<OdometryWheels.wheels, DcMotor> dcMotors;

    private DcMotor getMotor(OdometryWheels.wheels wheel) {
        return dcMotors.get(wheel);
    }

    private double getPositionTicks(OdometryWheels.wheels wheel) {
        return Objects.requireNonNull(getMotor(wheel)).getCurrentPosition();
    }

    private double getPositionInches(OdometryWheels.wheels wheel) {
        return getInches(getPositionTicks(wheel));
    }

    /**
     * The radius of the odometry wheels.
     * <p>
     * All three wheels are the same size, so this should
     * remain fairly consistent.
     * Holy fucking shit, my balls itch.
     * </p>
     */
    final double wheelRadius = 1.25;

    /**
     * CPR stands for "Counts Per Rotation."
     * <p>
     * Yep. Swag. That's all you need to know.
     * </p>
     */
    final double cpr = 360;

    /**
     * A measurement used to convert "ticks" (whatever those are)
     * into inches (a much more common measurement).
     * <p>
     * I'm assuming that, like any reasonable person, you'll
     * probably prefer to use the function literally RIGHT
     * BELOW this, but I wouldn't really know. You do you
     * lovie.
     * </p>
     */
    final double ticksToInch = 2 * Math.PI * wheelRadius / cpr;

    /**
     * Get a measurement (in inches) from a measurement in ticks.
     *
     * @param ticks a tick count
     * @return inches
     */
    public double getInches(double ticks) {
        return ticks * ticksToInch;
    }

    /**
     * Constructor! Yay! These are the positions of each of the three wheels used here.
     */
    public Odometry(OdometryWheels wheels) {
        super(wheels.getMotorArray());
        motors = wheels.getMotorMap();
        dcMotors = wheels.getDcMotorMap();
        this.wheels = wheels;
    }

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                getPositionInches(OdometryWheels.wheels.LEFT),
                getPositionInches(OdometryWheels.wheels.RIGHT),
                getPositionInches(OdometryWheels.wheels.BACK)
        );
    }

    public void updateOdometry() {
        this.update();
        currentPose = this.getPoseEstimate();
    }
}
