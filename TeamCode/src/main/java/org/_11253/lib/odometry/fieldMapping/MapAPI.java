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
public class MapAPI {
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

    public void scheduleAsync(final Odometry odometry, int duration) {
        StringEvents.schedule(
                "_1125c_MAPPING_ODO_UPDATE",
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

    public void cancelAsync() {
        StringEvents.clear("_1125c_MAPPING_ODO_UPDATE");
    }

    public void importMap(Map map) {
        this.map = map;
    }

    public MapAPI(Map map) {
        importMap(map);
    }

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
