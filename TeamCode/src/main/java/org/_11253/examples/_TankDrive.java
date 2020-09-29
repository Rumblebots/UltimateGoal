///*
// * **
// *
// * Copyright (c) 2020
// * Copyright last updated on 6/4/20, 9:59 PM
// * Part of the _1125c library
// *
// * **
// *
// * Permission is granted, free of charge, to any person obtaining
// * a copy of this software and / or any of it's related source code or
// * documentation ("Software") to copy, merge, modify, publish,
// * distribute, sublicense, and / or sell copies of Software.
// *
// * All Software included is provided in an "as is" state, without any
// * type or form of warranty. The Authors and Copyright Holders of this
// * piece of software, documentation, or source code waive all
// * responsibility and shall not be liable for any claim, damages, or
// * other forms of liability, regardless of the form it may take.
// *
// * Any form of re-distribution of Software is required to have this same
// * copyright notice included in any source files or forms of documentation
// * which have stemmed or branched off of the original Software.
// *
// * **
// *
// */
//
//package org._11253.examples;
//
//import org._11253.lib.Global;
//import org._11253.lib.controllers.ControllerMap;
//import org._11253.lib.drives.Tank;
//import org._11253.lib.utils.Command;
////import org._11253.lib.utils.gen.Toggle;
//
///**
// * Demonstrates a really simple Tank Drive.
// * <p>
// * In a real situation, you'd need to include
// * FTC's annotation allowing this to be picked
// * up as an OpMode, more specifically,
// * a TeleOp mode. If you don't do that, you won't
// * be able to access this from the app.
// * For more information regarding FTC's annotations, go to this
// * link...
// * <a href="https://ftctechnh.github.io/ftc_app/doc/javadoc/com/qualcomm/robotcore/eventloop/opmode/TeleOp.html#:~:text=Provides%20an%20easy%20and%20non,in%20the%20driver%20station%20display.">
// * TeleOp ANNOTATION JAVADOC
// * </a>
// * </p>
// * <p>
// * This demonstrates how simple a Tank Drive system
// * is to create with this library. While, sadly, there's not
// * absolutely no code, there's very little, which (should)
// * make this simplify the process of OpMode creation quite a bit.
// * </p>
// * <p>
// * Because you're still interacting with lower-level Java, rather
// * than an even further abstracted scripting language, you still
// * have full access to all the FTC technologies you know and love.
// * With that being said, you can still utilize this library's
// * utils & functions to simplify the process of creating an
// * OpMode and better organize all of your team's code.
// * </p>
// */
//public class _TankDrive extends Tank {
//    /*
//     * We don't need to map any of the drive controls,
//     * because they're already handled in the Tank class
//     * which this class extends. However, we're going to
//     * add a new Toggle feature, which we'll see below in
//     * just a moment.
//     */
//
//    /**
//     * Example toggle which is mapped to the A button.
//     * <p>
//     * This uses _1125c's Command class to create a new
//     * command and map it to A. This also demonstrates how to
//     * create a toggle, and, more importantly, how to use
//     * map the toggle to a controller's controls.
//     * </p>
//     */
//    private Toggle aButtonToggle = new Toggle();
//
//    /**
//     * Map the A button to aButtonToggle
//     *
//     * <p>
//     * I'm sorry about how incredibly messy all
//     * of this code is, but there's not exactly
//     * much I can do it because of Java's limitations.
//     * I might (might (keyword: might)) implement JavaScript
//     * or maybe even Groovy or possibly even create my
//     * own scripting language just for this purpose.
//     * </p>
//     */
//    private void mapAButtonToggle() {
//        controller1.map.bind(
//                ControllerMap.States.A,
//                new Command() {
//                    @Override
//                    public Runnable active() {
//                        return new Runnable() {
//                            @Override
//                            public void run() {
//                                aButtonToggle.onPress();
//                            }
//                        };
//                    }
//
//                    @Override
//                    public Runnable inactive() {
//                        return new Runnable() {
//                            @Override
//                            public void run() {
//                                aButtonToggle.onRelease();
//                            }
//                        };
//                    }
//                }
//        );
//    }
//
//    /**
//     * Example function demonstrating how you should
//     * go about adding functionality to the Template
//     * class' event lists.
//     * <p>
//     * Keep in mind, the available event lists are
//     * as follows, and in order...
//     *     <ul>
//     *         <li>beforeStart</li>
//     *         <li>onStart</li>
//     *         <li>
//     *             <ul>
//     *                 <li>onStartRun</li>
//     *                 <li>run</li>
//     *                 <li>onFinishRun</li>
//     *             </ul>
//     *         </li>
//     *     </ul>
//     *     In general, you usually only need to play around with
//     *     beforeStart and run, but there's more so you can
//     *     make your code oh so much sexier. I know, you're welcome
//     *     for this blessing in the form of code. I love you too, don't worry. <3
//     * </p>
//     */
//    private void addStartTelemetry() {
//        /*
//         * This demonstrates how you should add Runnable's to
//         * the event lists.
//         */
//        onStart.add(
//                /*
//                 * Create a new Runnable
//                 */
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        /*
//                         * First add a little bit of telemetry, just
//                         * letting the user know that we've reached the
//                         * onStart list.
//                         */
//                        Global.getTelem().addLine("Start list has begun!");
//                    }
//                },
//                /*
//                 * Create yet another new Runnable
//                 */
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        /*
//                         * Then update the telemetry, because, well, we kinda need to do
//                         * that.
//                         */
//                        Global.getTelem().update();
//                    }
//                }
//        );
//    }
//
//    /**
//     * Override's Template class' initOp function for init functionality.
//     * <p>
//     * This is run before anything else. Basically, you should put any
//     * of the list functionality in here (ie. adding new Runnable's to
//     * lists for more functionality later on)
//     * </p>
//     * <p>
//     * In most normal cases, this should be the only function you'll need to
//     * write to actually interface directly with this the robot.
//     * By that, I mean you should still create other methods to better organize
//     * your code, but this function should be the only one which overrides
//     * another function already included in the inheritance / extension order.
//     * </p>
//     */
//    @Override
//    public void initOp() {
//        mapAButtonToggle();
//        addStartTelemetry();
//    }
//}
