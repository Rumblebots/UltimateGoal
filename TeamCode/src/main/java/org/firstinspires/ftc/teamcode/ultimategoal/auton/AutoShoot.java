/****
 * Made by Tejas Mehta
 * Made on Tuesday, December 01, 2020
 * File Name: AutoShoot
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.auton*/
package org.firstinspires.ftc.teamcode.ultimategoal.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Main Auo", group = "Auton")
public class AutoShoot extends GenericAuto {
    public AutoShoot() {
        super(42);
        onStart.add(new Runnable() {
            @Override
            public void run() {
                // TODO move up
                odometryMove(0, 6);
                // TODO turn to goal 1
                odometryTurn(5.5, 126);
                shoot();
                // TODO turn to goal 2
                odometryTurn(13, 126);
                shoot();
                // TODO turn to goal 3
                odometryTurn(20.5, 126);
                shoot();
                // TODO turn straight
                odometryTurnMove(getCurrentPos().getX(), 60);
                // TODO park
            }
        });
    }

}
