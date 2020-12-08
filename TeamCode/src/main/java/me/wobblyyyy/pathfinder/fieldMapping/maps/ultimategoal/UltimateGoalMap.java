package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue.*;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red.*;

public class UltimateGoalMap extends Map {
    public UltimateGoalMap() {
        fieldZones.add(
                new BlueStartingStack(),
//                new BlueStartLineLeft(),
//                new BlueStartLineRight(),
                new BlueTargetA(),
                new BlueTargetB(),
                new BlueTargetC(),
                new BlueWobbleGoalLeft(),
                new BlueWobbleGoalRight(),
                new RedStartingStack(),
//                new RedStartLineLeft(),
//                new RedStartLineRight(),
                new RedTargetA(),
                new RedTargetB(),
                new RedTargetC(),
                new RedWobbleGoalLeft(),
                new RedWobbleGoalRight()
//                new SharedBlueSide(),
//                new SharedCloseSide(),
//                new SharedFarSide(),
//                new SharedLaunchLine(),
//                new SharedRedSide()
        );
    }
}
