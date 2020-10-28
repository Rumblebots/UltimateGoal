package org.firstinspires.ftc.teamcode.testCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.utils.telem.Telemetry;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Odometry;

@TeleOp(name = "Mapped Odometry Test", group = "default")
public class MappedOdometryTest extends ShifterMeccanum {
    Odometry odometry;

    public MappedOdometryTest() {
        beforeStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometry = new Odometry(MappedOdometryTest.this, true);
                    }
                }
        );
        onStart.add();
        onStartRun.add();
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        Telemetry.addData(
                                "C_POSE",
                                "Odometry Pose",
                                ": ",
                                odometry.getPose().toString()
                        );
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        Telemetry.addData(
                                "C_POSITIONS",
                                "Positions",
                                ": ",
                                odometry.getApi().getPositionsString()
                        );
                    }
                }
        );
        onFinishRun.add();
        onFinish.add();
    }

    public void resetPosition() {
        odometry.getOdometry().currentPose = new Pose2d(0, 0, 0);
    }

    public Pose2d getPosition() {
        return odometry.getOdometry().getPoseEstimate();
    }
}
