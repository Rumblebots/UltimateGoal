package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Aux;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;

@TeleOp (name = "Modes Test", group = "default")
public class ModesTest extends LinearOpMode
{
    public enum RobotModes
    {
        GoingToIntakeBlock,
        IntakingBlock,
        LiftingBlock,
        AligningRobot,
        DroppingBlock,
        LoweringLifter,
    }
    public RobotModes RobotMode;
    public RobotModes nextMode (RobotModes currentMode)
    {
        switch (currentMode)
        {
            case GoingToIntakeBlock:
                return RobotModes.IntakingBlock;
            case IntakingBlock:
                return RobotModes.LiftingBlock;
            case LiftingBlock:
                return RobotModes.AligningRobot;
            case AligningRobot:
                return RobotModes.DroppingBlock;
            case DroppingBlock:
                return RobotModes.LoweringLifter;
            case LoweringLifter:
                return RobotModes.GoingToIntakeBlock;
            default:
                throw new IllegalArgumentException("Invalid robot mode");
        }
    }
    public class DriveMotors
    {
        public DcMotor frontRight,
                       frontLeft,
                       backRight,
                       backLeft;
        public void setDriveMotorsPower (DriveMotorPower power)
        {
            frontRight.setPower(power.frontRight);
            frontLeft.setPower(power.frontLeft);
            backRight.setPower(power.backRight);
            backLeft.setPower(power.backLeft);
        }
    }
    public class DriveMotorPower
    {
        public double frontRight,
                      frontLeft,
                      backRight,
                      backLeft;
    }
    public class SensorReadings
    {
        public double right, left, x;
    }
    public class IntakePower
    {
        public double dc1, dc2, s1, s2;
    }
    public DriveMotorPower align (SensorReadings sr)
    {
        DriveMotorPower mPO = new DriveMotorPower();
        return mPO;
    }
    public DriveMotorPower xalign (SensorReadings sr)
    {
        DriveMotorPower mPO = new DriveMotorPower();
        return mPO;
    }
    public DriveMotorPower controller (Gamepad gamepad)
    {
        DriveMotorPower mPO = new DriveMotorPower();
        return mPO;
    }
    public IntakePower intakeBlock ()
    {
        IntakePower mPO = new IntakePower();
        return mPO;
    }

    public Sensors sensors = new Sensors();
    public Motors_Drive motorsDrive = new Motors_Drive();
    public DriveMotors driveMotors = new DriveMotors();
    public Motors_Aux motorsAux = new Motors_Aux();

    public void runOpMode ()
    {
        waitForStart();
        sensors.init(hardwareMap);
        motorsDrive.init(hardwareMap);
        driveMotors.frontRight = Motors_Drive.FrontRight;
        driveMotors.frontLeft = Motors_Drive.FrontLeft;
        driveMotors.backRight = Motors_Drive.BackRight;
        driveMotors.backLeft = Motors_Drive.BackLeft;
        motorsAux.init(hardwareMap);
        RobotMode = RobotModes.GoingToIntakeBlock;
        while (opModeIsActive())
        {
            switch (RobotMode)
            {
                case GoingToIntakeBlock:
                    break;
                case IntakingBlock:
                    break;
                case LiftingBlock:
                    break;
                case AligningRobot:
                    break;
                case DroppingBlock:
                    break;
                case LoweringLifter:
                    break;
            }
            if (RobotMode != RobotModes.AligningRobot)
            {
                driveMotors.setDriveMotorsPower(controller(gamepad1));
            }
        }
    }
}
