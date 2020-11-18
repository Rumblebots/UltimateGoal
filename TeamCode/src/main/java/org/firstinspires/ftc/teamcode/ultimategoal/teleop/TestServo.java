/****
 * Made by Tejas Mehta
 * Made on Thursday, November 12, 2020
 * File Name: TestServo
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.teleop*/
package org.firstinspires.ftc.teamcode.ultimategoal.teleop;

import org._11253.lib.controllers.ControllerMap;
import org._11253.lib.op.TeleOp;
import org._11253.lib.robot.phys.components.Servo;
import org._11253.lib.utils.Command;

public class TestServo extends TeleOp {
    Servo s;
    TestServo() {
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                s = new Servo("testServo");
            }
        });
        onStart.add(new Runnable() {
            @Override
            public void run() {
                controller1.map.bind(ControllerMap.States.LEFT_STICK_X, new Command() {
                    @Override
                    public Runnable active() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                s.setPosition(controller1.getLeftX());
                            }
                        };
                    }
                });
            }
        });
    }
}
