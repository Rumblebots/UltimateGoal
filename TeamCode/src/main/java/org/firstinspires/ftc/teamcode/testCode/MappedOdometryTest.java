package org.firstinspires.ftc.teamcode.testCode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.odometry.fieldMapping.Map;
import org._11253.lib.odometry.fieldMapping.MapAPI;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.UltimateGoalMap;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheel;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheels;
import org._11253.lib.utils.telem.Telemetry;

@TeleOp(name = "Mapped Odometry Test", group = "default")
public class MappedOdometryTest extends ShifterMeccanum {
    ThreeWheels odometryWheels;
    ThreeWheel threeWheel;
    Map map = new UltimateGoalMap();
    MapAPI mapAPI = new MapAPI(map);

    public MappedOdometryTest() {
        beforeStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometryWheels = new ThreeWheels(TestOWP.positions);
                        odometryWheels.init();
                        threeWheel = new ThreeWheel(odometryWheels);
                    }
                }
        );
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        mapAPI.scheduleAsync(threeWheel, 125);
                    }
                }
        );
        onStartRun.add();
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        Telemetry.addData(
                                "C_POSE",
                                "Odometry Pose",
                                ": ",
                                threeWheel.currentPose.toString()
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
                                mapAPI.getPositionsString()
                        );
                    }
                }
        );
        onFinishRun.add();
        onFinish.add();
    }

    public void resetPosition() {
        threeWheel.currentPose = new Pose2d(0, 0, 0);
    }

    public Pose2d getPosition() {
        return threeWheel.getPoseEstimate();
    }
}
