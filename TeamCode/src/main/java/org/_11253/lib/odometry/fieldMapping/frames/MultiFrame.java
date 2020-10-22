package org._11253.lib.odometry.fieldMapping.frames;

import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiFrame {
    ArrayList<HeadingCoordinate<Double>> positionsToTest;

    HashMap<HeadingCoordinate<Double>, Frame> frames;

    public MultiFrame(ArrayList<HeadingCoordinate<Double>> positionsToTest) {
        this.positionsToTest = positionsToTest;
        for (HeadingCoordinate<Double> coordinate : positionsToTest) {

        }
    }
}
