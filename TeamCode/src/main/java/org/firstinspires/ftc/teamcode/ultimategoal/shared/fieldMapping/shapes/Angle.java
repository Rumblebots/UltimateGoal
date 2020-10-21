package org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping.shapes;

import org._11253.lib.utils.math.Math;

public class Angle {
    private final double O;
    private final double A;
    private final double H;

    public double sineDegrees;      public double sineRadians;
    public double cosineDegrees;    public double cosineRadians;
    public double tangentDegrees;   public double tangentRadians;
    public double cosecantDegrees;  public double cosecantRadians;
    public double secantDegrees;    public double secantRadians;
    public double cotangentDegrees; public double cotangentRadians;

    public Angle(double oppositeLength,
                 double adjacentLength,
                 double hypotenuseLength) {
        O = oppositeLength;
        A = adjacentLength;
        H = hypotenuseLength;

        // Remember: SOHCAHTOA
        sineDegrees = O / H;
        cosineDegrees = A / H;
        tangentDegrees = O / A;
        cosecantDegrees = H / O;
        secantDegrees = H / A;
        cotangentDegrees = A / O;

        sineRadians = Math.toRadians(sineDegrees);
        cosineRadians = Math.toRadians(cosineRadians);
        tangentRadians = Math.toRadians(tangentRadians);
        cosecantRadians = Math.toRadians(cosecantDegrees);
        secantRadians = Math.toRadians(secantDegrees);
        cotangentRadians = Math.toRadians(cotangentDegrees);
    }
}
