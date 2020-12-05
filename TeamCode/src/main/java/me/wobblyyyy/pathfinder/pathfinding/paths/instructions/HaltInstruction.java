package me.wobblyyyy.pathfinder.pathfinding.paths.instructions;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder.pathfinding.managers.RobotManager;

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
        robotManager.setPfMotorPower(new PfMotorPower(
                0, // front-right
                0, // front-left
                0, // back-right
                0  // back-left
        ));
    }
}
