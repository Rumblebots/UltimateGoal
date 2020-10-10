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
    private Motor    flywheel1;                   // Flywheels are very cool, I know. Very, very cool.
                                                  // The first of two flywheels. Used in the portion of the shooter which actually
                                                  // shoots things out.
                                                  // Note that the two flywheels used here are chained together. They don't actually
                                                  // spin independently - Brodie says that they can both spin in the same direction.
    private Motor    flywheel2;                   // Our second flywheel! Even cooler than the first, interestingly enough.
    private Servo    loader;                      // I'm not entirely sure all of this works, but the goal of the loader is to load a
                                                  // ring into a system which will then push the ring into a shooting device and finally
                                                  // will be shot out of the robot.
    private Servo    pusher;                      // The purpose of the pusher is to push a ring into the flywheels, from the loader, in
                                                  // order for them to get shot out. This is a servo - might be interesting to try to
                                                  // switch it up and use a CR servo so things actually work better?
                                                  // Man... I gotta ask. How long are you gonna read these comments for?
    public String    flywheel1Name = "flywheel1"; // It's important to note that absolutely none of this matters in the
    public String    flywheel2Name = "flywheel2"; // slightest and you're definitely wasting your time reading it...
    public String    loaderName = "loader";       // but hey, you're a real one.
    public String    pusherName = "pusher";       // I love you cutie <3

    // These are four privately and internally used variables which dictate which portions of the shooter are active
    // at which points. Interestingly enough, all of the code I could possibly write would still run near instantly,
    // because these phones are surprisingly powerful. These are all used to determine whether an element can be
    // activated again.
    /** {@link #shoot()} */
    private boolean  isShooterActive = false;
    /** {@link #load()} */
    private boolean  isLoaderActive = false;
    /** {@link #push()} */
    private boolean  isPusherActive = false;
    /** {@link #shootRing()} */
    private boolean  isActive = false;

    // Here we have a couple timings. Fairly important to note - all of these times are in milliseconds. Not seconds.
    // These are constants, so they can't be changed. These values need to be determined experimentally (or through
    // really complex math that I don't have the time, energy, nor ability to do.
    /** How long the shooter shoots. */  //
    final int        shootLength = 1000; // How long the shooting flywheel is active. Note that there are two motors which
                                         // are controlled by the single shooter function.
    /**                                  //
     * How long the shooter takes        //
     * before it starts spinning up.     //
     */                                  //
    final int        shootDelay = 1000;  // How long, after the function starts, it should be before the shooter itself
                                         // ends up shooting out the ring. The projectile.
    /** How long the loader loads. */    //
    final int        loadLength = 1000;  // The length for which the loader servo will be active.
    /** Loader delay! */                 //
    final int        loadDelay = 1000;   // How long before the loader servo actually moves.
                                         // Since everything is done using my (very amazing and very cool) system for
                                         // scheduling and running events "asynchronously," these delays are independent
                                         // of each other - meaning you can have two sub-sub-systems active at the same
                                         // exact time. Amazing, isn't it?
    /**                                  //
     * How long the pusher...            //
     * well, pushes for.                 //
     */                                  //
    final int        pushLength = 1000;  // The length for which the pusher servo will be active.
    /** Delay! For the pusher! */        //
    final int        pushDelay = 1000;   // Read the above comments - swag.

    private int      timeRemaining = 0;
    private int      timeElapsed = 0;

    /** Used in {@link #shoot()} */
    private void startShooter() {
        isShooterActive = true;
        flywheel1.setPower(1.0);
        flywheel2.setPower(1.0);
    }

    /** Used in {@link #shoot()} */
    private void stopShooter() {
        isShooterActive = false;
        flywheel1.setPower(0.0);
        flywheel2.setPower(0.0);
    }

    /** Schedule an asynchronous event to shoot a ring out. */
    private void shoot() {
        if (!isShooterActive) {
            StringEvents.schedule(
                    "Shooter",
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

    /** Used in {@link #load()} */
    private void startLoader() {
        isLoaderActive = true;
        loader.setPosition(1.0);
    }

    /** Used in {@link #load()} */
    private void stopLoader() {
        isLoaderActive = false;
        loader.setPosition(0.0);
    }

    /** Load a ring from the magazine to the pusher. */
    private void load() {
        if (!isLoaderActive) {
            StringEvents.schedule(
                    "Shooter",
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

    /** Used in {@link #push()} */
    private void startPusher() {
        isPusherActive = true;
        pusher.setPosition(1.0);
    }

    /** Used in {@link #push()} */
    private void stopPusher() {
        isPusherActive = false;
        pusher.setPosition(0.0);
    }

    /** Push a ring from the loading system into the flywheels. */
    private void push() {
        if (!isPusherActive) {
            StringEvents.schedule(
                    "Shooter",
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

    /**
     * Get how long it takes for the entire shooting subsystem to function.
     * <p>
     *     So, basically, because I'm cool, I have this to determine how long the shooter can run for. The longer it
     *     runs for, the longer the shooter has to be blocked off from access.
     * </p>
     * @return the longest time (in ms) which the shooter can possibly run
     */
    private int getLongestLength() {
        int[] delays = {
                loadDelay + loadLength,
                pushDelay + pushLength,
                shootDelay + shootLength
        };
        Arrays.sort(delays);
        return delays[0];
    }

    /**
     * Actually shoot the entire ring out.
     * {@link #load()} {@link #loadDelay} {@link #loadLength}
     * {@link #push()} {@link #pushDelay} {@link #pushLength}
     * {@link #shoot()} {@link #shootDelay} {@link #shootLength}
     */
    public void shootRing() {
        if (!isActive) {
            StringEvents.schedule(
                    "Shooter",
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
                    "Shooter",
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
                    "Shooter",
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
                    "Shooter",
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

    /** See if the shooter is active or inactive, in a string format. */
    private String getShooterStatus() {
        if (isActive) return "active";
        else return "inactive";
    }

    /** Format a string. Swag. */
    private String getRemainingMs() {
        return timeRemaining + "ms";
    }

    /** Format another string. Even more swag. */
    private String getElapsedMs() {
        return timeElapsed + "ms";
    }

    /**
     * Used for telemetry, shows shooter status.
     * <p>
     *     Although it doesn't have much of a practical benefit, having additional telemetry might be helpful
     *     at some point in the near future. Nothing could really go wrong here.
     * </p>
     */
    public void showActive() {
        Telemetry.addData(
                "Shooter_status",
                "Shooter " + getShooterStatus(),
                " for ",
                getElapsedMs() + ", " + getRemainingMs() + " remaining"
        );
    }

    /** Initialize the whole subsystem. */
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
