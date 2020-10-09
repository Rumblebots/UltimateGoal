package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import org._11253.lib.Global;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.robot.phys.components.Servo;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;
import org._11253.lib.utils.telem.Telemetry;

import java.util.Arrays;

/**
 * A quick (and hopefully) functional shooter subsystem. Because FTC software runs incredibly well, regardless of it's
 * very poor optimization, a lot of this is done using StringEvents to operate several different sub-sub-systems at the
 * same time. In other words, you can press a single button and the entire shooting process will be cycled. Say, for
 * example, you press A. Immediately after, the {@link #shootRing()} function will start - and swaggy things happen
 * from there.
 */
public class Shooter {
    public Motor   flywheel1;                   // Flywheels are very cool, I know. Very, very cool.
                                                // The first of two flywheels. Used in the portion of the shooter which actually
                                                // shoots things out.
                                                // Note that the two flywheels used here are chained together. They don't actually
                                                // spin independently - Brodie says that they can both spin in the same direction.
    public Motor   flywheel2;                   // Our second flywheel! Even cooler than the first, interestingly enough.
    public Servo   loader;                      // I'm not entirely sure all of this works, but the goal of the loader is to load a
                                                // ring into a system which will then push the ring into a shooting device and finally
                                                // will be shot out of the robot.
    public Servo   pusher;                      // The purpose of the pusher is to push a ring into the flywheels, from the loader, in
                                                // order for them to get shot out. This is a servo - might be interesting to try to
                                                // switch it up and use a CR servo so things actually work better?

    public String  flywheel1Name = "flywheel1";
    public String  flywheel2Name = "flywheel2";
    public String  loaderName = "loader";
    public String  pusherName = "pusher";

    public boolean isShooterActive = false;
    public boolean isLoaderActive = false;
    public boolean isPusherActive = false;
    public boolean isActive = false;

    final int      shootLength = 1000;
    final int      shootDelay = 1000;
    final int      loadLength = 1000;
    final int      loadDelay = 1000;
    final int      pushLength = 1000;
    final int      pushDelay = 1000;

    private int    timeRemaining = 0;
    private int    timeElapsed = 0;

    public void startShooter() {
        isShooterActive = true;
        flywheel1.setPower(1.0);
        flywheel2.setPower(1.0);
    }

    public void stopShooter() {
        isShooterActive = false;
        flywheel1.setPower(0.0);
        flywheel2.setPower(0.0);
    }

    public void shoot() {
        if (!isShooterActive) {
            StringEvents.schedule(
                    "Shooter_shoot",
                    shootLength,
                    0,
                    new Timed() {
                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    startShooter();
                                }
                            };
                        }

                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    stopShooter();
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    public void startLoader() {
        isLoaderActive = true;
        loader.setPosition(1.0);
    }

    public void stopLoader() {
        isLoaderActive = false;
        loader.setPosition(0.0);
    }

    public void load() {
        if (!isLoaderActive) {
            StringEvents.schedule(
                    "Shooter_load",
                    loadLength,
                    0,
                    new Timed() {
                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    startLoader();
                                }
                            };
                        }

                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    stopLoader();
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    public void startPusher() {
        isPusherActive = true;
        pusher.setPosition(1.0);
    }

    public void stopPusher() {
        isPusherActive = false;
        pusher.setPosition(0.0);
    }

    public void push() {
        if (!isPusherActive) {
            StringEvents.schedule(
                    "Shooter_push",
                    pushLength,
                    0,
                    new Timed() {
                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    startPusher();
                                }
                            };
                        }

                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    stopPusher();
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    private int getLongestLength() {
        int[] delays = {
                loadDelay + loadLength,
                pushDelay + pushLength,
                shootDelay + shootLength
        };
        Arrays.sort(delays);
        return delays[0];
    }

    public void shootRing() {
        if (!isActive) {
            StringEvents.schedule(
                    "Shooter_shooter_loader",
                    0,
                    loadDelay,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    load();
                                }
                            };
                        }
                    },
                    false
            );
            StringEvents.schedule(
                    "Shooter_shooter_pusher",
                    0,
                    pushDelay,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    push();
                                }
                            };
                        }
                    },
                    false
            );
            StringEvents.schedule(
                    "Shooter_shooter_shooter",
                    0,
                    shootDelay,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    shoot();
                                }
                            };
                        }
                    },
                    false
            );
            StringEvents.schedule(
                    "Shooter_shooter_activity",
                    getLongestLength(),
                    0,
                    new Timed() {
                        int longest = getLongestLength();
                        long last = 0;
                        long difference = 0;

                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    isActive = true;
                                    timeElapsed = 0;
                                    timeRemaining = longest;
                                }
                            };
                        }

                        @Override
                        public Runnable during() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    long current = System.currentTimeMillis();
                                    difference = current - last;
                                    last = current;
                                    timeElapsed += difference;
                                    timeRemaining -= difference;
                                }
                            };
                        }

                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    isActive = false;
                                    timeElapsed = 0;
                                    timeRemaining = 0;
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    private String getShooterStatus() {
        if (isActive) return "active";
        else return "inactive";
    }

    private String getRemainingMs() {
        return timeRemaining + "ms";
    }

    private String getElapsedMs() {
        return timeElapsed + "ms";
    }

    public void showActive() {
        Telemetry.addData(
                "Shooter_status",
                "Shooter " + getShooterStatus(),
                " for ",
                getElapsedMs() + ", " + getRemainingMs() + " remaining"
        );
    }

    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException("Global hardware map has to be initialized before initializing the shooter.");
        }

        flywheel1 = new Motor(flywheel1Name);
        flywheel2 = new Motor(flywheel2Name);
        loader = new Servo(loaderName);
        pusher = new Servo(pusherName);
    }
}
