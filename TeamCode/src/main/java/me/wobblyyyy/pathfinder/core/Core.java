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
    private Map map;
    private Odometry odometry;
    private PfcManager pfcManager;
    private ZoneGenerator zoneGenerator;

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

    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }

    public ArrayList<Coordinate<Double>> getPathFromPoint(Coordinate<Double> start,
                                                          Coordinate<Double> end) {
        return pfcManager.finder.getCoordinatePath(
                start,
                end
        );
    }

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
