package me.wobblyyyy.pathfinder_ftc.pathfinder;

import me.wobblyyyy.pathfinder.Pathfinder;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder_ftc.AbstractOdometry;
import org._11253.lib.motors.SourceType;
import org._11253.lib.motors.SourcedMotorPower;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

/**
 * The main interface for interacting with the Pathfinder library.
 *
 * @author Colin Robertson
 */
public class PfInterface {
    /**
     * A general name, used for multiple things.
     */
    private final static String name = "PFI";

    /**
     * The name of the set of events used for automatic ticking.
     */
    private final static String automaticTickingName = name;

    /**
     * The name of the set of events used for controlling the motors
     * without any form of user input.
     */
    private final static String pathfindingControl = name;

    /**
     * Used to keep track of whether or not an event has been scheduled yet.
     */
    private static boolean hasEventBeenScheduled = false;

    /**
     * The type of input PfInterface uses - NON_USER, of course.
     */
    private static final SourceType type = SourceType.NON_USER;

    /**
     * A reference to the {@link Pathfinder} that's used here.
     */
    public Pathfinder pathfinder;

    /**
     * A lovely constructor for the lovely pathfinding interface.
     *
     * @param odometry   the {@link AbstractOdometry} system that the robot uses. This isn't
     *                   a regular odometry that would be used within Pathfinder natively.
     *                   Abstract odometry is a class in Pathfinder-ftc that serves to extend
     *                   the pathfinding system by further implementing functionality that's
     *                   specific only to the First Tech Challenge competition series.
     * @param drivetrain the {@link Drivetrain} system that the robot uses. This is what the
     *                   name would imply - the drivetrain. If you're using any extension of
     *                   the Template class (including all of the following:
     *                   {@link org._11253.lib.op.Template},
     *                   {@link org._11253.lib.op.TeleOp},
     *                   {@link org._11253.lib.drives.Meccanum},
     *                   {@link org._11253.lib.drives.ShifterMeccanum}),
     *                   you can just pass "drivetrain" as a parameter, and you're all good!
     * @param map        the {@link Map} you want the pathfinding system to use. As a general idea,
     *                   it's probably a good idea to use the map of the competition you're working
     *                   on. For 2020/2021, UltimateGoal was the competition title - thus, you
     *                   should probably use the pre-provided UltimateGoal map. Yaya!
     */
    public PfInterface(AbstractOdometry odometry,
                       Drivetrain drivetrain,
                       Map map) {
        PfManager.drivetrain = new PfDrivetrainManager(drivetrain);
        PfManager.map = new PfMapManager(map, odometry.getOdometry());
        PfManager.odometry = odometry.getOdometry();
        pathfinder = new Pathfinder(
                map,
                odometry.getOdometry(),
                50
        );
    }

    /**
     * Enable automatic ticking of the pathfinder.
     *
     * @param duration the length of time (in ms) between pathfinder ticks. As with most
     *                 other things, the more times per second this is updated, the more
     *                 accurate pathfinding will be. Make sure not to ramp this up too
     *                 much, however - running this too many times per second will cause
     *                 huge performance penalties. Because of the way {@link StringEvents}
     *                 functions, every single event that you schedule will execute at some
     *                 point. It's less than ideal to run several hundred repetitions of the
     *                 same piece of code, all executed within the same few ms. My point is -
     *                 a value somewhere around 50-100 should be satisfactory for this. If
     *                 you notice any performance hits, you can increase this value, but you
     *                 likely shouldn't increase this beyond anything near 200.
     * @param delay    the length of time (in ms) before the pathfinder starts ticking.
     *                 This should almost always be zero - I really don't know why it
     *                 would need to be anything other than zero. If you need to schedule
     *                 a delay for the automatic ticking of the pathfinder, you should
     *                 probably look at how your code is structured and move any of the
     *                 code related to pathfinding to a different location where no
     *                 delay is required.
     */
    public void enableAutomaticTicking(long duration,
                                       long delay) {
        if (hasEventBeenScheduled) return;
        StringEvents.schedule(
                automaticTickingName,
                duration,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {

                            }
                        };
                    }
                },
                true
        );
        hasEventBeenScheduled = true;
    }

    /**
     * Disable the automatic ticking of the pathfinder.
     */
    public void disableAutomaticTicking() {
        StringEvents.clear(automaticTickingName);
        hasEventBeenScheduled = false;
    }

    /**
     * Set power to the drivetrain based on the suggested motor power put
     * out by the pathfinding system.
     *
     * <p>
     * This actually sets power to the motors. While the pathfinding system, when ticked,
     * will find the optimal motor power to get to a given destination, it doesn't actually
     * set power to any of the motors. This is largely because the pathfinding library is
     * designed to be very general - it's not only applicable for FTC scenarios. This
     * library, aptly named Pathfinder-ftc, actually is designed for FTC, which is why
     * we have to set motor power here and not somewhere else.
     * </p>
     */
    public void setMotorPower() {
        PfMotorPower mp = pathfinder.pfMotorPower;
        PfManager.drivetrain.setPower(
                new SourcedMotorPower(
                        mp.getFr(),
                        mp.getFl(),
                        mp.getBr(),
                        mp.getBl(),
                        type
                )
        );
    }

    /**
     * Tells the pathfinder to go to a given position.
     *
     * @param position        the position the robot should go to.
     * @param delay           time (ms) before the pathfinding motor control is initiated.
     *                        This value should (generally) be zero, but that's entirely
     *                        up to you. Always remember - you're the master of your own
     *                        world! Or reality! Or something like that!
     * @param timeout         the maximum amount of time (ms) the pathfinding system is
     *                        allowed to take up. Some time around 7 seconds (7,000ms)
     *                        is generally the suggested value, but you can pick whatever
     *                        you'd like. Your choice!
     * @param tickingDelay    the length of time (in ms) before the pathfinder starts ticking.
     *                        This should almost always be zero - I really don't know why it
     *                        would need to be anything other than zero. If you need to schedule
     *                        a delay for the automatic ticking of the pathfinder, you should
     *                        probably look at how your code is structured and move any of the
     *                        code related to pathfinding to a different location where no
     *                        delay is required.
     * @param tickingDuration the length of time (in ms) between pathfinder ticks. As with most
     *                        other things, the more times per second this is updated, the more
     *                        accurate pathfinding will be. Make sure not to ramp this up too
     *                        much, however - running this too many times per second will cause
     *                        huge performance penalties. Because of the way {@link StringEvents}
     *                        functions, every single event that you schedule will execute at some
     *                        point. It's less than ideal to run several hundred repetitions of the
     *                        same piece of code, all executed within the same few ms. My point is -
     *                        a value somewhere around 50-100 should be satisfactory for this. If
     *                        you notice any performance hits, you can increase this value, but you
     *                        likely shouldn't increase this beyond anything near 200.
     */
    public void goToPosition(final HeadingCoordinate<Double> position,
                             final long delay,
                             final long timeout,
                             final long tickingDelay,
                             final long tickingDuration) {
        StringEvents.schedule(
                pathfindingControl,
                timeout,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                pathfinder.goToPosition(position);
                                enableAutomaticTicking(
                                        tickingDuration,
                                        tickingDelay
                                );
                            }
                        };
                    }

                    @Override
                    public Runnable during() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                setMotorPower();
                            }
                        };
                    }

                    @Override
                    public Runnable close() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                disableAutomaticTicking();
                            }
                        };
                    }
                },
                false
        );
    }
}
