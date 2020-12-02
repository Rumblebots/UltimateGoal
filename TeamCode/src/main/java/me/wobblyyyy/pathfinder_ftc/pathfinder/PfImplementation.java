package me.wobblyyyy.pathfinder_ftc.pathfinder;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import org._11253.lib.motors.MotorPower;

/**
 * Utilities regarding the implementation and connection of
 * Pathfinder to Pathfinder-ftc.
 *
 * @author Colin Robertson
 */
public class PfImplementation {
    /**
     * Transform an instance of...
     * {@link PfMotorPower}
     * ... to an instance of ...
     * {@link MotorPower}.
     *
     * @param pfMotorPower pathfinder-specific suggested motor power.
     * @return good ol' motor power.
     */
    public static MotorPower getMotorPower(PfMotorPower pfMotorPower) {
        return new MotorPower(
                pfMotorPower.getFr(),
                pfMotorPower.getFl(),
                pfMotorPower.getBr(),
                pfMotorPower.getBl()
        );
    }
}
