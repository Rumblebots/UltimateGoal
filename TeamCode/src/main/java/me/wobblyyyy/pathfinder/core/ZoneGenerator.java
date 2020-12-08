package me.wobblyyyy.pathfinder.core;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.RadiusCircle;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Rectangle;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;

import java.util.ArrayList;

/**
 * Used in generating new field zones to prevent any potential collisions between the
 * robot and the field.
 *
 * <p>
 * For reference, the order things are done in here is as follows.
 * <ul>
 *     <li>Any potential 2dRobot instances are removed.</li>
 *     <li>The zones are first assessed on whether or not they're collidable objects.</li>
 *     <li>The zones are sorted into two categories - circles and rectangles.</li>
 *     <li>Circles are given a radius that is 9 inches larger than the current radius.</li>
 *     <li>Rectangles are given lengths that are 4.5 inches longer on all sides.</li>
 *     <li>These new shapes are given zones.</li>
 *     <li>These zones are stored and now available for access.</li>
 *     <li>Clean up excess shit.</li>
 * </ul>
 * </p>
 *
 * @author Colin Robertson
 */
public class ZoneGenerator {
    private final ArrayList<Zone> originalZones;
    private final ArrayList<Zone> newZones = new ArrayList<>();
    private final ArrayList<Zone> sortedRectangles = new ArrayList<>();
    private final ArrayList<Zone> sortedCircles = new ArrayList<>();

    private final double robotWidth;
    private final double robotHeight;
    private final double halfRobotWidth;
    private final double halfRobotHeight;

    private static final double defaultRobotWidth = 18.0;
    private static final double defaultRobotHeight = 18.0;

    public ZoneGenerator(ArrayList<Zone> originalZones) {
        this(
                originalZones,
                defaultRobotWidth,
                defaultRobotHeight
        );
    }

    public ZoneGenerator(ArrayList<Zone> originalZones,
                         double robotWidth,
                         double robotHeight) {
        this.originalZones = originalZones;

        this.robotWidth = robotWidth;
        this.robotHeight = robotHeight;
        this.halfRobotWidth = robotWidth / 2;
        this.halfRobotHeight = robotHeight / 2;

        sortZones();
        createNewRectangles();
        createNewCircles();

        clean();
    }

    private boolean isCollidable(Zone zone) {
        if (zone.isField()) {
            if (zone.getParentShape().isCollidableExterior()) return true;
            return zone.getParentShape().isCollidableInterior();
        }
        return false;
    }

    private boolean isRectangle(Zone zone) {
        return zone.getParentShape() instanceof Rectangle;
    }

    private boolean isCircle(Zone zone) {
        return zone.getParentShape() instanceof RadiusCircle;
    }

    private void sortZones() {
        for (Zone z : originalZones) {
            if (isCollidable(z)) {
                if (isRectangle(z)) sortedRectangles.add(z);
                if (isCircle(z)) sortedCircles.add(z);
            }
        }
    }

    private Zone createNewZone(final Zone zone, final Shape shape) {
        return new Zone() {
            @Override
            public String getName() {
                return zone.getName();
            }

            @Override
            public Shape getParentShape() {
                return shape;
            }

            @Override
            public int getZonePriority() {
                return zone.getZonePriority();
            }

            @Override
            public boolean doesLineEnterZone(Line line) {
                return zone.doesLineEnterZone(line);
            }

            @Override
            public boolean isPointInZone(Coordinate<Double> point) {
                return zone.isPointInZone(point);
            }

            @Override
            public double getDriveSpeedMultiplier() {
                return zone.getDriveSpeedMultiplier();
            }

            @Override
            public int getComponents() {
                return zone.getComponents();
            }

            @Override
            public boolean isField() {
                return zone.isField();
            }

            @Override
            public boolean isCollidableWithField() {
                return zone.isCollidableWithField();
            }
        };
    }

    private void addAndCreateZone(Zone zone, Shape shape) {
        newZones.add(
                createNewZone(
                        zone,
                        shape
                )
        );
    }

    private void createNewRectangles() {
        for (Zone zone : sortedRectangles) {
            addAndCreateZone(
                    zone,
                    createNewRectangle(zone)
            );
        }
    }

    private void createNewCircles() {
        for (Zone zone : sortedCircles) {
            addAndCreateZone(
                    zone,
                    createNewCircle(zone)
            );
        }
    }

    private Rectangle createNewRectangle(Zone zone) {
        Rectangle original = (Rectangle) zone.getParentShape();
        Rectangle updated;

        Rectangle.Corners drawCorner = original.drawCorner;
        Rectangle.Corners rotateCorner = original.rotateCorner;
        Coordinate<Double> startingPoint = original.startingPoint;
        double xDraw = original.xDraw;
        double yDraw = original.yDraw;
        double rotationalAngle = original.rotationalAngle;

        Coordinate<Double> startMod;

        switch (drawCorner) {
            case FRONT_RIGHT:
                startMod = new Coordinate<>(
                        halfRobotWidth,
                        halfRobotHeight
                );
                xDraw += -halfRobotWidth;
                yDraw += -halfRobotHeight;
                break;
            case FRONT_LEFT:
                startMod = new Coordinate<>(
                        -halfRobotWidth,
                        halfRobotHeight
                );
                xDraw += halfRobotWidth;
                yDraw += -halfRobotHeight;
                break;
            case BACK_RIGHT:
                startMod = new Coordinate<>(
                        halfRobotWidth,
                        -halfRobotHeight
                );
                xDraw += -halfRobotWidth;
                yDraw += halfRobotHeight;
                break;
            case BACK_LEFT:
                startMod = new Coordinate<>(
                        -halfRobotWidth,
                        -halfRobotHeight
                );
                xDraw += halfRobotWidth;
                yDraw += halfRobotHeight;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + drawCorner);
        }

        startingPoint = Coordinate.addCoords(
                startingPoint,
                startMod
        );

        updated = new Rectangle(
                drawCorner,
                rotateCorner,
                startingPoint,
                xDraw,
                yDraw,
                rotationalAngle,
                true,
                true
        );

        return updated;
    }

    private RadiusCircle createNewCircle(Zone zone) {
        RadiusCircle original = (RadiusCircle) zone.getParentShape();
        RadiusCircle updated;

        Coordinate<Double> center = original.center;
        double radius = original.radius;
        double hitboxRadius = original.hitboxRadius;

        radius += halfRobotWidth;
        hitboxRadius += halfRobotWidth;

        updated = new RadiusCircle(
                center,
                radius,
                hitboxRadius,
                true,
                true
        );

        return updated;
    }

    private void clean() {

    }

    public ArrayList<Zone> getZones() {
        return newZones;
    }
}
