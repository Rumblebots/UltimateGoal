package me.wobblyyyy.pathfinder.fieldMapping.pathfinding.routes;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.MapApi;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.frames.MultiFrame;
import me.wobblyyyy.pathfinder.localizer.Odometry;

import java.util.ArrayList;

/**
 * A system used for checking the validity of different routes.
 *
 * <p>
 * This uses {@link MultiFrame} to generate multiple frames at a time and check for collisions from
 * there. Obviously, this isn't the most effective or safe way to do this. However, it (mostly) seems
 * to work for me, so who really cares anyways?
 * </p>
 *
 * @author Colin Robertson
 */
public class RouteChecker {
    /**
     * A reference to the LL odometry system.
     */
    private Odometry odometry;

    /**
     * A reference to the field's map.
     */
    private Map map;

    /**
     * A reference to the map's API.
     */
    private MapApi mapApi;

    /**
     * Constructor for the route checker.
     *
     * @param odometry the LL odometry system to use.
     * @param map the map to use.
     * @param mapApi the map api to use.
     */
    public RouteChecker(Odometry odometry,
                        Map map,
                        MapApi mapApi) {
        this.odometry = odometry;
        this.map = map;
        this.mapApi = mapApi;
    }

    /**
     * Check whether or not a given route is valid.
     *
     * @param route the route to check.
     * @return whether or not the route's tests end up passing.
     */
    public boolean checkRoute(Route route) {
        MultiFrame.clearFrames();
        ArrayList<HeadingCoordinate<Double>> tests = route.getTests();
        for (HeadingCoordinate<Double> h : tests) {
            // For each and every one of the test positions, we
            // have to check the position to see if it's still
            // valid or not.
            MultiFrame.addFrame(new ArrayList<>(map.fieldZones.list), h);
        }
        return MultiFrame.checkFrames();
    }
}
