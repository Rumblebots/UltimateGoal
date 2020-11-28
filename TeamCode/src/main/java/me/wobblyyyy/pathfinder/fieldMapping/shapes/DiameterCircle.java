package me.wobblyyyy.pathfinder.fieldMapping.shapes;

import me.wobblyyyy.intra.ftc2.utils.jrep.ListWrapper;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.GigaArc;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

import java.util.ArrayList;

/**
 * Don't use this! Use RadiusCircle. This will get updated later, but for
 * now it's entirely useless.
 *
 * <p>
 * Circles are a lot more complex mathematically than their linear
 * counterparts. I'm writing a lot of code that doesn't really do much
 * right now and I'm not near any pencils and paper so I don't feel like
 * working out the math to figure out how to detect things like line-circle
 * collisions and even circle-circle collisions, but I'm sure I'll get
 * around to that somewhere in the near enough future.
 * </p>
 * <p>
 * If you'll notice, this circle actually has two component circles contained
 * within it - one labelled "circle," and the other; "hitbox." My reasoning
 * behind doing this has nothing to do with liking to type out GigaArc (GigaArc
 * GigaArc GigaArc GigaArc GigaArc GigaArc) and more to do with the fact that
 * circles are a tad bit finicky and I have a feeling the hitbox might
 * come in handy for future collision detection (and hopefully avoidance).
 * </p>
 */
@Deprecated
public class DiameterCircle implements Shape {
    private final GigaArc circle;
    private final GigaArc hitbox;

    public DiameterCircle(Coordinate<Double> center,
                          double diameter,
                          double hitboxDiameter) {
        circle = new GigaArc(center, diameter / 2);
        hitbox = new GigaArc(center, hitboxDiameter / 2);
    }

    @Override
    public boolean isCollidableExterior() {
        return false;
    }

    @Override
    public boolean isCollidableInterior() {
        return false;
    }

    @Override
    public boolean isPointInShape(Coordinate<Double> point) {
        return hitbox.isPointInCircle(point);
    }

    public boolean isPointNearShape(Coordinate<Double> point) {
        return hitbox.isPointInCircle(point);
    }

    @Override
    public boolean doesLineEnterShape(Line line) {
        return false;
    }

    @Override
    public Coordinate<Double> getCenterPoint() {
        return circle.center;
    }

    @Override
    public int getComponentCount() {
        return 2;
    }

    @Override
    public ListWrapper<Component> getComponents() {
        return new ListWrapper<>(
                new ArrayList<Component>() {{
                    add(circle);
                    add(hitbox);
                }}
        );
    }
}
