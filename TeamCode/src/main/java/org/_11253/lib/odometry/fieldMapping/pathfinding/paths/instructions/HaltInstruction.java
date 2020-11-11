package org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions;

import org._11253.lib.motors.MotorPower;
import org._11253.lib.odometry.fieldMapping.pathfinding.managers.RobotManager;

public class HaltInstruction implements Instruction {
    private RobotManager robotManager;

    public HaltInstruction(RobotManager robotManager) {
        this.robotManager = robotManager;
    }

    @Override
    public boolean cancellable() {
        return true;
    }

    @Override
    public boolean isTimed() {
        return true;
    }

    @Override
    public int maxTime() {
        return 10000;
    }

    @Override
    public int minTime() {
        return 0;
    }

    @Override
    public boolean conditionStart() {
        return false;
    }

    @Override
    public boolean conditionContinue() {
        return false;
    }

    @Override
    public boolean conditionStop() {
        return false;
    }

    /**
     * Halt the robot.
     *
     * <p>
     * As the name "halt" would suggest, this quite literally just stops everything
     * that's going on. How shocking - how amazing, truly!
     * </p>
     */
    @Override
    public void execute() {
        robotManager.drivetrain.setPower(new MotorPower(
                0, // Front-right
                0, // Front-left
                0, // Back-right
                0  // Back-left
        ));
    }
}
