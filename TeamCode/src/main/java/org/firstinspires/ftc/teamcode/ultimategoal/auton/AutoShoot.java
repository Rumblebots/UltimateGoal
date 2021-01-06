/****
 * Made by Tejas Mehta
 * Made on Tuesday, December 01, 2020
 * File Name: AutoShoot
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.auton*/
package org.firstinspires.ftc.teamcode.ultimategoal.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//@Autonomous(name = "Main Auo", group = "Auton")
public class AutoShoot extends GenericAuto {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor shooter1;
    DcMotor shooter2;
    DcMotor intake;

    Servo intakePush;
    Servo magazine;
    Servo flipper;

    CRServo intakeCR;



//    public AutoShoot() {
////        super(42);
//        onStart.add(new Runnable() {
//            @Override
//            public void run() {
//                // TODO move up
//                odometryMove(42, 12);
//                // TODO turn to goal 1
////                odometryTurn(47.5, 126);
////                shoot();
////                // TODO turn to goal 2
////                odometryTurn(55, 126);
////                shoot();
////                // TODO turn to goal 3
////                odometryTurn(62.5, 126);
////                shoot();
//                // TODO turn straight
////                odometryTurnMove(getCurrentPos().getX(), 60);
//                // TODO park
//            }
//        });
//    }

}
