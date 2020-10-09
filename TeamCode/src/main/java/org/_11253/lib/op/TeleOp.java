/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 10:58 PM
 * Part of the _1125c library
 *
 * **
 *
 * Permission is granted, free of charge, to any person obtaining
 * a copy of this software and / or any of it's related source code or
 * documentation ("Software") to copy, merge, modify, publish,
 * distribute, sublicense, and / or sell copies of Software.
 *
 * All Software included is provided in an "as is" state, without any
 * type or form of warranty. The Authors and Copyright Holders of this
 * piece of software, documentation, or source code waive all
 * responsibility and shall not be liable for any claim, damages, or
 * other forms of liability, regardless of the form it may take.
 *
 * Any form of re-distribution of Software is required to have this same
 * copyright notice included in any source files or forms of documentation
 * which have stemmed or branched off of the original Software.
 *
 * **
 *
 */

package org._11253.lib.op;

import org._11253.lib.Global;
import org._11253.lib.controllers.Controller;
import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.telem.Telemetry;

/**
 * Tele-Op template class which uses the op mode template class.
 * <p>
 * Provides a very simple extension of the default operation mode template.
 * There's really not much else it does. Yeah, that's it. Exciting, I know.
 * </p>
 *
 * @author Colin Robertson
 */
public class TeleOp extends Template {
    public Controller controller1;
    public Controller controller2;

    /**
     * Constructor. Yeah, that's really it.
     * <p>
     * Damn, I really feel bad for all the poor
     * people who might be reading this documentation
     * for genuine help on what the hell they're doing
     * wrong. Well, it's not my problem, so...
     * Good luck to you, brave adventurer!
     * </p>
     */
    public TeleOp() {
        addToStart();
        mapStartButton();
        addToOnStartRun();
        addToOnFinishRun();
    }

    /**
     * Update telem after run loop finishes
     * <p>
     * This should run every single loop to
     * update the printed telem data to what
     * the latest telem data is. I know,
     * so cool. You wish you could be that
     * cool, but you can't.
     * </p>
     */
    private void addToOnFinishRun() {
//        onFinishRun.add(new Runnable() {
//            @Override
//            public void run() {
//                Telemetry.printTelemetry();
//            }
//        });
    }

    /**
     * Example method that shows how control mapping is done.
     * <p>
     * This can be over-written in a user's extension of the TeleOp class by simply
     * re-mapping what happens on START. This just exists as as a demonstration of how
     * control mapping works.
     * </p>
     */
    private void mapStartButton() {
        onStart.add(new Runnable() {
            @Override
            public void run() {
                controller1.map.bind(ControllerMap.States.START, new Command() {
                    @Override
                    public Runnable active() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                Telemetry.addData("_1125c_START_BUTTON", "Start button status", "on");
                            }
                        };
                    }

                    @Override
                    public Runnable inactive() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                Telemetry.addData("_1125c_START_BUTTON", "Start button status", "off");
                            }
                        };
                    }
                });
            }
        });
    }

    /**
     * Add to the onStart list in Template class.
     * <p>
     * This just sets the global gamepads and sets up
     * both of the controllers for use in other files.
     * </p>
     */
    private void addToStart() {
        onStart.add(new Runnable() {
            @Override
            public void run() {
                Global.setGamepad1(gamepad1);
                Global.setGamepad2(gamepad2);
                controller1 = new Controller(gamepad1);
                controller2 = new Controller(gamepad2);
            }
        });
    }

    /**
     * Run the controller's maps whenever the loop begins again.
     * <p>
     * I don't know why anybody would possibly want to change this,
     * but even if they did, they can't, so it would really suck to be them,
     * now wouldn't it?
     * </p>
     */
    private void addToOnStartRun() {
        onStartRun.add(new Runnable() {
            @Override
            public void run() {
                controller1.map.runMap();
                controller2.map.runMap();
            }
        });
    }
}
