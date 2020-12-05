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
import org._11253.lib.utils.telem.Telemetry;

public class OdometryThread extends Thread{

    private final DcMotor encoderLeft = Drivetrain.frontLeft.getDcMotorComponent();
    private final DcMotor encoderRight = Drivetrain.frontRight.getDcMotorComponent();
    private final DcMotor encoderBack = Drivetrain.backLeft.getDcMotorComponent();
    private final double offset;
    private OdometryPosition currentPosition;
    private boolean running = true;
    private static OdometryThread currentInstance;

    public static void initialize(double offset) {
        System.out.println("INITING");
        currentInstance = new OdometryThread(offset);
        currentInstance.startThread();
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
        System.out.println("STARTED");
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
        System.out.println("STARTING");
        while (running) {
            OdometryPosition rawPos = OdometryCore.getInstance().getCurrentPosition(new EncoderPositions(encoderLeft.getCurrentPosition(), encoderRight.getCurrentPosition(), encoderBack.getCurrentPosition()));
            currentPosition = new OdometryPosition(rawPos.getX() + offset, rawPos.getY(), rawPos.getHeadingRadians(), HeadingUnit.RADIANS);
            Telemetry.addData("ODOM_POS", "Odometry Pos", ":", "x: " + currentPosition.getX() + ", y: " + currentPosition.getY() + ", heading: " + currentPosition.getHeadingDegrees());
        }
    }
}
