package me.wobblyyyy.pathfinder.core;

import me.wobblyyyy.pathfinder.fieldMapping.Map;

/**
 * Container for field maps.
 *
 * @author Colin Robertson
 */
public class MapHolder {
    /**
     * Stored map.
     */
    private Map map;

    /**
     * Create a new MapHolder.
     *
     * @param map the map to use.
     */
    public MapHolder(Map map) {
        this.map = map;
    }

    /**
     * Get the map.
     *
     * @return the stored map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Set the map.
     *
     * @param map the new map to set.
     */
    public void setMap(Map map) {
        this.map = map;
    }
}
