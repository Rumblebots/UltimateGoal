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
import org._11253.lib.motors.SourceType;
import org._11253.lib.motors.SourcedMotorPower;
import org._11253.lib.op.TeleOp;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.telem.Telemetry;

/**
 * A super simple meccanum drivetrain.
 *
 * <p>
 * This uses addition and subtraction rather
 * than trig to calculate the power the motors
 * should be set to. Yeah, that's all.
 * </p>
 *
 * <p>
 * Do note that this is NOT a class which is modifiable. What I mean by this is that
 * you can't change some of the values in this if you don't like it. You can feel more
 * than free to copy this class and change the values, but if you extend this class,
 * you can't possibly change them.
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
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                drivetrain.init();
            }
        });
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        controller1.map.bind(ControllerMap.States.STICK, new Command() {
                            @Override
                            public Runnable active() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
//                                        double x = controller1.getLeftX();
//                                        double y = controller1.getLeftY();
//
//                                        double turn = Math.atan2(
//                                                controller1.getRightY(),
//                                                controller1.getRightX()
//                                        );
//                                        double magnitude = Math.sqrt(
//                                                Math.pow(x, 2.0) + Math.pow(y, 2.0)
//                                        );
//
//                                        double fr = (Math.sin(turn + (0.25 * Math.PI))) * magnitude + turn;
//                                        double fl = (-Math.sin(turn - (0.25 * Math.PI))) * magnitude + turn;
//                                        double br = (Math.sin(turn - (0.25 * Math.PI))) * magnitude + turn;
//                                        double bl = (-Math.sin(turn + (0.25 * Math.PI))) * magnitude + turn;
//
//                                        double max = Math.max(
//                                                Math.max(
//                                                        Math.abs(fr),
//                                                        Math.abs(fl)
//                                                ),
//                                                Math.max(
//                                                        Math.abs(br),
//                                                        Math.abs(bl)
//                                                )
//                                        );
//                                        double factor = 1 / max;
//
//                                        // Motors need to be scaled down in terms of
//                                        // maximum output power.
//                                        if (max > 1) {
//                                            fr *= factor;
//                                            fl *= factor;
//                                            br *= factor;
//                                            bl *= factor;
//                                        }
//
//                                        br *= -1;
//                                        bl *= -1;
//
//                                        drivetrain.setPower(
//                                                new SourcedMotorPower(
//                                                        fr,
//                                                        fl,
//                                                        br,
//                                                        bl,
//                                                        SourceType.USER
//                                                )
//                                        );
//
//                                        Telemetry.addData(
//                                                "_1125c_MOTORS",
//                                                "Motors engaged",
//                                                "? ",
//                                                "true"
//                                        );
//                                        Telemetry.addData(
//                                                "_1125c_MOTORS_2",
//                                                "FR, FL, BR, BL",
//                                                ": ",
//                                                fr + " " + fl + " " + br + " " + bl
//                                        );
                                        drivetrain.setPower(
                                                new SourcedMotorPower(
                                                        controller1.getLeftY() + controller1.getRightX() + controller1.getLeftX(),
                                                        -controller1.getLeftY() + controller1.getRightX() + controller1.getLeftX(),
                                                        controller1.getLeftY() + controller1.getRightX() - controller1.getLeftX(),
                                                        -controller1.getLeftY() + controller1.getRightX() - controller1.getLeftX(),
                                                        SourceType.USER
                                                )
                                        );
                                    }
                                };
                            }

                            @Override
                            public Runnable inactive() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
//                                        if (!controller1.manager.isUserControlled()) return;
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
