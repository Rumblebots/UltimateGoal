package org._11253.lib.odometry.fieldMapping.pathfinding.managers;

import org._11253.lib.odometry.fieldMapping.TwoDimensionalRobot;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.DrivetrainRegulationSystem;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.OdometryRegulationSystem;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.UserInputRegulationSystem;

/**
 * Used to manage all of the physical aspects of the robot.
 */
public class RobotManager {
    public DrivetrainRegulationSystem drivetrain;
    public OdometryRegulationSystem odometry;
    public UserInputRegulationSystem user;
    public TwoDimensionalRobot twoDimensionalRobot;

    public RobotManager(DrivetrainRegulationSystem drivetrain,
                        OdometryRegulationSystem odometry,
                        UserInputRegulationSystem user,
                        TwoDimensionalRobot twoDimensionalRobot) {
        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.user = user;
        this.twoDimensionalRobot = twoDimensionalRobot;
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
}
