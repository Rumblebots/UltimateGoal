package org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping.shapes;

import org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping.Coordinate;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping.Line;

public class Rectangle {
    final Coordinate<Double> frontRight;
    final Coordinate<Double> backRight;
    final Coordinate<Double> frontLeft;
    final Coordinate<Double> backLeft;

    final Line top;
    final Line right;
    final Line bottom;
    final Line left;

    final double lengthTop;
    final double lengthRight;
    final double lengthBottom;
    final double lengthLeft;

    double width;
    double height;

    /**
     * Construct a rectangle.
     *
     * @param fr front right
     * @param br back right
     * @param fl front left
     * @param bl back left
     */
    public Rectangle(Coordinate<Double> fr,
                     Coordinate<Double> br,
                     Coordinate<Double> fl,
                     Coordinate<Double> bl) {
        frontRight = fr;
        backRight = br;
        frontLeft = fl;
        backLeft = bl;

        // Shared with other constructor.
        lengthTop = frontRight.getX() - frontLeft.getX();
        lengthBottom = lengthTop;
        lengthRight = frontRight.getY() - backRight.getY();
        lengthLeft = lengthRight;
        width = lengthTop;
        height = lengthRight;
        top = new Line(frontLeft, frontRight);
        right = new Line(frontRight, backRight);
        bottom = new Line(backRight, backLeft);
        left = new Line(backLeft, frontLeft);
    }

    public Rectangle(Coordinate<Double> fl,
                     double height,
                     double width) {
        frontRight = new Coordinate<Double>(fl.getX() + width, fl.getY());
        backRight = new Coordinate<Double>(frontRight.getX(), frontRight.getY() - height);
        frontLeft = fl;
        backLeft = new Coordinate<Double>(fl.getX(), fl.getY() - height);

        // Shared with other constructor.
        lengthTop = frontRight.getX() - frontLeft.getX();
        lengthBottom = lengthTop;
        lengthRight = frontRight.getY() - backRight.getY();
        lengthLeft = lengthRight;
        width = lengthTop;
        height = lengthRight;
        top = new Line(frontLeft, frontRight);
        right = new Line(frontRight, backRight);
        bottom = new Line(backRight, backLeft);
        left = new Line(backLeft, frontLeft);
    }
}
