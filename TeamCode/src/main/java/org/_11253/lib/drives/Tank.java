///*
// * **
// *
// * Copyright (c) 2020
// * Copyright last updated on 6/10/20, 10:58 PM
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
//package org._11253.lib.drives;
//
//import org._11253.lib.controllers.ControllerMap;
//import org._11253.lib.motors.MotorPower;
//import org._11253.lib.op.TeleOp;
//import org._11253.lib.robot.phys.assm.Drivetrain;
//import org._11253.lib.utils.Command;
//import org._11253.lib.utils.gen.Shifter;
//import org._11253.lib.utils.telem.Telemetry;
//
///**
// * Short and sweet drive train method which can be extended by other actual op modes.
// * <p>
// * Includes a basic speed-shifter (divisor) as well as mapping really simple
// * controls to controller 1, which is really just
// * </p>
// *
// * @author Colin Robertson
// */
//public class Tank extends TeleOp {
//    /**
//     * A drivetrain. Yes, that's really it.
//     * That's all there is to it.
//     * <p>
//     * What's that one saying like thing? Is it
//     * "that's all folks?"
//     * </p>
//     */
//    public Drivetrain drivetrain = new Drivetrain();
//
//    /**
//     * A simple speed-shifter.
//     * <p>
//     * This doesn't really have much of a practical application,
//     * as realistically, who the hell wants to have a three-speed
//     * virtually controlled shifter on a robot that can go a top
//     * of maybe 15 miles per hour? However, it looks cool,
//     * or something, and makes the file longer, which obviously makes it
//     * cooler... you get what I'm saying.
//     * </p>
//     */
//    public Shifter divisor = new Shifter(2, 3, 1);
//
//    /**
//     * Simple constructor for Tank class.
//     * <p>
//     * Does a couple things:
//     *     <ul>
//     *         <li>Runs super() to set up all of the stuff in TeleOp</li>
//     *         <li>Adds runnable to onStart which inits the drivetrain and maps controls</li>
//     *         <li>Write telemetry describing the current gear</li>
//     *     </ul>
//     * </p>
//     * <p>
//     *     PLEASE don't forget to always call super() when using a constructor for
//     *     init-based stuff, it's really important so all of the code functions properly.
//     * </p>
//     */
//    public Tank() {
//        super();
//
//        onStart.add(new Runnable() {
//            @Override
//            public void run() {
//                drivetrain.init();
//                mapControls();
//            }
//        });
//
//        onStartRun.add(new Runnable() {
//            @Override
//            public void run() {
//                Telemetry.addData("_1125c_TANK_GEAR", "Current Gear", Integer.toString(divisor.getCurrentGear()));
//            }
//        });
//    }
//
//    /**
//     * Map basic tank controls, which are just the following:
//     * <ul>
//     *     <li>right-left movement</li>
//     *     <li>gear shifting</li>
//     * </ul>
//     */
//    public void mapControls() {
//        controller1.map.bind(ControllerMap.States.STICK, new Command() {
//            @Override
//            public Runnable active() {
//                return new Runnable() {
//                    @Override
//                    public void run() {
//                        Telemetry.addData("_1125c_DRIVING",
//                                "Motors engaged?",
//                                " ",
//                                "True");
//                        drivetrain.setPower(new MotorPower(
//                                controller1.getRightY() / divisor.getCurrentGear(),
//                                controller1.getLeftY() / divisor.getCurrentGear(),
//                                controller1.getRightY() / divisor.getCurrentGear(),
//                                controller1.getLeftY() / divisor.getCurrentGear()
//                        ));
//                    }
//                };
//            }
//
//            @Override
//            public Runnable inactive() {
//                return new Runnable() {
//                    @Override
//                    public void run() {
//                        Telemetry.addData("_1125c_DRIVING",
//                                "Motors engaged?",
//                                " ",
//                                "False");
//                        drivetrain.setPower(new MotorPower());
//                    }
//                };
//            }
//        });
//        controller1.map.bind(ControllerMap.States.DPAD_UP, new Command() {
//            @Override
//            public Runnable active() {
//                return new Runnable() {
//                    @Override
//                    public void run() {
//                        divisor.onPressShiftUp();
//                    }
//                };
//            }
//        });
//        controller1.map.bind(ControllerMap.States.DPAD_DOWN, new Command() {
//            @Override
//            public Runnable active() {
//                return new Runnable() {
//                    @Override
//                    public void run() {
//                        divisor.onPressShiftDown();
//                    }
//                };
//            }
//
//            @Override
//            public Runnable inactive() {
//                return new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!controller1.getDpadUp()) {
//                            divisor.onRelease();
//                        }
//                    }
//                };
//            }
//        });
//    }
//}
