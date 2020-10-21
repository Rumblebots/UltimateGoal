package org._11253.lib.odometry.fieldMapping.shapes;

import org._11253.lib.utils.math.Math;
import org._11253.lib.odometry.fieldMapping.Coordinate;

public class RightTriangle {
    public Angle angleA;
    public Angle angleB;

    public double sideA;
    public double sideB;
    public double sideC;

    public RightTriangle(Coordinate<Double> target) {
        // Angle A is opposite of Side A.
        // Angle B is opposite of Side B.
        // Angle C is opposite of Side C. Angle C is a right angle, Side C is the hypotenuse.
        // Side A is the distance (x) from target.
        // Side B is the distance (y) from target.
        // Side C is the hypotenuse, or the distance from the origin to the target.
        sideA = target.getX();
        sideB = target.getY();
        sideC = Math.hypot(sideA, sideB);

        // Angle Name | Adjacent | Opposite | Hypotenuse
        // A          | Side B   | Side A   | Side C
        // B          | Side A   | Side B   | Side C
        angleA = new Angle(sideA, sideB, sideC);
        angleB = new Angle(sideA, sideB, sideC);
    }
}
