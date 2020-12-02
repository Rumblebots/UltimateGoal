package me.wobblyyyy.pathfinder_ftc.threeWheel;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder_ftc.pathfinder.PfInterface;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;

/**
 * The complete class for using a three-wheeled odometry system.
 *
 * <p>
 * Comes with out-of-the-box support for pathfinding as well as advanced and
 * accurate odometry-based tracking. Wow, I feel like a used car salesman.
 * You can get this incredibly scuffed product! Yay you! Yay me! Yay
 * everybody! The documentation here should explain everything that's
 * going on - if it doesn't, that kinda sucks, can't even lie.
 * </p>
 *
 * @author Colin Robertson
 */
public class ThreeWheelComplete {
    /**
     * The three-wheel odometry system that the robot makes use of.
     *
     * <p>
     * For more information regarding the ThreeWheel class and the inner workings of that very
     * class, you should go check out the JavaDocs {@link ThreeWheel} here.
     * </p>
     */
    public ThreeWheel threeWheel;

    /**
     * The pathfinding interface the robot makes use of.
     *
     * <p>
     * For more information regarding the PfInterface class and the inner workings of that very
     * class, you should go check out the JavaDocs {@link PfInterface} here.
     * </p>
     */
    public PfInterface pfInterface;

    /**
     * Create a brand-spanking-new ThreeWheelComplete instance. Continue
     * reading this choose-your-own adventure novel (not really) to learn
     * a bit more about what's going on.
     *
     * <p>
     * This is the highest-level possible implementation of the odometry and pathfinding
     * systems. Although much of the methods and properties of the classes involved here
     * are, in fact, public, using this class might make it a little bit difficult to
     * work with the specifics of the pathfinding and odometry systems. You don't have to
     * use this class - if you're really confused about what's going on, you should start
     * from the bottom up and read the documentation for everything involved in both the
     * pathfinding and the odometry. Good luck! Yay!
     * </p>
     *
     * @param leftName      the String name of the left encoder. Encoders are treated
     *                      as parts of the {@link org._11253.lib.robot.phys.components.Motor}
     *                      class, so this name will most likely coincide with the name of
     *                      another motor, quite potentially one on the drivetrain.
     * @param rightName     the String name of the left encoder. Encoders are treated
     *                      as parts of the {@link org._11253.lib.robot.phys.components.Motor}
     *                      class, so this name will most likely coincide with the name of
     *                      another motor, quite potentially one on the drivetrain.
     * @param frontBackName the String name of the left encoder. Encoders are treated
     *                      as parts of the {@link org._11253.lib.robot.phys.components.Motor}
     *                      class, so this name will most likely coincide with the name of
     *                      another motor, quite potentially one on the drivetrain.
     * @param config        the {@link ThreeWheelConfig} that the odometry system uses.
     *                      For more information on how the configuration works, you should
     *                      go check out the JavaDocs contained in the ThreeWheelConfig file.
     *                      If you're lazy, which I can totally understand (and relate to,
     *                      just thought I should put that out there) it's a fantastic idea
     *                      to make a new file that implements ThreeWheelConfig. In that
     *                      file, set up all of the required functions with the values
     *                      that they're supposed to have. After doing that, you should be
     *                      good to go - have fun!
     * @param drivetrain    the {@link Drivetrain} the robot uses. This is literally just
     *                      the drivetrain that the robot uses. That's it. It's that simple,
     *                      it really is. If you're using any of the extensions of Template,
     *                      which includes TeleOp, Meccanum, ShifterMeccanum, and even
     *                      all of the autonomous classes, you can simply pass "drivetrain"
     *                      as a parameter.
     * @param map           the map the field uses. You should probably use the map for
     *                      the current season you're in - i.e. the 2020/2021 season would
     *                      use the UltimateGoal map, the 2019/2020 season would use the
     *                      SkyStone map, etc. I'm sure you understand what's going on.
     */
    public ThreeWheelComplete(String leftName,
                              String rightName,
                              String frontBackName,
                              ThreeWheelConfig config,
                              Drivetrain drivetrain,
                              Map map) {
        threeWheel = new ThreeWheel(
                leftName,
                rightName,
                frontBackName,
                config
        );

        pfInterface = new PfInterface(
                threeWheel,
                drivetrain,
                map
        );
    }
}
