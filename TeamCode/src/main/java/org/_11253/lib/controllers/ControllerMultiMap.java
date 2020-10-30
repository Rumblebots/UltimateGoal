package org._11253.lib.controllers;

import com.qualcomm.robotcore.hardware.Gamepad;
import org._11253.lib.utils.CommandCore;

import java.util.ArrayList;

/**
 * Used to store multiple controller maps at the same time.
 * Useful in multiple bindings.
 *
 * <p>
 * As the JavaDoc description would suggest, this is quite literally just a multi-variable
 * map for the controller. An issue with the first iteration of {@link ControllerMap} is
 * that the HashMap was designed so as to only allow one {@link org._11253.lib.utils.CommandCore}
 * to be mapped to a single state. By using multiple maps and a manager class to manipulate
 * those aforementioned maps, multiple events can be bound to a single {@link ControllerMap.States}.
 * </p>
 */
public class ControllerMultiMap {
    Gamepad gamepad;
    public ArrayList<ControllerMap> maps = new ArrayList<>();

    private ControllerMap createNewMap(Gamepad gamepad) {
        return new ControllerMap(gamepad);
    }

    /**
     * Optimize all of the maps by moving as many bindings as possible into a single map.
     *
     * <p>
     * Although this doesn't result in dramatic performance advantages - and, to be entirely
     * honest, we don't really need dramatic performance advantages - condensing the amount of
     * maps which need to be iterated over, even if it's just by one complete map, should save
     * a fair bit of processing power.
     * </p>
     */
    private void optimizeMaps() {

    }

    /**
     * Delete a map, taking care to ensure it's not the base map.
     *
     * @param index the index to delete.
     */
    private void safeDeleteMap(int index) {
        if (index > 0) maps.remove(index);
    }

    /**
     * Figure out the most effective map to store a new binding on, and map it there.
     *
     * <p>
     * This automatically does the optimization on the go, which is why it's a lot
     * more useful than the significantly less complex bind function.
     * </p>
     */
    public void autoBind(ControllerMap.States states,
                         CommandCore commandCore) {
        boolean hasBeenFound = false;
        int index = 0;
        for (ControllerMap map : maps) {
            if (!map.commandMap.containsKey(states)) {
                hasBeenFound = true;
            }
            if (hasBeenFound) break;
            index++;
        }
        ControllerMap temporary =
                hasBeenFound ?
                        maps.get(index) :
                        createNewMap(gamepad);
        temporary.bind(states, commandCore);
    }

    /**
     * Bind a state to a specific map.
     *
     * @param index       the index to bind to.
     * @param states      the state to bind to.
     * @param commandCore the command to bind.
     */
    public void bind(int index,
                     ControllerMap.States states,
                     CommandCore commandCore) {
        if (index > maps.size() - 1) {
            throw new IndexOutOfBoundsException(
                    "Multi-map index out of bounds. Make sure the index you choose is smaller than the " +
                            "map size minus one."
            );
        }
        maps.get(index).bind(states, commandCore);
    }

    /**
     * Automatically unbind all commands from a given state.
     *
     * <p>
     * Yes, all means all. Any map which contains that state will have the corresponding command
     * entirely removed.
     * </p>
     *
     * @param states the state to unbind.
     */
    public void autoUnbind(ControllerMap.States states) {
        for (ControllerMap map : maps) {
            map.unbind(states);
        }
    }

    /**
     * Unbind a specific state from a specific index.
     *
     * @param index  the index to unbind from.
     * @param states the state to unbind.
     */
    public void unbind(int index,
                       ControllerMap.States states) {
        if (index > maps.size() - 1) {
            throw new IndexOutOfBoundsException(
                    "Multi-map index out of bounds. Make sure the index you choose is smaller than the " +
                            "map size minus one."
            );
        }
        maps.get(index).unbind(states);
    }

    /**
     * Run all of the maps. For more, see {@link ControllerMap#runMap()}
     */
    public void runMaps() {
        for (ControllerMap map : maps) {
            map.runMap();
        }
    }
}
