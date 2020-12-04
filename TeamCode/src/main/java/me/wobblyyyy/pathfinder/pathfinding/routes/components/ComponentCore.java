package me.wobblyyyy.pathfinder.pathfinding.routes.components;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

public interface ComponentCore {
    PfMotorPower execute();
    boolean startCondition();
    boolean finishCondition();
}
