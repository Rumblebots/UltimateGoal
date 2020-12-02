package me.wobblyyyy.pathfinder_ftc;

import me.wobblyyyy.pathfinder.fieldMapping.MapApi;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;

import java.util.ArrayList;

/**
 * A utility class containing information about the position of
 * the robot.
 *
 * <p>
 * This class is just used to better organize code - this doesn't provide any additional
 * functionality, so none of that good stuff can be expect. Sorry to presumably (very
 * definitely) let you down there - I know, boring class, boring code, whatever else.
 * </p>
 *
 * @author Colin Robertson
 */
public class Position {
    /**
     * A reference to the odometry system in use.
     *
     * <p>
     * Rather than creating new objects of this type every time we want to change
     * the positional data of the robot, simply having a reference to the odometry
     * system, and then updating it every so often, gets the job done even better.
     * </p>
     */
    private final AbstractOdometry odometry;

    /**
     * A reference to the mapping interface that's in use.
     *
     * <p>
     * This is used for advanced positional data, such as the complete list of zones
     * the robot is in, or anything of the likes. The possibilities, are, of course,
     * absolutely and completely unrestricted - endless, if you will.
     * </p>
     *
     * <p>
     * Rather than creating new objects of this type every time we want to change
     * the positional data of the robot, simply having a reference to the odometry
     * system, and then updating it every so often, gets the job done even better.
     * </p>
     */
    private final MapApi mapApi;

    /**
     * The prefix for any string array.
     */
    private final static String stringArrayOpen = "[";

    /**
     * The separator between different elements of a condensed string array.
     */
    private final static String stringArraySeparator = ", ";

    /**
     * The suffix for any string array.
     */
    private final static String stringArrayClose = "]";

    /**
     * Creates a new instance of a positional data container.
     *
     * @param odometry the odometry system that's in use.
     * @param mapApi   the map API that's in use.
     */
    public Position(AbstractOdometry odometry,
                    MapApi mapApi) {
        this.odometry = odometry;
        this.mapApi = mapApi;
    }

    /**
     * Get the robot's complete position.
     *
     * @return the robot's position (with heading, etc).
     */
    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }

    /**
     * Get the robot's position, as a string.
     *
     * @return the robot's position (string).
     */
    public String getPositionString() {
        return odometry.getPosition().toString();
    }

    /**
     * Get the robot's X pos.
     *
     * @return the robot's X pos.
     */
    public double getX() {
        return odometry.getPosition().getX();
    }

    /**
     * Get the robot's Y pos.
     *
     * @return the robot's Y pos.
     */
    public double getY() {
        return odometry.getPosition().getY();
    }

    /**
     * Get the robot's heading (radians).
     *
     * @return the robot's heading (radians).
     */
    public double getHeading() {
        return odometry.getPosition().getHeading();
    }

    /**
     * Get the robot's X pos.
     *
     * @return the robot's X pos (string).
     */
    public String getXString() {
        return String.valueOf(getX());
    }

    /**
     * Get the robot's Y pos.
     *
     * @return the robot's Y pos (string).
     */
    public String getYString() {
        return String.valueOf(getY());
    }

    /**
     * Get the robot's heading.
     *
     * @return the robot's heading (string).
     */
    public String getHeadingString() {
        return String.valueOf(getHeading());
    }

    /**
     * Get the robot's position (without heading).
     *
     * @return the robot's position.
     */
    public Coordinate<Double> getCoordinate() {
        return odometry.getPosition().getCoordinate();
    }

    /**
     * Get all of the zones the robot is in (as a string).
     *
     * @return a list of all of the zones the robot is in.
     */
    public String getRobotZones() {
        return mapApi.getPositionsString();
    }

    /**
     * Get the names of all of the field zones (as a string, without the robot).
     *
     * @return the name of all of the field zones.
     */
    public String getAllZones() {
        StringBuilder product = new StringBuilder();
        ArrayList<Zone> zones = new ArrayList<>(mapApi.map.fieldZones.list);
        ArrayList<String> zoneNames = new ArrayList<>();

        for (Zone z : zones) {
            zoneNames.add(z.getName());
        }

        int index = 0;
        int maxIndex = zoneNames.size() - 1;

        product.append(stringArrayOpen);
        for (String s : zoneNames) {
            product.append(s);
            if (index == maxIndex) {
                // We're at the end of the list - we don't need to add a separator.
                break;
            } else {
                // We're not at the end of the list - we need to add a separator.
                product.append(stringArraySeparator);
            }
        }
        product.append(stringArrayClose);

        return product.toString();
    }
}
