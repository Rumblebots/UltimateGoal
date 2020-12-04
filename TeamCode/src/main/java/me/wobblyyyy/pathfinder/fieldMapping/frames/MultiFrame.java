package me.wobblyyyy.pathfinder.fieldMapping.frames;

import me.wobblyyyy.pathfinder.fieldMapping.TwoDimensionalRobot;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;

import java.util.ArrayList;

public class MultiFrame {
    public static ArrayList<Frame> frames = new ArrayList<>();

    public static void addFrame(ArrayList<Zone> zones,
                                HeadingCoordinate<Double> position) {
        final TwoDimensionalRobot r = new TwoDimensionalRobot(18, 18, 0);
        r.update(position);
        frames.add(
                new Frame(
                        new ArrayList<Zone>(zones) {{
                            add(r.zone);
                        }}
                )
        );
    }

    /**
     * Check all of the frames in the MultiFrame.
     *
     * @return false if there are any collisions between the robot and the field, true if there aren't.
     */
    public static boolean checkFrames() {
        for (Frame f : frames) {
            if (f.robotCollisions.size() > 0) return false;
        }
        return true;
    }

    /**
     * Clear all of the frames.
     */
    public static void clearFrames() {
        frames.clear();
    }
}
