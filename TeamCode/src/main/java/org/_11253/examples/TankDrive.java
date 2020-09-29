/*
 * TODO
 *  Update this file so it accurately reflects using the framework.
 *  Currently, it's broken. Do something about it, just like your
 *  history teacher in 8th grade told you to do whenever she mentioned any
 *  kind of problem or something. YKTV.
 */

//package org._11253.examples;
//
//import org._11253.lib.controllers.ControllerMap;
//import org._11253.lib.drives.Tank;
//import org._11253.lib.utils.Command;
//import org._11253.lib.utils.gen.Shifter;
//
///**
// * An example TankDrive implementation.
// * <p>
// * This extends the default Tank example drive mode in org._11253.lib.drives,
// * and serves as an example op mode, which is fully functional.
// * </p>
// */
//public class TankDrive extends Tank
//{
//    /*
//     * Here's an example shifter - note that it doesn't actually have a purpose.
//     * Inside of the Tank class (the class that this one extends) is a shifter that actually
//     * changes the speed, which we'll unmap, for demonstration purposes.
//     */
//    public Shifter demoShifter = new Shifter(1, 10, 1);
//
//    /**
//     * This function is going to be what we use to bind controls before the OpMode starts.
//     * <p>
//     * bind(States, Command) binds a command to a state, in case you couldn't tell.
//     * This means that when something on the controller happens, the command will be executed.
//     * </p>
//     */
//    public void bindControls ()
//    {
//        /*
//         * The basic syntax of binding something is...
//         * <controller> (either controller1 or controller2).map.bind(<state>, <command>).
//         * In this case, we make use of anonymous functions to create a new command to bind inline, rather
//         * than having to define one somewhere else. Note that a new command on it's own (just
//         * new Command() instead of new Command (){}) has nothing inside of it and does absolutely nothing.
//         */
//        controller1.map.bind(ControllerMap.States.RIGHT_BUMPER, new Command()
//        {
//            /*
//             * After creating a new command, we now have to re-define the Runnable's active and inactive.
//             * Active is run when the state is active.
//             * Inactive is run when the state is NOT active.
//             * As of now, without modifying the library, there is no way to change the name of these.
//             */
//            public Runnable active = new Runnable()
//            {
//                /*
//                 * All runnables must implement the public void run ().
//                 * We have to use override because it's already declared in template runnables.
//                 */
//                @Override
//                public void run ()
//                {
//                    /*
//                     * When the right bumper is held up, we're going to want to shift gears UPWARDS.
//                     * We do this by calling the onPressShiftUp function from our shifter.
//                     * Everything else related to the shifter is taken care of behind-the-scenes by
//                     * our shifter class.
//                     */
//                    demoShifter.onPressShiftUp();
//                }
//            };
//        });
//        /*
//         * Here we're going to create another binding, but this time for shifting down.
//         * Much like our last one, we declare everything in the same way.
//         */
//        controller1.map.bind(ControllerMap.States.LEFT_BUMPER, new Command()
//        {
//            public Runnable active = new Runnable()
//            {
//                @Override
//                public void run ()
//                {
//                    /*
//                     * When the left bumper is pressed, we're going to want to shift gears DOWNWARDS.
//                     * We do this by calling the onPressShifterDown function from our shifter.
//                     * Everything else related ot the shifter is taken care of behind-the-scenes by our
//                     * shifter class.
//                     */
//                    demoShifter.onPressShiftDown();
//                }
//            };
//            public Runnable inactive = new Runnable()
//            {
//                @Override
//                public void run ()
//                {
//                    /*
//                     * However, if the left bumper is NOT active and the right bumper is not active either,
//                     * we don't want to do anything at all, except for run the onRelease function, which signifies to our
//                     * shifter that neither of the options are currently active.
//                     * It's worth noting that we only have to check if the right bumper is not active, because the other
//                     * bumper is already known to be to be inactive, given this code is within the 'inactive' runnable of
//                     * our command.
//                     */
//                    if (!controller1.getRightBumper())
//                    {
//                        demoShifter.onRelease();
//                    }
//                }
//            };
//        });
//        /*
//         * Note that in the first binding example we entirely left out the inactive runnable.
//         * This is because new commands by default contain empty runnables, meaning we don't have to declare
//         * a new runnable if we don't want to, so this way all of the code works cleanly.
//         */
//    }
//
//    /**
//     * This is a small function, which override's Template's load function,
//     * just to load / map / bind our controls.
//     */
//    @Override
//    public void load ()
//    {
//        bindControls();
//    }
//
//    /*
//     * The rest of the code is handled automatically by the classes, in order, of...
//     * - Tank (example drive system)
//     * - TeleOp (TeleOp implementation)
//     * - Template (high-level implementation of LinearOpMode)
//     */
//}
