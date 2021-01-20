package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.UltimateGoalMap;
import me.wobblyyyy.pathfinder_ftc.configs._3._2020_12_1;
import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelComplete;
import org._11253.lib.drives.ShifterMeccanum;

@TeleOp(name = "UG Meccanum Drive", group = "TeleOp")
public class Movement extends ShifterMeccanum {
    private final UltimateGoalMap map = new UltimateGoalMap();

    private final HeadingCoordinate<Double> target1 = new HeadingCoordinate<>(
            32.0,
            32.0,
            1.0
    );
    private final HeadingCoordinate<Double> target2 = new HeadingCoordinate<>(
            64.0,
            64.0,
            0.5
    );
    private final HeadingCoordinate<Double> target3 = new HeadingCoordinate<>(
            70.0,
            22.0,
            0.55
    );
    private final HeadingCoordinate<Double> target4 = new HeadingCoordinate<>(
            90.0,
            22.0,
            1.0
    );

    private final ThreeWheelComplete twc = new ThreeWheelComplete(
            "left",
            "right",
            "back",
            new _2020_12_1(),
            drivetrain,
            map
    );

    public Movement() {
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        twc.goToPosition(target1);
                    }
                }
        );
    }
}
