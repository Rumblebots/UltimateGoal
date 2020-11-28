package org.firstinspires.ftc.teamcode.testCode.fmTesting;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.MapApi;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.UltimateGoalMap;
import org._11253.lib.drives.ShifterMeccanum;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.utils.telem.Telemetry;

@TeleOp(name = "FmTest", group = "default")
public class FmTest extends ShifterMeccanum {
    private final double cpr = 360.000;
    private final double wheelDiameter = 2.500;
    private final double leftOffset = 7.830;
    private final double rightOffset = 7.830;
    private final double frontBackOffset = 5.414;

    private double rightPos = 0.0;
    private double leftPos = 0.0;
    private double frontBackPos = 0.0;

    // private String rightName = "frontRight";
    // private String leftName = "frontLeft";
    // private String frontBackName = "backRight";

    private Motor right;
    private Motor left;
    private Motor frontBack;

    private OdometrySystem odometry = new OdometrySystem(
            cpr,
            wheelDiameter,
            leftOffset,
            rightOffset,
            frontBackOffset
    );

    private Map map = new UltimateGoalMap();
    private MapApi mapApi = new MapApi(map);

    private void updateOdometry() {
        odometry.update(
                left.getCount(),
                right.getCount(),
                frontBack.getCount()
        );
    }

    public FmTest() {
        beforeStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        odometry.init();
                        right = Drivetrain.frontRight;
                        left = Drivetrain.frontLeft;
                        frontBack = Drivetrain.backRight;
                    }
                }
        );
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        mapApi.scheduleAsync(
                                odometry,
                                100
                        );
                    }
                }
        );
        onStartRun.add(
                new Runnable() {
                    @Override
                    public void run() {
                        updateOdometry();
                    }
                }
        );
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        Telemetry.addLine(
                                mapApi.getPositionsString()
                        );
                    }
                }
        );
    }
}
