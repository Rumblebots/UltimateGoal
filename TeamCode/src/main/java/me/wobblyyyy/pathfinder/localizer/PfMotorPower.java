package me.wobblyyyy.pathfinder.localizer;

/**
 * Pathfinder Motor Power, representing motor powers for a meccanum chassis.
 *
 * <p>
 * These are simply suggested motor values. None of these motor values are actually
 * set immediately based on the Pathfinder determining these motor values - that
 * still has to be done manually, unless, of course, you're working with the famed,
 * the legendary, the one and only, Pathfinder-ftc.
 * </p>
 *
 * @author Colin Robertson
 */
public class PfMotorPower {
    /**
     * Power for the front-right motor.
     */
    private final double fr;

    /**
     * Power for the front-left motor.
     */
    private final double fl;

    /**
     * Power for the back-right motor.
     */
    private final double br;

    /**
     * Power for the back-left motor.
     */
    private final double bl;

    public PfMotorPower(double fr,
                        double fl,
                        double br,
                        double bl) {
        this.fr = fr;
        this.fl = fl;
        this.br = br;
        this.bl = bl;
    }

    /**
     * Get front-right.
     * @return motor power
     */
    public double getFr() {
        return fr;
    }

    /**
     * Get front-left.
     * @return motor power
     */
    public double getFl() {
        return fl;
    }

    /**
     * Get back-right.
     * @return motor power
     */
    public double getBr() {
        return br;
    }

    /**
     * Get back-left.
     * @return motor power
     */
    public double getBl() {
        return bl;
    }
}
