package me.wobblyyyy.pathfinder.localizer;

import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

public class Implementation {
    public static HeadingCoordinate<Double> fromOdometryPosition(OdometryPosition odometryPosition) {
        return new HeadingCoordinate<>(
                odometryPosition.getX(),
                odometryPosition.getY(),
                odometryPosition.getHeadingRadians()
        );
    }
}
