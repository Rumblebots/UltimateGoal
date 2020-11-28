package me.wobblyyyy.pathfinder.fieldMapping.components;

import me.wobblyyyy.intra.ftc2.utils.math.Math;

/**
 * A geometric angle.
 *
 * <p>
 * This may be a little bit laggy - you've been warned.
 * </p>
 * <p>
 * Although this probably won't be used very often -
 * there's all but no point in using an angle unless
 * you're doing a very low-level geometric implementation -
 * I figured that I'd leave this here anyway.
 * </p>
 * <p>
 * I had originally planned on using right triangles and
 * some basic trig to figure out where angles are without
 * using rotational matricies and linear algebra, but I
 * decided to go ahead and use both of those as it's more
 * convientient to use.
 * </p>
 */
public class Angle implements Component {
    /** Opposite side length. */
    private final double O;
    /** Adjacent side length. */
    private final double A;
    /** Hypotenuse side length. */
    private final double H;

    // All of our trig functions, in both degrees and radians.
    // Radians are pretty cool - way cooler than degrees, in
    // fact, but there's not really any need to have them. 
    // This class (probably and hopefully) won't be used too much,
    // and I'd suggest you try to keep that usage low. The
    // constructor of this class does a lot of math, so creating
    // multiple instances of this in every loop cycle of the op-mode
    // might cause a little bit of lag.
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
        // Sine is opposite over hypotenuse.
        // Cosine is adjacent over hypotenuse.
        // Tangent is opposite over adjacent.
        // Cosecant is the reciprocal of sine.
        // Secant is the reciprocal of cosine.
        // Cotangent is the reciprocal of tangent.
        sineDegrees = O / H;
        cosineDegrees = A / H;
        tangentDegrees = O / A;
        cosecantDegrees = H / O;
        secantDegrees = H / A;
        cotangentDegrees = A / O;

        // Rather than doing the actual math to figure
        // out how many radians each of these is, I'm
        // just using the Math.toRadians function.
        sineRadians = Math.toRadians(sineDegrees);
        cosineRadians = Math.toRadians(cosineRadians);
        tangentRadians = Math.toRadians(tangentRadians);
        cosecantRadians = Math.toRadians(cosecantDegrees);
        secantRadians = Math.toRadians(secantDegrees);
        cotangentRadians = Math.toRadians(cotangentDegrees);
    }
}
