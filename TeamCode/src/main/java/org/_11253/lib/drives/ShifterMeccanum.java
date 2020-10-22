package org._11253.lib.drives;

import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.telem.Telemetry;

public class ShifterMeccanum extends Meccanum {
    final double FAST = 1.0;
    final double NORMAL = 0.5;
    final double SLOW = 0.25;

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
                                        if (!controller1.manager.isUserControlled()) return;
                                        drivetrain.setMultiplier(FAST);
                                        Telemetry.addData(
                                                "_1125c_MULTIPLIER",
                                                "Multiplier",
                                                ": ",
                                                String.valueOf(FAST)
                                        );
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
                                        if (!controller1.manager.isUserControlled()) return;
                                        drivetrain.setMultiplier(SLOW);
                                        Telemetry.addData(
                                                "_1125c_MULTIPLIER",
                                                "Multiplier",
                                                ": ",
                                                String.valueOf(SLOW)
                                        );
                                    }
                                };
                            }

                            @Override
                            public Runnable inactive() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!controller1.manager.isUserControlled()) return;
                                        if (controller1.getRightTrigger() == 0) {
                                            drivetrain.setMultiplier(NORMAL);
                                            Telemetry.addData(
                                                    "_1125c_MULTIPLIER",
                                                    "Multiplier",
                                                    ": ",
                                                    String.valueOf(NORMAL)
                                            );
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
