/****
 * Made by Tejas Mehta
 * Made on Wednesday, November 04, 2020
 * File Name: PolarCoordinate
 * Package: com.tejasmehta.OdometryCore.com.tejasmehta.OdometryCore.math*/
package org.firstinspires.ftc.teamcode.testCode.odometryCoreTest.math;

public class PolarCoordinate {
    private final double r;
    private final double theta;

    public static PolarCoordinate fromCartesian(CartesianCoordinate cartesian) {
        double x = cartesian.getX();
        double y = cartesian.getY();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan(y/x);
        return new PolarCoordinate(r, theta);
    }

    public PolarCoordinate(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }

    public double getR() {
        return r;
    }

    public double getTheta() {
        return theta;
    }
}
