package org._11253.lib.playback;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder.recording.Recorder;
import org._11253.lib.controllers.Controller;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

import java.util.HashMap;

public class Playback {
    public static HashMap<Long, ControllerState> record = new HashMap<>();
    public static Controller controller1;
    public static Controller controller2;
    public static long offset = 0;
    public static PfMotorPower power = new PfMotorPower(0, 0, 0, 0);
    public static final String name = "Recorder";

    public static void setController1(Controller controller) {
        controller1 = controller;
    }

    public static void setController2(Controller controller) {
        controller2 = controller;
    }

    public static void startRecording(long duration,
                                      long delay,
                                      long interval) {
        StringEvents.schedule(
                name,
                duration,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                record = new HashMap<>();
                                offset = System.currentTimeMillis();
                            }
                        };
                    }
                },
                false
        );
        StringEvents.schedule(
                name,
                interval,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                long currentOffset = System.currentTimeMillis() - offset;
                                record.put(
                                        currentOffset,
                                        null
                                );
                            }
                        };
                    }
                },
                true
        );
        StringEvents.schedule(
                name + " (Execution Stop)",
                duration,
                delay,
                new Timed() {
                    @Override
                    public Runnable close() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                StringEvents.clear(name);
                            }
                        };
                    }
                },
                false
        );
    }

    public static void execute() {
        for (HashMap.Entry<Long, ControllerState> entry : record.entrySet()) {
            long delay = entry.getKey();
            final ControllerState state = entry.getValue();

            StringEvents.schedule(
                    name,
                    delay,
                    0,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {

                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    public static void update(PfMotorPower power) {
        Recorder.power = power;
    }
}































