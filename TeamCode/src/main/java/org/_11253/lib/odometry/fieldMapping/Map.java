package org._11253.lib.odometry.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.zones.Zone;
import org._11253.lib.utils.jrep.ListWrapper;

import java.util.ArrayList;

public class Map {
    public ListWrapper<Zone> fieldZones = new ListWrapper<>();
    public TwoDimensionalRobot twoDimensionalRobot;
    public Zone robotZone;

    public ListWrapper<Zone> getFieldZones() {
        return fieldZones;
    }

    public ListWrapper<Zone> getFieldZonesWithRobot() {
        ListWrapper<Zone> zones = fieldZones;
        zones.add(robotZone);
        return zones;
    }

    public ListWrapper<Zone> getFieldZonesWithRobotAtPosition(Pose2d pose2d) {
        return getFieldZonesWithRobotAtPosition(
                new HeadingCoordinate<Double>(
                        pose2d.getX(),
                        pose2d.getY(),
                        pose2d.getHeading()
                )
        );
    }

    public ListWrapper<Zone> getFieldZonesWithRobotAtPosition(HeadingCoordinate<Double> position) {
        ListWrapper<Zone> zones = fieldZones;
        TwoDimensionalRobot robot = new TwoDimensionalRobot(18, 18, position.getHeading());
        robot.update(new Pose2d(position.getX(), position.getY(), position.getHeading()));
        robotZone = robot.zone;
        zones.add(robot.zone);
        return zones;
    }
}
