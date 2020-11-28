package me.wobblyyyy.pathfinder.localizer;

/**
 * Class, representing motor powers for a meccanum chassis.
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
     * Get {@link fr}
     * @return motor power
     */
    public double getFr() {
        return fr;
    }

    /**
     * Get {@link fl}
     * @return motor power
     */
    public double getFl() {
        return fl;
    }

    /**
     * Get {@link br}
     * @return motor power
     */
    public double getBr() {
        return br;
    }

    /**
     * Get {@link bl}
     * @return motor power
     */
    public double getBl() {
        return bl;
    }
}
