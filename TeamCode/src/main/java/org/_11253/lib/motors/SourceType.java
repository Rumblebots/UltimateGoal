package org._11253.lib.motors;

/**
 * Different types of sources of drivetrain input.
 *
 * <p>
 * Used in controlling the drivetrain. Certain scenarios might require user input
 * to be disabled - in those situations, SourceType(s) come in handy by allowing
 * user inputs to be disregarded when they're not needed.
 * </p>
 *
 * @author Colin Robertson
 */
public enum SourceType {
    /**
     * Any input that comes from a user.
     */
    USER,

    /**
     * Any input that does not come from a user.
     */
    NON_USER
}
