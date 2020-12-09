package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import me.wobblyyyy.pathfinder.Pathfinder;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.UltimateGoalMap;
import me.wobblyyyy.pathfinder_ftc.configs._3._2020_12_1;
import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelComplete;
import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelConfig;
import org._11253.lib.drives.Meccanum;
import org._11253.lib.op.Template;

@TeleOp(name = "Pathfinding Test", group = "Test")
public class PathfindingTest extends Meccanum {
    public ThreeWheelComplete odometry;
    public Map map = new UltimateGoalMap();

    public PathfindingTest() {
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometry = new ThreeWheelComplete(
                                "frontLeft",
                                "backLeft",
                                "backRight",
                                new _2020_12_1(),
                                drivetrain,
                                map
                        );
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        odometry.pfInterface.pathfinder.goToPosition(
                                new HeadingCoordinate<Double>(
                                        30.0,
                                        30.0,
                                        0.0
                                )
                        );
                    }
                }
        );
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometry.pfInterface.setMotorPower();
                    }
                }
        );
    }
}
