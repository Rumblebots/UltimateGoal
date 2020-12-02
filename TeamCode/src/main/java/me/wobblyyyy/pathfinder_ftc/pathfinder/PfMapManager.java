package me.wobblyyyy.pathfinder_ftc.pathfinder;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.MapApi;
import me.wobblyyyy.pathfinder.localizer.Odometry;

public class PfMapManager {
    /**
     * A reference to the Odometry in use.
     */
    private Odometry odometry;

    /**
     * A reference to the Map in use.
     */
    public Map map;

    /**
     * A reference to the MapApi in use.
     */
    public MapApi mapApi;

    public PfMapManager(Map map, Odometry odometry) {
        this.map = map;
        this.mapApi = new MapApi(map);
        this.odometry = odometry;
    }

    /**
     * Schedule an asynchronous event to update the mapping system.
     *
     * @param duration how long in-between updates.
     */
    public void scheduleAsync(int duration) {
        mapApi.scheduleAsync(odometry, duration);
    }

    /**
     * Cancel the async event scheduled to automatically update the mapping system.
     */
    public void cancelAsync() {
        mapApi.cancelAsync();
    }
}
