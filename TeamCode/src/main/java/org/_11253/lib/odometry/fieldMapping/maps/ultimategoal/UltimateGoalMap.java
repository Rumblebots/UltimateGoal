package org._11253.lib.odometry.fieldMapping.maps.ultimategoal;

import org._11253.lib.odometry.fieldMapping.Map;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue.*;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red.*;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.shared.*;

public class UltimateGoalMap extends Map {
    public UltimateGoalMap() {
        fieldZones.add(
                new BlueStartingStack(),
                new BlueTargetA(),
                new BlueTargetB(),
                new BlueTargetC(),
                new BlueWobbleGoalLeft(),
                new BlueWobbleGoalRight(),
                new RedStartingStack(),
                new RedTargetA(),
                new RedTargetB(),
                new RedTargetC(),
                new RedWobbleGoalLeft(),
                new RedWobbleGoalRight(),
                new SharedBlueSide(),
                new SharedCloseSide(),
                new SharedFarSide(),
                new SharedLaunchLine(),
                new SharedRedSide()
        );
    }
}
