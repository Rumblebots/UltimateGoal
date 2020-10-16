package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import org._11253.lib.robot.phys.components.Motor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OdometryWheels {
    public Motor left;
    public Motor right;
    public Motor back;

    private DcMotor _left;
    private DcMotor _right;
    private DcMotor _back;

    public String leftName = "encoder_left";
    public String rightName = "encoder_right";
    public String backName = "encoder_back";

    public static final HashMap<wheels, Pose2d> wheelPoses = new HashMap<>() {{
        put(wheels.LEFT, new Pose2d(0, 0, 0));
        put(wheels.RIGHT, new Pose2d(0, 0, 0));
        put(wheels.BACK, new Pose2d(0, 0, 0));
    }};

    public OdometryWheels() {

    }

    public List<Pose2d> getMotorArray() {
        return Arrays.asList(
                wheelPoses.get(wheels.LEFT),
                wheelPoses.get(wheels.RIGHT),
                wheelPoses.get(wheels.BACK)
        );
    }

    public HashMap<wheels, Motor> getMotorMap() {
        return new HashMap<>() {{
            put(wheels.LEFT, left);
            put(wheels.RIGHT, right);
            put(wheels.BACK, back);
        }};
    }

    public HashMap<wheels, DcMotor> getDcMotorMap() {
        return new HashMap<>() {{
            put(wheels.LEFT, _left);
            put(wheels.RIGHT, _right);
            put(wheels.BACK, _back);
        }};
    }

    public enum wheels {
        LEFT,
        RIGHT,
        BACK
    }
}
