package org._11253.lib.odometry.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

import java.util.ArrayList;

public class Map {
    private ArrayList<Zone> fieldZones = new ArrayList<>();
    public Zone robotZone;

    public ArrayList<Zone> getFieldZones() {
        return fieldZones;
    }

    public ArrayList<Zone> getFieldZonesWithRobot() {
        ArrayList<Zone> zones = fieldZones;
        zones.add(robotZone);
        return zones;
    }

    public ArrayList<Zone> getFieldZonesWithRobotAtPosition(HeadingCoordinate<Double> position) {
        ArrayList<Zone> zones = fieldZones;
        TwoDimensionalRobot robot = new TwoDimensionalRobot(18, 18, position.getHeading());
        robot.update(new Pose2d(18, 18, position.getHeading()));
        zones.add(robot.zone);
        return zones;
    }
}
