package org.firstinspires.ftc.teamcode.ultimategoal.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.utils.gen.Toggle;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Intake;
import org._11253.lib.utils.Command;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Lengths;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Shooter;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Storage;

/**
 * Default drive code for this year's driver-controlled period. Because I'm a complete and utter savage (and want to
 * make it seem like some of the "work" I did over the summer had any impact on this season), I'm using the collection of
 * code I already wrote so I don't have to write more code. Intelligent!
 */
@TeleOp(name = "UG Meccanum Drive", group = "TeleOp")
public class MeccanumDrive extends ShifterMeccanum {
    public Intake intake = new Intake();
    public Storage storage = new Storage();
    public Shooter shooter = new Shooter(
            storage,
            Lengths.shootLength,
            Lengths.shootDelay,
            Lengths.loadLength,
            Lengths.loadDelay,
            Lengths.pushLength,
            Lengths.pushDelay
    );

    private Toggle intakeToggle = new Toggle(false);
    private Toggle storageToggle = new Toggle(false);
    private Toggle shooterToggle = new Toggle(false);

    private boolean canBeChanged;

    private void bindIntake() {
        controller2.map.bind(
                ControllerMap.States.LEFT_TRIGGER,
                new Command() {
                    @Override
                    public Runnable active() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                intake.setPower(1.0);
                            }
                        };
                    }
                }
        );
        controller2.map.bind(
                ControllerMap.States.RIGHT_TRIGGER,
                new Command() {
                    @Override
                    public Runnable active() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                intake.setPower(-1.0);
                            }
                        };
                    }

                    @Override
                    public Runnable inactive() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                intake.setPower(0.0);
                            }
                        };
                    }
                }
        );
    }

    private void bindStorage() {

    }

    private void bindShooter() {
        controller2.map.bind(
                ControllerMap.States.A,
                new Command() {
                    @Override
                    public Runnable active() {
                        return super.active();
                    }

                    @Override
                    public Runnable inactive() {
                        return super.inactive();
                    }
                }
        );
    }

    public MeccanumDrive() {
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                intake.init();
            }
        });

        onStart.add(
                // Bind controls for intake
                new Runnable() {
                    @Override
                    public void run() {
                        controller2.map.bind(ControllerMap.States.A, new Command() {
                            @Override
                            public Runnable active() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        storage.increment();
                                        shooter.shootRing();
                                        canBeChanged = false;
                                    }
                                };
                            }

                            @Override
                            public Runnable inactive() {
                                return new Runnable() {
                                    @Override
                                    public void run() {
                                        canBeChanged = true;
                                    }
                                };
                            }
                        });
                    }
                },
                // Bind automatic telemetry updating
                new Runnable() {
                    @Override
                    public void run() {
                        StringEvents.schedule(
                                "_1125c_AUTOTELEMETRY",
                                250,
                                0,
                                new Timed() {
                                    @Override
                                    public Runnable open() {
                                        return new Runnable() {
                                            @Override
                                            public void run() {
                                                storage.showCount();
                                                shooter.showActive();
                                            }
                                        };
                                    }
                                },
                                true
                        );
                    }
                },
                // Bind controls for shooter
                new Runnable() {
                    @Override
                    public void run() {

                    }
                }
        );
    }
}
