package me.wobblyyyy.pathfinder.recording;

import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

import java.util.HashMap;

public class Recorder {
    public static HashMap<Long, PfMotorPower> record = new HashMap<>();
    public static long offset = 0;
    public static PfMotorPower power = new PfMotorPower(0, 0, 0, 0);
    public static final String name = "Recorder";

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
                                        power
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
        for (HashMap.Entry<Long, PfMotorPower> entry : record.entrySet()) {
            long delay = entry.getKey();
            PfMotorPower motorPower = entry.getValue();

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
                                    // TODO SET PATHFINDER SUGGESTED MOTOR POWER HERE !!
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
