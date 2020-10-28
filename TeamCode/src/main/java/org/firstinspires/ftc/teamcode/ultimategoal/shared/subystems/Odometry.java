package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.MapAPI;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.UltimateGoalMap;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheel;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheels;
import org._11253.lib.op.Template;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.OdometryWheelPositions;

import java.util.HashMap;

public class Odometry {
    private final ThreeWheels wheels;
    private final ThreeWheel odometry;
    private final MapAPI api;
    private final Template template;
    private final HashMap<ThreeWheels.wheels, Pose2d> wheelPositions = OdometryWheelPositions.positions;
    private final boolean isMapped;

    public Odometry(Template template, boolean isMapped) {
        wheels = new ThreeWheels(wheelPositions);
        odometry = new ThreeWheel(wheels);
        api = new MapAPI(new UltimateGoalMap());
        this.template = template;
        this.isMapped = isMapped;
        template.onStartRun.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometry.update();
                    }
                }
        );
    }

    public void init() {
        wheels.init();
        if (isMapped) {
            api.scheduleAsync(odometry, 100);
        }
    }

    public Pose2d getPose() {
        return odometry.getPoseEstimate();
    }

    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }
}
