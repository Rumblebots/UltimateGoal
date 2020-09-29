/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 10:52 PM
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

package org._11253.lib.drives;

import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.motors.MotorPower;
import org._11253.lib.op.TeleOp;
import org._11253.lib.robot.phys.assm.Drivetrain;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.math.Math;
import org._11253.lib.utils.telem.Telemetry;

/**
 * A super simple meccanum drivetrain.
 * <p>
 * This uses addition and subtraction rather
 * than trig to calculate the power the motors
 * should be set to. Yeah, that's all.
 * </p>
 *
 * @author Colin Robertson
 */
public class Meccanum extends TeleOp {
    /**
     * Instance of the drivetrain we'd like to use.
     */
    public Drivetrain drivetrain = new Drivetrain();

    /**
     * Whenever a new instance of Meccanum is created
     * run a couple setup things.
     * <p>
     * In order, this initializes the drivetrain and then
     * binds drive controls to when either of the sticks
     * have in any way moved.
     * </p>
     * <p>
     * The 'formulas' we use for the meccanum wheels isn't
     * exactly complicated, and I'm sure you can find more
     * information on it with a quick Google search. For
     * whatever reason, we can't get it to work using the
     * trigonometry method, so the additive one works
     * just as well.
     * </p>
     */
    public Meccanum() {
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        drivetrain.init();
                        controller1.map.bind(ControllerMap.States.STICK, new Command() {
                            @Override
                            public Runnable active() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        double speed = controller1.getLeftY();
                                        double strafe = controller1.getInvLeftX();
                                        double turn = controller1.getRightX();
                                        drivetrain.setPower(
                                                new MotorPower(
                                                        Math.clip(speed) - Math.clip(turn) + Math.clip(strafe),
                                                        Math.clip(speed) + Math.clip(turn) - Math.clip(strafe),
                                                        Math.clip(speed) - Math.clip(turn) - Math.clip(strafe),
                                                        Math.clip(speed) + Math.clip(turn) + Math.clip(strafe)
                                                )
                                        );
                                        Telemetry.addData(
                                                "_1125c_MOTORS",
                                                "Motors engaged",
                                                "? ",
                                                "true"
                                        );
                                    }
                                };
                            }

                            @Override
                            public Runnable inactive() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        drivetrain.setPower(new MotorPower());
                                        Telemetry.addData(
                                                "_1125c_MOTORS",
                                                "Motors engaged",
                                                "? ",
                                                "false"
                                        );
                                    }
                                };
                            }
                        });
                    }
                }
        );
    }
}
