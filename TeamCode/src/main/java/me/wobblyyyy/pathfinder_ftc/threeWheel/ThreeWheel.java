package me.wobblyyyy.pathfinder_ftc.threeWheel;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.ThreeWheelCore;
import me.wobblyyyy.pathfinder_ftc.AbstractOdometry;
import me.wobblyyyy.pathfinder_ftc.Manager;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

/**
 * A custom implementation of a three-wheel odometry system.
 *
 * <p>
 * This class contains a bunch of different utilities that can be used
 * specifically in the FTC competition. Rather than using something like
 * {@link ThreeWheelCore}, which would definitely provide the same results,
 * I've ever-so-graciously provided you the wonderful opportunity of using
 * some obviously absolutely fantastic code. On a more serious note, this
 * allows you to get an odometry system up and running reliably and quickly
 * without having to spend tons of time digging into code and math.
 * </p>
 *
 * @author Colin Robertson
 */
public class ThreeWheel implements AbstractOdometry {
    /**
     * The name of the string events that will be scheduled.
     *
     * <p>
     * For the sake of keeping everything as consistent as possible,
     * this is stored as a static string, instead of creating a new
     * string every time we want to reference it.
     * </p>
     */
    private static final String eventName = "automatic pathfinder-ftc updating";

    /**
     * The {@link ThreeWheelConfig} that we're using.
     *
     * <p>
     * You can read more about OdometryConfiguration(s) by going to the
     * documentation for them - however, this is essentially used to
     * prevent having to pass tons of different values through many
     * different methods. Heck to the yeah.
     * </p>
     */
    private final ThreeWheelConfig configuration;

    /**
     * The {@link Odometry} system that's in use.
     *
     * <p>
     * Much like any other class element in this library, you can check
     * out the JavaDocs that are linked above for more information on
     * what's going on here.
     * </p>
     */
    private final ThreeWheelCore odometry;

    /**
     * The name of the left motor.
     */
    private final String leftName;

    /**
     * The name of the right motor.
     */
    private final String rightName;

    /**
     * The name of the front/back motor.
     */
    private final String frontBackName;

    /**
     * The left motor.
     */
    private final Motor left;

    /**
     * The right motor.
     */
    private final Motor right;

    /**
     * The front/back motor.
     */
    private final Motor frontBack;

    /**
     * The current value of the left motor.
     */
    public int valLeft;

    /**
     * The current value of the right motor.
     */
    public int valRight;

    /**
     * The current value of the front/back motor.
     */
    public int valFrontBack;

    /**
     * A {@link Runnable}, used to get updated encoder values.
     */
    private final Runnable getEncoderValues;

    /**
     * A {@link Runnable}, used to update the odometry system.
     */
    private final Runnable updateOdometry;

    /**
     * A {@link Runnable}, used to update encoder values and
     * the whole odometry system.
     */
    private final Runnable onAutoTick;

    /**
     * Our lovely constructor! Creating a new ThreeWheel is just
     * moments away - get started today!
     *
     * @param leftName      the name of the left encoder/motor. In most
     *                      scenarios, this will be the same as one of the drive
     *                      motors. This is because encoders aren't actually given
     *                      their own slot to plug into - they actually plug into
     *                      a small portion of a motor port. Yeah.
     * @param rightName     the name of the right encoder/motor. In most
     *                      scenarios, this will be the same as one of the drive
     *                      motors. This is because encoders aren't actually given
     *                      their own slot to plug into - they actually plug into
     *                      a small portion of a motor port. Yeah.
     * @param frontBackName the name of the front/back encoder/motor. In most
     *                      scenarios, this will be the same as one of the drive
     *                      motors. This is because encoders aren't actually given
     *                      their own slot to plug into - they actually plug into
     *                      a small portion of a motor port. Yeah.
     * @param configuration The {@link ThreeWheelConfig} at play here.
     *                      This is an interface that contains a bunch of required
     *                      methods that enable us to quickly and effectively pass
     *                      different configurations without worrying about keeping
     *                      track of a ton of different parameters - five, to be
     *                      exact. One is better than five - I think.
     */
    public ThreeWheel(String leftName,
                      String rightName,
                      String frontBackName,
                      ThreeWheelConfig configuration) {
        this.leftName = leftName;
        this.rightName = rightName;
        this.frontBackName = frontBackName;

        this.left = new Motor(leftName);
        this.right = new Motor(rightName);
        this.frontBack = new Motor(frontBackName);

        this.configuration = configuration;

        odometry = new ThreeWheelCore(
                configuration.getCpr(),
                configuration.getWheelDiameter(),
                configuration.getLeftOffset(),
                configuration.getRightOffset(),
                configuration.getFrontBackOffset()
        );

        getEncoderValues = new Runnable() {
            @Override
            public void run() {
                valLeft = left.getCount();
                valRight = right.getCount();
                valFrontBack = frontBack.getCount();
            }
        };
        updateOdometry = new Runnable() {
            @Override
            public void run() {
                odometry.update(
                        valLeft,
                        valRight,
                        valFrontBack
                );
            }
        };
        onAutoTick = new Runnable() {
            @Override
            public void run() {
                getEncoderValues.run();
                updateOdometry.run();
            }
        };
    }

    /**
     * Schedule automatic updating of the odometry system.
     *
     * <p>
     * Generally speaking, the best bet here is to run this method at the
     * start of any OpMode (whether it be tele-op or autonomous) and let it
     * do its thing.
     * </p>
     *
     * @param duration how long the event lasts. In simple terms, this is the
     *                 duration of time in between when two updates of the
     *                 odometry system are triggered. Speaking in general terms
     *                 here, you'll often want to have the duration be as low
     *                 as you possibly can without causing performance hits.
     *                 Because of the way StringEvents function, a spike
     *                 in lag will cause several odometry updates to run
     *                 at the same time, which I think we can both agree is
     *                 less than ideal. I would suggest a value around 50-100 -
     *                 respectively, those allow you to get an updated position
     *                 of the robot 10-20 times per second - quite a few.
     * @param delay    how long before the event starts running. This really is
     *                 just what the name suggests - a delay between when this
     *                 method is called and when the updating code is actually
     *                 executed. Much like the duration one, you want to have the
     *                 smallest possible value for this - preferably zero. It's
     *                 a significantly better idea to leave this value at zero
     *                 and figure out another way to replace the delay - delays
     *                 are stupid and take up a lot of space. Whatever.
     */
    public void enableAuto(long duration,
                           long delay) {
        StringEvents.schedule(
                eventName,
                duration,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return onAutoTick;
                    }
                },
                true
        );
    }

    /**
     * Disable the automatic updating of the odometry system.
     */
    public void disableAuto() {
        StringEvents.clear(eventName);
    }

    /**
     * Get the odometry system in use.
     *
     * @return the odometry system being utilized.
     */
    @Override
    public Odometry getOdometry() {
        return odometry;
    }

    /**
     * Get the left motor.
     *
     * @return the left motor.
     */
    @Override
    public Motor getLeft() {
        return left;
    }

    /**
     * Get the right motor.
     *
     * @return the right motor.
     */
    @Override
    public Motor getRight() {
        return right;
    }

    /**
     * Get the front/back motor.
     *
     * @return the front/back motor.
     */
    @Override
    public Motor getFrontBack() {
        return frontBack;
    }

    /**
     * Get the name of the left motor.
     *
     * @return the name of the left motor.
     */
    public String getLeftName() {
        return leftName;
    }

    /**
     * Get the name of the right motor.
     *
     * @return the name of the right motor.
     */
    public String getRightName() {
        return rightName;
    }

    /**
     * Get the name of the front/back motor.
     *
     * @return the name of the front/back motor.
     */
    public String getFrontBackName() {
        return frontBackName;
    }

    /**
     * Get the odometry configuration that the odometry system
     * is using.
     *
     * @return the presently-used {@link ThreeWheelConfig}
     */
    public ThreeWheelConfig getConfiguration() {
        return configuration;
    }

    /**
     * Get the position of the robot, represented in a
     * heading-based coordinate. Yay!
     *
     * @return the {@link HeadingCoordinate} position of the robot.
     */
    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }
}
