package org._11253.lib.odometry.threeWheelOdometry;

import java.util.List;

public class ThreeWheelPositions {
    private final double leftWheelPos;
    private final double rightWheelPos;
    private final double backWheelPos;

    public ThreeWheelPositions(List<Double> wheelPositions) {
        this(wheelPositions.get(0), wheelPositions.get(1), wheelPositions.get(2));
    }

    public ThreeWheelPositions(double leftWheelPos, double rightWheelPos, double midWheelPos) {
        this.leftWheelPos = leftWheelPos;
        this.rightWheelPos = rightWheelPos;
        this.backWheelPos = midWheelPos;
    }

    public double getLeftWheelPos() {
        return leftWheelPos;
    }

    public double getRightWheelPos() {
        return rightWheelPos;
    }

    public double getBackWheelPos() {
        return backWheelPos;
    }
}
