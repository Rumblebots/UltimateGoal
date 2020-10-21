package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import org._11253.lib.robot.phys.components.Motor;
import org._11253.lib.Global;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A subystem containing all of the information we need
 * about the physical encoder components on the robot.
 *
 * <p>
 * This is used (primarily) in the ~/shared/Odometry.java file 
 * and contains all of the stuff we need for it. This is my first time
 * doing anything related to odometry, as well as my first time doing it
 * on a robot, so I can't promise much.
 * </p>
 * <p>
 * TODO
 *   Tejas, you can feel more than free to work on this yourself
 *   to some degree.
 * I truly don't know what the flippity-fuck is going on with any of this
 * code, and if you do, that would be pretty swaggy.
 * </p>
 */
public class OdometryWheels {
    public Motor left;
    public Motor right;
    public Motor back;

    private DcMotor _left;
    private DcMotor _right;
    private DcMotor _back;

    // I know - deep in my soul - that, as a fact, there WILL be a bit
    // of confusion over the names of the encoders. So, just to clarify,
    // and to make sure everybody understands, they're right here.
    // They shouldn't ever change, they should always stay like this.
    // No exceptions or whatever. This is the way they are. Cope.
    public String leftName = "encoder_left";
    public String rightName = "encoder_right";
    public String backName = "encoder_back";

    /**
     * A HashMap containing the position of each of the wheels.
     *
     * <p>
     * The position of each of these wheels is relative to the very
     * center of the robot. This needs to be adjusted after we get the
     * positioning of the wheels. I'm not entirely sure if these are 
     * supposed to be able to move, but let's really hope not. Anyways...
     * </p>
     * <p>
     * Don't change this until Brodie gives us some accurate positioning
     * information of the wheels. A couple more questions I have...
     * <ul>
     *  <li>1. Does heading matter?</li>
     *  <li>2. Can the wheels be free-spinning?</li>
     *  <li>3. Is this optimal positioning?</li>
     * </ul>
     * </p>
     */
    public static final HashMap<wheels, Pose2d> wheelPoses = new HashMap<wheels, Pose2d>() {{
        put(wheels.LEFT, new Pose2d(0, 0, 0));
        put(wheels.RIGHT, new Pose2d(0, 0, 0));
        put(wheels.BACK, new Pose2d(0, 0, 0));
    }};

    /**
     * A (very cool) constructor.
     *
     * <p>
     * I'm not entirely sure whether or not this will be needed in
     * the future for anything, but there's no harm in having it here,
     * now.
     * </p>
     */
    public OdometryWheels() {

    }

    /**
     * Initialize the three custom motors and the three DcMotors.
     * 
     * <p>
     * Much like the initialize process for every other physical
     * subsystem on the entire robot, you need to initialize it
     * before you can actually use it. For the most part, subsystems
     * are usually initialized during the beforeStart list.
     * </p>
     */
    public void init() {
      left = new Motor(leftName);
      right = new Motor(rightName);
      back = new Motor(backName);

      _left = left.getDcMotorComponent();
      _right = right.getDcMotorComponent();
      _back = back.getDcMotorComponent();
    }

    /**
     * Get, as an array, all of the positioning of our encoders.
     */
    public List<Pose2d> getMotorArray() {
        return Arrays.asList(
                wheelPoses.get(wheels.LEFT),
                wheelPoses.get(wheels.RIGHT),
                wheelPoses.get(wheels.BACK)
        );
    }

    /**
     * Get, as a map, all of our custom DcMotor "wrappers".
     */
    public HashMap<wheels, Motor> getMotorMap() {
        return new HashMap<wheels, Motor>() {{
            put(wheels.LEFT, left);
            put(wheels.RIGHT, right);
            put(wheels.BACK, back);
        }};
    }

    /**
     * Get, as a map, all of our original DcMotor components.
     */
    public HashMap<wheels, DcMotor> getDcMotorMap() {
        return new HashMap<wheels, DcMotor>() {{
            put(wheels.LEFT, _left);
            put(wheels.RIGHT, _right);
            put(wheels.BACK, _back);
        }};
    }

    /**
     * Our three different types/positions of wheels.
     *
     * <p>
     * These positions are all relative to the BACK of the robot. To 
     * figure out which wheel is which, stand behind the robot and look
     * at the RIGHT side, the LEFT side, and the BACK side. There will
     * undoubtedly be some confusion about this in the future, so this 
     * is here for reference, and quite possibly my own sanity.
     * </p>
     */
    public enum wheels {
        LEFT,
        RIGHT,
        BACK
    }
}
