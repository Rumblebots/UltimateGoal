package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

// TODO actually finish this lmfao

/**
 * @author Colin Robertson
 */
public class Pathfinder {
    private PfRobot pfRobot;
    private PfRoute pfRoute;

    private HeadingCoordinate<Double> position;

    // todo make this a member variable
    public static PfMotorPower pfMotorPower = new PfMotorPower(0, 0, 0, 0);

    public Pathfinder() {

    }

    public void generateRoutes() {

    }

    public void updatePosition() {

    }

    public void tickPathfinder() {

    }
}
