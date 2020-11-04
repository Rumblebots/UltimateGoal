package org._11253.lib.odometry.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.Odometry;
import org._11253.lib.odometry.fieldMapping.frames.Frame;
import org._11253.lib.odometry.fieldMapping.zones.Zone;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A public-facing interface for accessing the field mapping system.
 */
public class MapApi {
    /**
     * The map which is used.
     *
     * <p>
     * There are a few preset maps contained in this
     * library, but you might want to end up making
     * your own anyways - who knows.
     * </p>
     */
    Map map = new Map();
    TwoDimensionalRobot robot = new TwoDimensionalRobot(0, 0, 0);
    HashMap<Integer, ArrayList<Zone>> positionWithPriorities = new HashMap<>();
    Odometry odometry;
    private final String mappingName = "_1125c_MAPPING_ODO_UPDATE";

    /**
     * Schedule an asynchronous updater to automagically update the
     * position of the robot's odometry subsystem for us.
     *
     * @param odometry the instance of odometry to use.
     * @param duration how long in between updates.
     */
    public void scheduleAsync(final Odometry odometry, int duration) {
        StringEvents.schedule(
                mappingName,
                duration,
                0,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                update(odometry.getPose());
                            }
                        };
                    }
                },
                true
        );
    }

    /**
     * Cancel an already-scheduled asynchronous event.
     */
    public void cancelAsync() {
        StringEvents.clear(mappingName);
    }

    /**
     * Import a map to use.
     *
     * <p>
     * This should probably be used exclusively in the constructor
     * of this class, as you can't do much with map importation
     * after everything has already been set up.
     * </p>
     *
     * @param map the map to import.
     */
    public void importMap(Map map) {
        this.map = map;
    }

    /**
     * Create a new MapApi instance using a map.
     *
     * @param map the field map to use. If in doubt, you can use UltimateGoal,
     *            SkyStone, or any of the testing maps. Honestly, it doesn't
     *            matter in the slightest - it just has to be a map.
     */
    public MapApi(Map map) {
        importMap(map);
    }

    /**
     * Update the whole map system.
     *
     * <p>
     * This will probably end up being done automatically, so you likely don't have
     * to worry about manually updating it.
     * </p>
     *
     * @param pose2d the position of the robot
     */
    public void update(Pose2d pose2d) {
        Frame frame = new Frame(
                new ArrayList<Zone>(
                        map.getFieldZonesWithRobotAtPosition(pose2d).list
                )
        );
        for (Zone z : frame.getRobotPositions()) {
            int priority = z.getZonePriority();
            ArrayList<Zone> list = new ArrayList<>();
            if (positionWithPriorities.containsKey(priority)) {
                list = positionWithPriorities.get(priority);
            }
            Objects.requireNonNull(list).add(z);
            positionWithPriorities.put(priority, list);
        }
    }

    /**
     * Get the list of zones the robot is in as a string.
     *
     * <p>
     * The string is ordered by zone priority. Keep in mind higher priorities
     * override lower ones.
     * </p>
     *
     * @return the list of zones the robot is in.
     */
    public String getPositionsString() {
        String working = "";
        for (HashMap.Entry<Integer, ArrayList<Zone>> e : positionWithPriorities.entrySet()) {
            working += e.getKey() + " {";
            ArrayList<Zone> list = e.getValue();
            int ptr = 0;
            int len = list.size();
            for (Zone z : list) {
                working += "\"" + z.getName() + "\"";
                if (ptr + 1 == len) {
                    working += ", ";
                }
                ptr++;
            }
            working += "} ";
        }
        return working;
    }
}
