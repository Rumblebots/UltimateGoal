package org._11253.lib.odometry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;

public interface Odometry {
    Pose2d getPose();
    HeadingCoordinate<Double> getPosition();
}
