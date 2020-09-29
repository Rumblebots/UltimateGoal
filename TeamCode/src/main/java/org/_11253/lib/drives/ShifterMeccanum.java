package org._11253.lib.drives;

import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.utils.Command;

public class ShifterMeccanum extends Meccanum {
    public ShifterMeccanum() {
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        controller1.map.bind(ControllerMap.States.RIGHT_TRIGGER, new Command() {
                            @Override
                            public Runnable active() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        drivetrain.setMultiplier(2.0);
                                    }
                                };
                            }
                        });
                        controller1.map.bind(ControllerMap.States.LEFT_TRIGGER, new Command() {
                            @Override
                            public Runnable active() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        drivetrain.setMultiplier(0.5);
                                    }
                                };
                            }

                            @Override
                            public Runnable inactive() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        if (controller1.getRightTrigger() == 0) {
                                            drivetrain.setMultiplier(1.0);
                                        }
                                    }
                                };
                            }
                        });
                    }
                }
        );
    }
}
