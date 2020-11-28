package me.wobblyyyy.pathfinder.fieldMapping.components.countable;

import me.wobblyyyy.intra.ftc2.utils.math.Comparator;
import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

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
