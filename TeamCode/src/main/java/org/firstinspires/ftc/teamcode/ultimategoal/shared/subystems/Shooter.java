package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import org._11253.lib.Global;
import org._11253.lib.robot.phys.components.CRServo;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

public class Shooter {
    public Motor flywheel1;
    public Motor flywheel2;
    public CRServo loader;
    public CRServo pusher;

    public String flywheel1Name = "flywheel1";
    public String flywheel2Name = "flywheel2";
    public String loaderName = "loader";
    public String pusherName = "pusher";

    public boolean isShooterActive = false;
    public boolean isLoaderActive = false;
    public boolean isPusherActive = false;
    public boolean isActive = false;

    final int shootLength = 1000;
    final int shootDelay = 1000;
    final int loadLength = 1000;
    final int loadDelay = 1000;
    final int pushLength = 1000;
    final int pushDelay = 1000;

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
        loader.setPower(1.0);
    }

    public void stopLoader() {
        isLoaderActive = false;
        loader.setPower(0.0);
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
        pusher.setPower(1.0);
    }

    public void stopPusher() {
        isPusherActive = false;
        pusher.setPower(0.0);
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
                    loadDelay + pushDelay,
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
                    loadDelay + pushDelay + shootDelay,
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
                    loadDelay + pushDelay + shootDelay,
                    0,
                    new Timed() {
                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    isActive = true;
                                }
                            };
                        }

                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    isActive = false;
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException("Global hardware map has to be initialized before initializing the shooter.");
        }

        flywheel1 = new Motor(flywheel1Name);
        flywheel2 = new Motor(flywheel2Name);
        loader = new CRServo(loaderName);
        pusher = new CRServo(pusherName);
    }
}
