/****
 * Made by Tejas Mehta
 * Made on Tuesday, December 01, 2020
 * File Name: OdometryThread
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems*/
package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.tejasmehta.OdometryCore.OdometryCore;
import com.tejasmehta.OdometryCore.localization.EncoderPositions;
import com.tejasmehta.OdometryCore.localization.HeadingUnit;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;

public class OdometryThread extends Thread{

    private final DcMotor encoderLeft = Drivetrain.frontLeft.getDcMotorComponent();
    private final DcMotor encoderRight = Drivetrain.frontRight.getDcMotorComponent();
    private final DcMotor encoderBack = Drivetrain.backLeft.getDcMotorComponent();
    private final double offset;
    private OdometryPosition currentPosition;
    private boolean running = true;
    private static OdometryThread currentInstance;

    public static void initialize(double offset) {
        currentInstance = new OdometryThread(offset);
    }

    public static OdometryThread getInstance() {
        if (currentInstance == null) {
            currentInstance = new OdometryThread(0);
        }
        return currentInstance;
    }

    public void stopThread() {
        running = false;
    }

    public void startThread() {
        running = true;
        currentInstance.start();
    }

    private OdometryThread(double offset) {
        currentPosition = new OdometryPosition(offset, 0, 0, HeadingUnit.RADIANS);
        this.offset = offset;
        OdometryCore.initialize(1440, 1.5, 7.83, 7.83, 1);
    }

    public OdometryPosition getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void run() {
        while (running) {
            OdometryPosition rawPos = OdometryCore.getInstance().getCurrentPosition(new EncoderPositions(encoderLeft.getCurrentPosition(), encoderRight.getCurrentPosition(), encoderBack.getCurrentPosition()));
            currentPosition = new OdometryPosition(rawPos.getX() + offset, rawPos.getY(), rawPos.getHeadingRadians(), HeadingUnit.RADIANS);
        }
    }
}
