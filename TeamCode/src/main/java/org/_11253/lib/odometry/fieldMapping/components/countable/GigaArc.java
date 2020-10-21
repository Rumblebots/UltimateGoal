package org._11253.lib.odometry.fieldMapping.components.countable;

import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.components.Component;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.utils.math.Comparator;
import org._11253.lib.utils.math.Math;

public class GigaArc implements Component {
    public final Coordinate<Double> center;
    public final double radius;
    public final double diameter;
    public final double circumference;
    public final double area;

    public GigaArc(Coordinate<Double> center, double radius) {
        this.center = center;
        this.radius = radius;
        diameter = radius * 2;
        circumference = 2 * Math.PI * radius;
        area = Math.PI * Math.pow(radius, 2);
    }

    public boolean isPointInCircle(Coordinate<Double> point) {
        return Geometry.getDistance(point, center) < radius;
    }

    public boolean isPointOnCircle(Coordinate<Double> point) {
        return isPointOnCircle(point, 0.1);
    }

    public boolean isPointOnCircle(Coordinate<Double> point,
                                   double tolerance) {
        Comparator comparator = new Comparator(0.1);
        return comparator.compare(
                Geometry.getDistance(point, center),
                radius
        );
    }

    public boolean isPointNearCircle(Coordinate<Double> point,
                                     double tolerance) {
        GigaArc circle = new GigaArc(center, radius + tolerance);
        return isPointInCircle(point);
    }
}
