package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.core.Followers;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.util.RobotProfile;
import org._11253.lib.utils.telem.Telemetry;

import java.util.ArrayList;

/**
 * Test a meccanum drive's pathfinding abilities. Very poorly, actually,
 * because there's not a chance this will work. But hey! It's okay.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
@TeleOp(name = "Basic Pathfinder Test", group = "Test")
public class MoveForwards extends LinearOpMode {
    /**
     * Wheel diameter for encoder wheels.
     */
    private static final double diameter = 1.5;

    /**
     * Left offset, inches.
     */
    private static final double OFFSET_LEFT = 7.83;

    /**
     * Right offset, inches.
     */
    private static final double OFFSET_RIGHT = 7.83;

    /**
     * Center offset, inches.
     */
    private static final double OFFSET_CENTER = 1.0;

    /**
     * Encoder wheel counts per rotation.
     */
    private static final double CPR = 1440;

    private static final ArrayList<HeadingPoint> targets =
            new ArrayList<HeadingPoint>() {{
                add(new HeadingPoint(0, 10, 0));
            }};

    private PfMotor motorFr;
    private PfMotor motorFl;
    private PfMotor motorBr;
    private PfMotor motorBl;

    private PfEncoder encoderRight;
    private PfEncoder encoderLeft;
    private PfEncoder encoderCenter;

    private Field field;
    private Odometry odometry;
    private Drive drive;
    private Config config;

    private Pathfinder pathfinder;

    private boolean showPos = true;

    private void initComponents() {
        motorFr = new PfMotor("frontRight");
        motorFl = new PfMotor("frontLeft");
        motorBr = new PfMotor("backRight");
        motorBl = new PfMotor("backLeft");

        encoderRight = new PfEncoder(motorBr);
        encoderLeft = new PfEncoder(motorBl);
        encoderCenter = new PfEncoder(motorFr);

        field = new Field();
        odometry = new Odometry(
                encoderLeft,
                encoderRight,
                encoderCenter,
                diameter,
                OFFSET_LEFT,
                OFFSET_RIGHT,
                OFFSET_CENTER
        );
        drive = new Drive(motorFr, motorFl, motorBr, motorBl);
        config = new Config(
                odometry,
                144,
                144,
                2,
                18,
                18,
                18,
                18,
                new RobotProfile(0, 0, 0, 0, 0, 0),
                drive,
                field,
                Followers.LINEAR,
                true,
                true,
                true
        );

        pathfinder = new Pathfinder(config);
    }

    private void displayRobotPosition() {
        Telemetry.addData(
                "Robot Position",
                pathfinder.getPosition().toString()
        );
        Telemetry.printTelemetry();
    }

    private void enablePositionThread() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (showPos) {
                    displayRobotPosition();
                }
            }
        })).start();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initComponents();
        enablePositionThread();

        waitForStart();

        pathfinder.goToPosition(targets.get(0));

        showPos = true;
        pathfinder.lock();
        showPos = false;
    }
}
