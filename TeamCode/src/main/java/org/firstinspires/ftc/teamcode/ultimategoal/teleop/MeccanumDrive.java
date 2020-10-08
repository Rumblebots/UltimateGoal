package org.firstinspires.ftc.teamcode.ultimategoal.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.robot.phys.assm.Intake;
import org._11253.lib.utils.Command;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Storage;

/**
 * Default drive code for this year's driver-controlled period. Because I'm a complete and utter savage (and want to
 * make it seem like some of the "work" I did over the summer had any impact on this season), I'm using the collection of
 * code I already wrote so I don't have to write more code. Intelligent!
 */
@TeleOp(name = "Meccanum Drive", group = "TeleOp")
public class MeccanumDrive extends ShifterMeccanum {
    Intake intake = new Intake();
    public MeccanumDrive() {
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                intake.init();
            }
        });

        onStart.add(new Runnable() {
            @Override
            public void run() {
                controller2.map.bind(ControllerMap.States.LEFT_STICK_X, new Command() {
                    @Override
                    public Runnable active() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                double speed = controller2.getLeftX();
                                intake.setPower(speed);
                            }
                        };
                    }

                    @Override
                    public Runnable inactive() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                intake.setPower(0);
                            }
                        };
                    }
                });
            }
        });

        onStartRun.add(new Runnable() {
            @Override
            public void run() {
                Storage.showCount();
            }
        });
    }
}
