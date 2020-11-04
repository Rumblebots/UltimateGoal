/**
 * Anything related to this library's field mapping functionality,
 * which virtually tracks the position of the robot, relative to
 * the field it's on and the objects on said field.
 *
 * <p>
 * Field-mapping is roughly quite similar to techniques self-driving car
 * manufacturers and programmers are using. By having a constant and static
 * list of positions where the robot can't go, we can figure out where we
 * have to avoid, and thus, where we can go. Field-mapping isn't exactly
 * a new concept, but I haven't seen any other implementations of it in
 * the FTC world. I guess I'm the first - I know, I'm really god damn cool.
 * </p>
 */

package org._11253.lib.odometry.fieldMapping;
