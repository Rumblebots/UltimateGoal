package org.firstinspires.ftc.teamcode.ultimategoal.shared;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.threeWheelOdometry.ThreeWheels;

import java.util.HashMap;

public class OdometryWheelPositions {
    public static HashMap<ThreeWheels.wheels, Pose2d> positions = new HashMap<ThreeWheels.wheels, Pose2d>() {{
        put(
                ThreeWheels.wheels.LEFT,
                new Pose2d(
                        -1.25,
                        7.83,
                        0
                )
        );
        put(
                ThreeWheels.wheels.RIGHT,
                new Pose2d(
                        -1.25,
                        -7.83,
                        0
                )
        );
        put(
                ThreeWheels.wheels.BACK,
                new Pose2d(
                        -5.414,
                        0.875,
                        90
                )
        );
    }};
}
