package me.wobblyyyy.pathfinder.pathfinding.managers;

import me.wobblyyyy.pathfinder.fieldMapping.TwoDimensionalRobot;
import me.wobblyyyy.pathfinder.pathfinding.Pathfinder;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.DrivetrainRegulationSystem;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.OdometryRegulationSystem;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.UserInputRegulationSystem;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * Used to manage all of the physical aspects of the robot.
 */
public class RobotManager {
    public DrivetrainRegulationSystem drivetrain;
    public OdometryRegulationSystem odometry;
    public UserInputRegulationSystem user;
    public TwoDimensionalRobot twoDimensionalRobot;
    private Pathfinder pathfinder;

    public RobotManager(DrivetrainRegulationSystem drivetrain,
                        OdometryRegulationSystem odometry,
                        UserInputRegulationSystem user,
                        TwoDimensionalRobot twoDimensionalRobot,
                        Pathfinder pathfinder) {
        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.user = user;
        this.twoDimensionalRobot = twoDimensionalRobot;
        this.pathfinder = pathfinder;
    }

    /**
     * Can the user control the robot?
     *
     * <p>
     * Overrides should still be processed even if the user shouldn't be
     * controlling the robot.
     * </p>
     */
    public boolean isUserControlled = true;

    /**
     * Prevent the user from controlling the robot.
     */
    public void disableUserControl() {

    }

    /**
     * Allow the user to control the robot.
     */
    public void enableUserControl() {

    }

    public void setPfMotorPower(PfMotorPower pfMotorPower) {
        pathfinder.setPfMotorPower(pfMotorPower);
    }
}
