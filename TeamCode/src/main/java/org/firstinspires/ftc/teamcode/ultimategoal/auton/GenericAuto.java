/****
 * Made by Tejas Mehta
 * Made on Thursday, October 15, 2020
 * File Name: AutoRed
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.auton*/
package org.firstinspires.ftc.teamcode.ultimategoal.auton;

import com.tejasmehta.OdometryCore.OdometryCore;
import com.tejasmehta.OdometryCore.localization.EncoderPositions;
import com.tejasmehta.OdometryCore.localization.HeadingUnit;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import org._11253.lib.op.Auton;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.robot.phys.assm.drivetrain.MotionType;
import org._11253.lib.robot.phys.components.Motor;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.OdometryThread;

public class GenericAuto extends Auton {

    private double offsetPos;
    public Drivetrain drivetrain = new Drivetrain();

    public GenericAuto(final double offsetPos) {
        this.offsetPos = offsetPos;
        initializeOdometry();
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                drivetrain.init();
                OdometryThread.initialize(offsetPos);
            }
        });
    }

    public void initializeOdometry() {
        //Initialize odometry with the given values
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                OdometryThread.getInstance().startThread();
            }
        });
    }

    public OdometryPosition getCurrentPos() {
        return OdometryThread.getInstance().getCurrentPosition();
    }

    public void odometryMove(double x, double y) {

        if (x > getCurrentPos().getX()) {
            while (x > getCurrentPos().getX()) {
                drivetrain.meccanumDrive(MotionType.RIGHT, 0.5);
            }
        } else {
            while (x < getCurrentPos().getX()) {
                drivetrain.meccanumDrive(MotionType.LEFT, 0.5);
            }
        }

        if (y > getCurrentPos().getY()) {
            while (y > getCurrentPos().getX()) {
                drivetrain.meccanumDrive(MotionType.FORWARD, 0.5);
            }
        } else {
            while (y < getCurrentPos().getX()) {
                drivetrain.meccanumDrive(MotionType.BACKWARD, 0.5);
            }
        }
    }

    public void odometryTurnMove(double x, double y) {
        double radHeading = Math.atan2(y-getCurrentPos().getY(), x-getCurrentPos().getX());
        odometryTurn(Math.toDegrees(radHeading));
        while (getCurrentPos().getX() < x) {
            drivetrain.meccanumDrive(MotionType.FORWARD, 0.5);
        }
    }

    public void odometryTurn(double headingDegrees) {
        double targetDegrees = getCurrentPos().getHeadingRadians() + headingDegrees;
        while (getCurrentPos().getHeadingDegrees() != targetDegrees) {
            drivetrain.meccanumDrive(MotionType.TURN_CCW, 0.3);
        }
    }

}
