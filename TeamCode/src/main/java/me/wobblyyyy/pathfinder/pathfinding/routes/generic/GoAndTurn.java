package me.wobblyyyy.pathfinder.pathfinding.routes.generic;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.pathfinding.routes.HighRoute;
import me.wobblyyyy.pathfinder.pathfinding.routes.components.ComponentCore;
import me.wobblyyyy.pathfinder.pathfinding.routes.components.MoveComponent;
import me.wobblyyyy.pathfinder.pathfinding.routes.components.TurnComponent;

import java.util.ArrayList;

/**
 * The most basic of the routes - start by moving to the target position,
 * and then turn after getting there.
 *
 * <p>
 * This will probably be used the most frequently, until a go-and-turn
 * at the same time route is developed, which will happen later. For now,
 * however, this is your best bet - I can only assume this will be the most
 * frequently used route.
 * </p>
 *
 * @author Colin Robertson
 */
public class GoAndTurn extends HighRoute {
    /**
     * Odometry reference.
     */
    private final Odometry odometry;

    /**
     * Constructor.
     *
     * @param odometry LL odometry sys.
     * @param start start pos.
     * @param target end pos.
     */
    public GoAndTurn(Odometry odometry,
                     HeadingCoordinate<Double> start,
                     HeadingCoordinate<Double> target) {
        super(start, target);
        this.odometry = odometry;
    }

    @Override
    public ArrayList<Line> getLines() {
        return new ArrayList<Line>() {{
            add(
                    new Line(
                            getStart().getCoordinate(),
                            getTarget().getCoordinate()
                    )
            );
        }};
    }

    /**
     * All of the movement-related components.
     *
     * @return all of the movement-related components.
     */
    @Override
    public ArrayList<ComponentCore> getComponents() {
        return new ArrayList<ComponentCore>() {{
            add(new MoveComponent(
                    odometry,
                    getStart(),
                    getTarget()
            ));
            add(new TurnComponent(
                    odometry,
                    getTarget().getHeading()
            ));
        }};
    }

    /**
     * Tests, used in route-checking.
     *
     * @return an ArrayList of tests.
     */
    @Override
    public ArrayList<HeadingCoordinate<Double>> getTests() {
        final double heading = getTarget().getHeading();
        return new ArrayList<HeadingCoordinate<Double>>() {{
            add(
                    new HeadingCoordinate<>(
                            getStart().getX(),
                            getStart().getY(),
                            heading
                    )
            );
            add(
                    new HeadingCoordinate<>(
                            new Line(
                                    getStart().getCoordinate(),
                                    getTarget().getCoordinate()
                            ).midpoint.getX(),
                            new Line(
                                    getStart().getCoordinate(),
                                    getTarget().getCoordinate()
                            ).midpoint.getY(),
                            getTarget().getHeading()
                    )
            );
            add(
                    new HeadingCoordinate<>(
                            getTarget().getX(),
                            getTarget().getY(),
                            heading
                    )
            );
        }};
    }

    /**
     * Get the total length of the route.
     *
     * @return total length of the route.
     */
    @Override
    public double getTotalLength() {
        return getOptimalLength();
    }
}
