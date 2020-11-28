package me.wobblyyyy.pathfinder.fieldMapping.frames;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiFrame {
    ArrayList<HeadingCoordinate<Double>> positionsToTest = new ArrayList<>();

    HashMap<HeadingCoordinate<Double>, Frame> frames = new HashMap<>();

    public MultiFrame(ArrayList<HeadingCoordinate<Double>> positionsToTest) {
        this.positionsToTest = positionsToTest;
        for (HeadingCoordinate<Double> coordinate : positionsToTest) {

        }
    }
}
