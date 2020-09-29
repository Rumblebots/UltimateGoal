package org.firstinspires.ftc.teamcode.skystone.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.skystone.RobotCore.Utils;
import org.firstinspires.ftc.teamcode.skystone.RobotCore.Utils.Toggle;
import org.firstinspires.ftc.teamcode.skystone.RobotCore.Utils.Shifter;

import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Aux.TeleOpEncoders;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Servos;
import org.firstinspires.ftc.teamcode.skystone.hardware.v2.Sensors.Sensors;

import java.util.HashMap;
import java.util.Map;

import static org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive.BackLeft;
import static org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive.BackRight;
import static org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive.FrontLeft;
import static org.firstinspires.ftc.teamcode.skystone.hardware.v2.Motors_Drive.FrontRight;

@Disabled

@TeleOp(name="Distance Sensor Test", group="default")
public class DistanceSensorTest extends LinearOpMode
{
    private Motors_Drive motorsDrive = new Motors_Drive();
    private Motors_Aux motorsAux = new Motors_Aux();
    private Motors_Aux.TeleOp motorsAuxTeleOp = motorsAux.new TeleOp();
    private TeleOpEncoders motorsAuxEncoders = motorsAux.new TeleOpEncoders();
    public Servos servos = new Servos();
    public Servos.TeleOp servosTeleOp = servos.new TeleOp();
    public Sensors sensors = new Sensors();
    public Utils utils = new Utils();
    private int lifterMode = 0, cpos, rpos;
    private Toggle alignArm = utils.new Toggle(false);
    private Toggle grabber = utils.new Toggle(false);
    private Toggle foundation = utils.new Toggle(false);
    private Toggle capstone = utils.new Toggle(false);
    private TouchSensor extenderlimit;
    private Shifter lifter = utils.new Shifter(1, 6);
    // first gear - 1 block - no previous
    // second gear - 2 blocks - 1 previous
    // third gear - 3 blocks - 2 previous
    // fourth gear - 4 blocks - 3 previous
    // fifth gear - 5 blocks - 4 previous
    // sixth gear - 6 blocks - 5 previous
    private Map<Integer, Integer> levels = new HashMap<Integer, Integer>()
    {{
       put (1, 0);
       put (2, 450);
       put (3, 1180);
       put (4, 1754);
       put (5, 2417);
       put (6, 3181);
    }};
    private Motors_Drive.motorPowerObject alignment (Sensors.Differential differential, double RD, double LD, double AD)
    {
        // RD -= 1;
        // LD -=  6;
        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
        switch (differential)
        {
            case Right:
                mPO.frontRight = 0;
                mPO.backRight = 0;
                mPO.frontLeft = 0.4;
                mPO.backLeft = 0.4;
                break;
            case Left:
                mPO.frontRight = 0.4;
                mPO.backRight = 0.4;
                mPO.frontLeft = 0;
                mPO.backLeft = 0;
                break;
        }
        if (RD > 10 && LD > 10)
        {
            mPO.frontRight = 0.4;
            mPO.backRight = 0.4;
            mPO.frontLeft = 0.4;
            mPO.backLeft = 0.4;
        }
        else if (RD - 1.5 <= LD && LD <= RD + 1.5)
        {
            if (2 - 1.5 <= RD && RD <= 2 + 1.5)
            {
                if (2 - 1.5 <= LD && LD <= 2 + 1.5)
                {

                }
                else
                {
                    // we need to turn left
                    // L side towards 1/2pi
                    mPO.frontRight = 0.4;
                    mPO.backRight = 0.4;
                    mPO.frontLeft = 0;
                    mPO.backLeft = 0;
                }
            }
            else
            {
                // we need to turn right
                // R side towards 1/2pi
                mPO.frontRight = 0;
                mPO.backRight = 0;
                mPO.frontLeft = 0.4;
                mPO.backLeft = 0.4;
            }
        }
        return mPO;
    }
    public Motors_Drive.motorPowerObject xalignment (double AD)
    {
        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
        if (8 - 0.5 <= AD && AD <= 8 + 0.5)
        {
            mPO.frontRight = 0;
            mPO.backRight = 0;
            mPO.frontLeft = 0;
            mPO.backLeft = 0;
        }
        else if (AD < 8 - 2)
        {
            // Strafe right
            mPO.frontLeft = 0.45;
            mPO.backRight = 0.45;
            mPO.frontRight = -0.45;
            mPO.backLeft = -0.45;
        }
        else if (AD > 8 + 2)
        {
            // Strafe left
            mPO.frontLeft = -0.45;
            mPO.backRight = -0.45;
            mPO.frontRight = 0.45;
            mPO.backLeft = 0.45;
        }
        else if (AD < 8 - 1)
        {
            // Strafe right
            mPO.frontLeft = 0.4;
            mPO.backRight = 0.4;
            mPO.frontRight = -0.4;
            mPO.backLeft = -0.4;
        }
        else if (AD > 8 + 1)
        {
            // Strafe left
            mPO.frontLeft = -0.4;
            mPO.backRight = -0.4;
            mPO.frontRight = 0.4;
            mPO.backLeft = 0.4;
        }
        else if (AD < 8 - 0.5)
        {
            // Strafe right
            mPO.frontLeft = 0.35;
            mPO.backRight = 0.35;
            mPO.frontRight = -0.35;
            mPO.backLeft = -0.35;
        }
        else if (AD > 8 + 0.5)
        {
            // Strafe left
            mPO.frontLeft = -0.35;
            mPO.backRight = -0.35;
            mPO.frontRight = 0.35;
            mPO.backLeft = 0.35;
        }
        return mPO;
    }
    public Motors_Drive.motorPowerObject meccanum (Gamepad gamepad)
    {
        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
        mPO.frontRight = -gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x;
        mPO.frontLeft = (gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x) * -1;
        mPO.backRight = -gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x;
        mPO.backLeft = (gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x) * -1;
        return mPO;
    }
    public void runOpMode ()
    {
        /*
         * Pre - init functionality
         */
        extenderlimit = hardwareMap.touchSensor.get("extenderlimit");
        waitForStart();
        /*
         * Post - init functionality
         * Yay sensors!!
         */
        telemetry.addLine("waiting for start");
        sensors.init(hardwareMap);
        servos.init(hardwareMap);
        motorsDrive.init(hardwareMap);
        motorsAux.init(hardwareMap);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        while (opModeIsActive())
        {
            Motors_Drive.motorPowerObject mPOfX = motorsDrive.new motorPowerObject();
            Motors_Drive.motorPowerObject mPOfA = motorsDrive.new motorPowerObject();
            Motors_Drive.motorPowerObject mPOfG = motorsDrive.new motorPowerObject();
            double motorSpeedDiv = 2;
            if (gamepad1.right_trigger > 0) motorSpeedDiv = gamepad1.right_trigger * 3 / 4;
            if (gamepad1.a) mPOfA = alignment(sensors.getDifferential(), sensors.distanceSensorRight.getDistanceCm(), sensors.distanceSensorLeft.getDistanceCm(), sensors.alignDistance.getDistanceCm());
            if (!gamepad1.a && !gamepad1.b) mPOfG = meccanum(gamepad1);
            if (gamepad1.b) mPOfX = xalignment(sensors.alignDistance.getDistanceCm());
            if (!gamepad1.b && !gamepad1.a)
            {
                FrontRight.setPower(mPOfG.frontRight / motorSpeedDiv);
                FrontLeft.setPower(mPOfG.frontLeft / motorSpeedDiv);
                BackRight.setPower(mPOfG.backRight / motorSpeedDiv);
                BackLeft.setPower(mPOfG.backLeft / motorSpeedDiv);
            }
            if (gamepad1.b)
            {
                FrontRight.setPower(mPOfX.frontRight);
                FrontLeft.setPower(mPOfX.frontLeft);
                BackRight.setPower(mPOfX.backRight);
                BackLeft.setPower(mPOfX.backLeft);
            }
            else if (gamepad1.a)
            {
                FrontRight.setPower(mPOfA.frontRight);
                FrontLeft.setPower(mPOfA.frontLeft);
                BackRight.setPower(mPOfA.backRight);
                BackLeft.setPower(mPOfA.backLeft);
            }
            // gamepad 1 alignment arm
            if (gamepad1.x) alignArm.UpdateToggle("ON");
            else alignArm.UpdateToggle("OFF");
            if (alignArm.Toggle()) servos.alignmentArm.setPosition(0.45);
            else servos.alignmentArm.setPosition(1);
            // gamepad 2 grabber
            if (gamepad2.a) grabber.UpdateToggle("ON");
            else grabber.UpdateToggle("OFF");
            if (grabber.Toggle()) servos.gripperServo.setPosition(0);
            else servos.gripperServo.setPosition(1);
            // gamepad 2 foundation hook
            if (gamepad2.x) foundation.UpdateToggle("ON");
            else foundation.UpdateToggle("OFF");
            if (foundation.Toggle()) servos.foundationServo1.setPosition(1);
            else servos.foundationServo1.setPosition(0);
            // lifter stuff
            if (gamepad2.dpad_down)
            {
                lifter.CurrentGear = 1;
                lifterMode = 1;
            }
            else if (gamepad2.dpad_up)
            {
                lifter.Shift(Utils.Shift.Up);
                motorsAuxEncoders.SetTargetPos(levels.get(lifter.CurrentGear));
            }
            else lifter.Released();
            if (gamepad2.right_trigger > 0 && !gamepad2.y)
            {
                motorsAuxTeleOp.SetStackerDirection(-gamepad2.right_trigger);
                lifterMode = 0;
            }
            if (gamepad2.left_trigger > 0 && !gamepad2.y)
            {
                if (!sensors.lifterTouchSensor.IsSensorActive()) motorsAuxTeleOp.SetStackerDirection(gamepad2.left_trigger);
                else
                {
                    motorsAuxTeleOp.SetStackerDirection(0);
                    lifterMode = 0;
                }
            }
            if (lifterMode == 0) motorsAuxTeleOp.SetStackerDirection(1.0);
            else if (!gamepad2.y) motorsAuxTeleOp.SetStackerDirection(0);
            if (sensors.lifterTouchSensor.IsSensorActive())
            {
                cpos = 0;
                rpos = motorsAuxEncoders.GetCurrentPos();
            }
            else cpos = motorsAuxEncoders.GetCurrentPos() - rpos;
            if (gamepad2.y) motorsAuxEncoders.AdjustMotorPower(cpos);
            if (gamepad2.b) capstone.UpdateToggle("ON");
            else capstone.UpdateToggle("OFF");
            if (capstone.Toggle()) servos.capstoneRelease.setPosition(0);
            else servos.capstoneRelease.setPosition(1);
            if (gamepad2.right_bumper)
            {
                motorsAuxTeleOp.SetIntakeDirection(1.0);
                servosTeleOp.SetIntakeCRServosDirection(1.0);
            }
            else if (gamepad2.left_bumper)
            {
                motorsAuxTeleOp.SetIntakeDirection(-1.0);
                servosTeleOp.SetIntakeCRServosDirection(-1.0);
            }
            else
            {
                motorsAuxTeleOp.SetIntakeDirection(0);
                servosTeleOp.SetIntakeCRServosDirection(0);
            }
            if (sensors.getInside1SensorVals().get("red") >= 36)
            {
                servos.blockWheel.setPower(0.0);
                grabber.OverrideSetToggle("OFF");
            }
            if (sensors.getInside2SensorVals().get("red") >= 150)
            {
                servos.blockWheel.setPower(0.8);
                grabber.OverrideSetToggle("ON");
            }
            if (extenderlimit.isPressed())
            {
                if (gamepad2.dpad_right)
                {
                    servos.extenderServo.setPower(-0.85);
                }
                else if (gamepad2.dpad_left)
                {
                    servos.extenderServo.setPower(0.85);
                }
                else
                {
                    servos.extenderServo.setPower(0.0);
                }
            }
        }
    }
}
