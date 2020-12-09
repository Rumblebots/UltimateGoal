package me.wobblyyyy.pathfinder.core;

import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.core.pfc.PfcManager;
import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;

/**
 * An interwoven implementation of Pathfinder's field-mapping and
 * PathfindingCore's mathematical approach to finding the quickest
 * possible to path from point A to point B - literally.
 *
 * @author Colin Robertson
 */
public class Core {
    /**
     * The field's map.
     */
    private Map map;

    /**
     * The odometry system of the robot.
     */
    private Odometry odometry;

    /**
     * PathfindingCore manager.
     */
    private PfcManager pfcManager;

    /**
     * Used to generate zones.
     */
    private ZoneGenerator zoneGenerator;

    /**
     * Create a new instance of Core.
     *
     * @param map the field's map.
     * @param odometry the odometry system to be used.
     */
    public Core(Map map,
                Odometry odometry) {
        this.map = map;
        this.odometry = odometry;
        zoneGenerator = new ZoneGenerator(
                new ArrayList<>(map.fieldZones.list),
                0,
                0
        );
        GridCell[][] cells = MapCellConvertor.getCells(
                zoneGenerator.getZones()
        );
        pfcManager = new PfcManager(cells);
    }

    /**
     * Update the suggested power of the pathfinding system.
     *
     * @param startH the start HeadingCoordinate.
     * @param endH the end HeadingCoordinate.
     * @return the new suggested PfMotorPower.
     */
    public PfMotorPower updatePower(HeadingCoordinate<Double> startH,
                                    HeadingCoordinate<Double> endH) {
        Coordinate<Double> start = startH.getCoordinate();
        Coordinate<Double> end = endH.getCoordinate();

        if (!Geometry.isNearPoint(start, end, 1.5)) {
            Line line = new Line(start, end);
            Line fromRobot = new Line(start, end);
            double angle = Math.atan2(
                    end.getY() - start.getY(),
                    end.getX() - start.getX()
            );
            // Let's use a PID control so the robot is nice and accurate.
            // The max value should be 1, which is when the robot goes the
            // fastest. The min value should be as close to 0 as possible,
            // which is when the robot should go slowest.
            // double magnitude = fromRobot.length / line.length;
            double magnitude = 1;
            // FRBL = Front Right & Back Left
            double frbl = Math.sin(angle - (Math.PI / 4)) * magnitude;
            // FLBR = Front Left & Back Right
            double flbr = Math.sin(angle + (Math.PI / 4)) * magnitude;
            double adjustment = Math.max(frbl, flbr);
            frbl /= adjustment;
            flbr /= adjustment;
            return new PfMotorPower(
                    frbl,
                    flbr,
                    frbl,
                    flbr
            );
        } else {
            double current = startH.getHeading();
            double target = endH.getHeading();
            double dist = target - current;
            double original = startH.getHeading();
            double adjustment = dist / original;
            return new PfMotorPower(
                    adjustment,
                    -adjustment,
                    adjustment,
                    -adjustment
            );
        }
    }

    /**
     * Get the current position of the robot.
     *
     * @return the current position of the robot.
     */
    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }

    /**
     * Get a path - to a point, from a point.
     *
     * @param start the start point.
     * @param end the end point.
     * @return an array list of points to follow.
     */
    public ArrayList<Coordinate<Double>> getPathFromPoint(Coordinate<Double> start,
                                                          Coordinate<Double> end) {
        return pfcManager.finder.getCoordinatePath(
                start,
                end
        );
    }

    /**
     * Get a path, from the robot's current position.
     *
     * @param target the target position.
     * @return an array list of points to follow.
     */
    public ArrayList<Coordinate<Double>> getPath(Coordinate<Double> target) {
        if (target.getX() == -1.0 && target.getY() == -1.0) {
            return new ArrayList<>();
        }
        return pfcManager.finder.getCoordinatePath(
                odometry.getPosition().getCoordinate(),
                target
        );
    }
}
