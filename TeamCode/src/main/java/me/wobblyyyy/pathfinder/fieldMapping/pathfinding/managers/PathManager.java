package me.wobblyyyy.pathfinder.fieldMapping.pathfinding.managers;

import me.wobblyyyy.pathfinder.fieldMapping.pathfinding.paths.PlannedLinearPath;

import java.util.ArrayList;
import java.util.Arrays;

public class PathManager {
    public ArrayList<PlannedLinearPath> pathQueue = new ArrayList<>();

    public void runQueue() {

    }

    public void stopQueue() {

    }

    public void clearQueue() {

    }

    public void addToQueue(PlannedLinearPath... plannedLinearPaths) {
        pathQueue.addAll(Arrays.asList(plannedLinearPaths));
    }
}
