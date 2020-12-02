package org._11253.lib.motors;

/**
 * An extension of the basic MotorPower, but with a source attribute.
 *
 * <p>
 * Sources are used in controlling user input at different points in time. There
 * are some scenarios in which it's not appropriate for user input to be enabled -
 * for example, if Pathfinder is being used to find a path, and that path is being
 * executed, user input should be entirely disregarded.
 * </p>
 *
 * @author Colin Robertson
 */
public class SourcedMotorPower extends MotorPower {
    /**
     * The type of input that this instance reflects.
     *
     * <p>
     * The options are either USER or NON_USER.
     * It's pretty simple, really. I can't even lie.
     * </p>
     */
    private SourceType type;

    /**
     * A constructor - of course, we have our lovely SOURCES now!
     *
     * @param fr front-right power.
     * @param fl front-left power.
     * @param br back-right power.
     * @param bl back-left power.
     * @param type the input type (user/non-user).
     */
    public SourcedMotorPower(double fr,
                             double fl,
                             double br,
                             double bl,
                             SourceType type) {
        super(fr, fl, br, bl);
        this.type = type;
    }

    /**
     * Get the type of input that this instance represents.
     *
     * @return the type of input being used.
     */
    public SourceType getType() {
        return type;
    }
}
