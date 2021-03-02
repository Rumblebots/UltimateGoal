package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import me.wobblyyyy.pathfinder.config.PathfinderConfig;
import me.wobblyyyy.pathfinder.core.Followers;
import me.wobblyyyy.pathfinder.drive.Drive;
import me.wobblyyyy.pathfinder.map.Map;
import me.wobblyyyy.pathfinder.robot.Odometry;
import me.wobblyyyy.pathfinder.util.RobotProfile;

public class Config extends PathfinderConfig {
    /**
     * Create a new PathfinderConfig to be fed to a Pathfinder.
     *
     * <p>
     * For your own sake - please go read the field descriptions in this Java
     * file if you're confused about what any of these means. I've written out
     * nice and long descriptions for everything, so there shouldn't be all that
     * much confusion about what's going on.
     * </p>
     *
     * @param odometry      the odometry subsystem that's used by the pathfinder
     *                      in determining the robot's position. This odometry
     *                      system should be as accurate as possible and maintain
     *                      contact with the ground at all times.
     * @param fieldWidth    the fieldWidth of the field, in whatever units you'd like.
     *                      Although the units don't matter much, you need to be
     *                      sure to keep the units consistent.
     * @param fieldHeight   the fieldHeight of the field, in whatever units you'd like.
     *                      Although the units don't matter much, you need to be
     *                      sure to keep the units consistent.
     * @param specificity   the field's specificity factor. Low specificity means
     *                      lower precision. High specificity means higher
     *                      precision. The higher the specificity value is, the
     *                      longer paths will take to find and the more resources
     *                      are needed on the host computer.
     * @param robotX        the robot's X dimension. X/Y are the same thing here.
     *                      That's not to say that you shouldn't measure X and Y
     *                      values - accuracy is still just as important as ever.
     * @param robotY        the robot's Y dimension. X/Y are the same thing here.
     *                      That's not to say that you shouldn't measure X and Y
     *                      values - accuracy is still just as important as ever.
     * @param gapX          the distance (in inches) between the pair of front
     *                      right and front left wheel centers.
     * @param gapY          the distance (in inches) between the pair of front
     *                      right and back right wheel centers.
     * @param profile       the robot's motion profiling profile. This profile
     *                      should provide (at least somewhat accurate) info
     *                      on the robot's motion in real life.
     * @param drive         the robot's drivetrain. The drivetrain is (rather
     *                      obviously) used to actually drive the robot.
     * @param map           a virtual map of something. In most cases, this is
     *                      a game field with all your different obstacles and
     *                      what not.
     * @param follower      what type of follower the pathfinder uses.
     * @param usesLightning see: {@link PathfinderConfig}
     * @param usesFast      see: {@link PathfinderConfig}
     * @param usesThetaStar see: {@link PathfinderConfig}
     */
    public Config(Odometry odometry, int fieldWidth, int fieldHeight, int specificity, double robotX, double robotY, double gapX, double gapY, RobotProfile profile, Drive drive, Map map, Followers follower, boolean usesLightning, boolean usesFast, boolean usesThetaStar) {
        super(odometry, fieldWidth, fieldHeight, specificity, robotX, robotY, gapX, gapY, profile, drive, map, follower, usesLightning, usesFast, usesThetaStar);
    }
}
