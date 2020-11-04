package org._11253.lib.odometry.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.zones.Zone;
import org._11253.lib.utils.jrep.ListWrapper;

import java.util.ArrayList;

/**
 * A list of many zones composing a single map.
 *
 * <p>
 * This should (obviously) be extended by other classes. I'd suggest
 * that you add zones to the fieldZone list wrapper by using a constructor,
 * but honestly, I could give less of a shit.
 * </p>
 * <p>
 * I don't even know why you're looking at this, actually. You can (and
 * should) just use an already existing map, as this should be update-to-date.
 * </p>
 */
public class Map {
    /**
     * All of the non-robot zones on the field.
     */
    public ListWrapper<Zone> fieldZones = new ListWrapper<>();

    /**
     * The ROBOT! Yay!
     */
    public TwoDimensionalRobot twoDimensionalRobot;

    /**
     * The zone of the robot.
     *
     * <p>
     * This can also be accessed through twoDimensionalRobot, but it's
     * here just in case you want it. You do you.
     * </p>
     */
    public Zone robotZone;

    /**
     * Get the zones on the field.
     *
     * @return the zones on the field. (robot is not included)
     */
    public ListWrapper<Zone> getFieldZones() {
        return fieldZones;
    }

    /**
     * Get the zones on the field, including the robot.
     *
     * @return get the zones on the field, including the robot.
     */
    public ListWrapper<Zone> getFieldZonesWithRobot() {
        ListWrapper<Zone> zones = fieldZones;
        zones.add(robotZone);
        return zones;
    }

    /**
     * Set the robot to a certain position, and then get all of the
     * zones (including the robot's zone).
     *
     * @param pose2d the position to test the robot at.
     * @return all of the zones, including the robot at a given position.
     */
    public ListWrapper<Zone> getFieldZonesWithRobotAtPosition(Pose2d pose2d) {
        return getFieldZonesWithRobotAtPosition(
                new HeadingCoordinate<Double>(
                        pose2d.getX(),
                        pose2d.getY(),
                        pose2d.getHeading()
                )
        );
    }

    /**
     * Set the robot to a certain position, and then get all of the
     * zones (including the robot's zone).
     *
     * @param position the position to test the robot at.
     * @return all of the zones, including the robot at a given position.
     */
    public ListWrapper<Zone> getFieldZonesWithRobotAtPosition(HeadingCoordinate<Double> position) {
        ListWrapper<Zone> zones = fieldZones;
        TwoDimensionalRobot robot = new TwoDimensionalRobot(18, 18, position.getHeading());
        robot.update(new Pose2d(position.getX(), position.getY(), position.getHeading()));
        robotZone = robot.zone;
        zones.add(robot.zone);
        return zones;
    }
}
