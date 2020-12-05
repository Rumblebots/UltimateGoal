package me.wobblyyyy.pathfinder.recording.gson;

import com.google.gson.reflect.TypeToken;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Load a HashMap used for recording from a JSON string.
 *
 * @author Colin Robertson
 */
public class GsonLoad {
    /**
     * Get a HashMap from a string.
     *
     * @param jsonString a string, likely read from a file, that represents a
     *                   prepared motor power plan.
     * @return a recording-implementable string.
     */
    public static HashMap<Long, PfMotorPower> getHashMap(String jsonString) {
        Type type = new TypeToken<HashMap<Long, PfMotorPower>>() {
        }.getType();
        return GsonInterface.gson.fromJson(jsonString, type);
    }
}
