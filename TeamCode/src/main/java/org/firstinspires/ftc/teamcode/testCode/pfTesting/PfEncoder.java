package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import me.wobblyyyy.pathfinder.robot.Encoder;

public class PfEncoder implements Encoder {
    private final PfMotor motor;

    public PfEncoder(PfMotor motor) {
        this.motor = motor;
    }

    /**
     * Get the encoder's current count.
     *
     * @return the encoder's count.
     */
    @Override
    public int getCount() {
        return motor.getCount();
    }

    /**
     * Get the encoder's CPR (counts per rotation).
     *
     * @return the encoder's counts per rotation.
     */
    @Override
    public double getCpr() {
        return 1440;
    }
}
