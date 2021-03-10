package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import org._11253.lib.robot.phys.components.Motor;

public class PfMotor extends Motor
        implements me.wobblyyyy.pathfinder.robot.Motor {
    private boolean isUserControlEnabled = true;
    private final boolean invert;

    /**
     * Create a new Motor.
     *
     * <p>
     * Search for the motor component and then create a new motor.
     * </p>
     *
     * @param name the name of the motor to look for.
     */
    public PfMotor(String name, boolean invert) {
        super(name);
        this.invert = invert;
    }

    /**
     * Allow the motor to be controlled by a user.
     *
     * <p>
     * Implementing this can be a little bit annoying. For that reason,
     * I'd suggest that you use whatever Pathfinder distro is available for
     * the robotics competition you're engaged in.
     * </p>
     *
     * <p>
     * While user control is disabled, the user SHOULD NOT be able to control
     * the robot via a joystick. In fact, nothing should be able to control
     * the robot's motors UNLESS it's specifically marked as a non-user,
     * which can be done however you'd like.
     * </p>
     */
    @Override
    public void enableUserControl() {
        isUserControlEnabled = true;
    }

    /**
     * Stop allowing the motor to be controlled by a user.
     *
     * <p>
     * Implementing this can be a little bit annoying. For that reason,
     * I'd suggest that you use whatever Pathfinder distro is available for
     * the robotics competition you're engaged in.
     * </p>
     *
     * <p>
     * While user control is disabled, the user SHOULD NOT be able to control
     * the robot via a joystick. In fact, nothing should be able to control
     * the robot's motors UNLESS it's specifically marked as a non-user,
     * which can be done however you'd like.
     * </p>
     */
    @Override
    public void disableUserControl() {
        isUserControlEnabled = false;
    }

    /**
     * Set power to the motor.
     *
     * <p>
     * Power set to motors is always within the range of (-1) to (+1).
     * <ul>
     *     <li>
     *         Positive 1 represents the motor's maximum speed in
     *         the POSITIVE/FORWARDS direction.
     *     </li>
     *     <li>
     *         Negative 1 represents the motor's maximum speed in
     *         the NEGATIVE/BACKWARDS direction.
     *     </li>
     * </ul>
     * </p>
     *
     * @param power the power to set to the motor.
     * @param user  whether or not this power change is the result of a user
     *              or non-user. true means that a user made the change, while
     */
    @Override
    public void setPower(double power, boolean user) {
//        if ((user && isUserControlEnabled) ||
//                ((!user) && (!isUserControlEnabled))) {
//            super.setPower(invert ? power * -1 : power);
//        }
        super.setPower(invert ? power * -1 : power);
    }
}
