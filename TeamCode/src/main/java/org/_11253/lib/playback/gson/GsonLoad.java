package org._11253.lib.playback.gson;

import com.google.gson.reflect.TypeToken;
import org._11253.lib.playback.ControllerState;

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
    public static HashMap<Long, ControllerState> getHashMap(String jsonString) {
        Type type = new TypeToken<HashMap<Long, ControllerState>>() {}.getType();
        return GsonInterface.gson.fromJson(jsonString, type);
    }
}
