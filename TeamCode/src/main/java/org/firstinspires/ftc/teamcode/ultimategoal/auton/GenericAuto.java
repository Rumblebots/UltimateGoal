/****
 * Made by Tejas Mehta
 * Made on Thursday, October 15, 2020
 * File Name: AutoRed
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.auton*/
package org.firstinspires.ftc.teamcode.ultimategoal.auton;

import org._11253.lib.op.Auton;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.Odometry;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.OdometryWheels;

public class GenericAuto extends Auton {

    public void initializeOdometry(int pos) {
        //Initialize odometry with the given values
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                System.out.println(Odometry.getInstance().currentPose);
            }
        });
    }

}
