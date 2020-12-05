package me.wobblyyyy.pathfinder.recording.gson;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

import java.util.HashMap;

/**
 * Save a HashMap used for recording from a JSON string.
 *
 * @author Colin Robertson
 */
public class GsonSave {
    /**
     * Get a JSON string from a HashMap.
     * @param map the map that's used.
     * @return a JSON string - can be saved to a file.
     */
    public static String getJsonString(HashMap<Long, PfMotorPower> map) {
        return GsonInterface.gson.toJson(map);
    }
}
