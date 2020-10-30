package org._11253.lib.drives;

import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.telem.Telemetry;

/**
 * An implementation of the MeccanumDrive that contains a built-in shifter.
 *
 * <p>
 * If you're confused, go check out {@link Meccanum}, which this class extends. This
 * is literally just a really primitive meccanum drive with a multi-speed shifter so
 * you can precisely control the speed at which the robot goes. As you'd expect with
 * just about everything in this whole library, I don't know what I'm saying because
 * I lost my train of thought and I'm tired.
 * </p>
 */
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
