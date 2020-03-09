//package org.firstinspires.ftc.teamcode.teleop;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import org.firstinspires.ftc.teamcode.RobotCore.Utils;
//import org.firstinspires.ftc.teamcode.RobotCore.Utils.Toggle;
//
//import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
//import org.firstinspires.ftc.teamcode.hardware.v2.Servos;
//import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;
//
//import static org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.BackLeft;
//import static org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.BackRight;
//import static org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.FrontLeft;
//import static org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive.FrontRight;
//
//@TeleOp(name="Distance Sensor Test", group="default")
//public class DistanceSensorTest extends LinearOpMode
//{
//    public Motors_Drive motorsDrive = new Motors_Drive();
//    public Servos servos = new Servos();
//    public Sensors sensors = new Sensors();
//    public Utils utils = new Utils();
//    public Toggle alignArm = utils.new Toggle(false);
//    public Toggle grabber = utils.new Toggle(false);
//    public Motors_Drive.motorPowerObject alignment (Sensors.Differential differential, double RD, double LD, double AD)
//    {
//        // RD -= 1;
//        // LD -=  6;
//        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
//        switch (differential)
//        {
//            case Right:
//                mPO.frontRight = 0;
//                mPO.backRight = 0;
//                mPO.frontLeft = 0.4;
//                mPO.backLeft = 0.4;
//                break;
//            case Left:
//                mPO.frontRight = 0.4;
//                mPO.backRight = 0.4;
//                mPO.frontLeft = 0;
//                mPO.backLeft = 0;
//                break;
//        }
//        if (RD > 10 && LD > 10)
//        {
//            mPO.frontRight = 0.4;
//            mPO.backRight = 0.4;
//            mPO.frontLeft = 0.4;
//            mPO.backLeft = 0.4;
//        }
//        else if (RD - 1.5 <= LD && LD <= RD + 1.5)
//        {
//            if (2 - 1.5 <= RD && RD <= 2 + 1.5)
//            {
//                if (2 - 1.5 <= LD && LD <= 2 + 1.5)
//                {
//
//                }
//                else
//                {
//                    // we need to turn left
//                    // L side towards 1/2pi
//                    mPO.frontRight = 0.4;
//                    mPO.backRight = 0.4;
//                    mPO.frontLeft = 0;
//                    mPO.backLeft = 0;
//                }
//            }
//            else
//            {
//                // we need to turn right
//                // R side towards 1/2pi
//                mPO.frontRight = 0;
//                mPO.backRight = 0;
//                mPO.frontLeft = 0.4;
//                mPO.backLeft = 0.4;
//            }
//        }
//        return mPO;
//    }
//    public Motors_Drive.motorPowerObject xalignment (double AD)
//    {
//        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
//        if (8 - 0.5 <= AD && AD <= 8 + 0.5)
//        {
//            mPO.frontRight = 0;
//            mPO.backRight = 0;
//            mPO.frontLeft = 0;
//            mPO.backLeft = 0;
//        }
//        else if (AD < 8 - 2)
//        {
//            // Strafe right
//            mPO.frontLeft = 0.45;
//            mPO.backRight = 0.45;
//            mPO.frontRight = -0.45;
//            mPO.backLeft = -0.45;
//        }
//        else if (AD > 8 + 2)
//        {
//            // Strafe left
//            mPO.frontLeft = -0.45;
//            mPO.backRight = -0.45;
//            mPO.frontRight = 0.45;
//            mPO.backLeft = 0.45;
//        }
//        else if (AD < 8 - 1)
//        {
//            // Strafe right
//            mPO.frontLeft = 0.4;
//            mPO.backRight = 0.4;
//            mPO.frontRight = -0.4;
//            mPO.backLeft = -0.4;
//        }
//        else if (AD > 8 + 1)
//        {
//            // Strafe left
//            mPO.frontLeft = -0.4;
//            mPO.backRight = -0.4;
//            mPO.frontRight = 0.4;
//            mPO.backLeft = 0.4;
//        }
//        else if (AD < 8 - 0.5)
//        {
//            // Strafe right
//            mPO.frontLeft = 0.35;
//            mPO.backRight = 0.35;
//            mPO.frontRight = -0.35;
//            mPO.backLeft = -0.35;
//        }
//        else if (AD > 8 + 0.5)
//        {
//            // Strafe left
//            mPO.frontLeft = -0.35;
//            mPO.backRight = -0.35;
//            mPO.frontRight = 0.35;
//            mPO.backLeft = 0.35;
//        }
//        return mPO;
//    }
//    public Motors_Drive.motorPowerObject meccanum (Gamepad gamepad)
//    {
//        Motors_Drive.motorPowerObject mPO = motorsDrive.new motorPowerObject();
//        mPO.frontRight = -gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x;
//        mPO.frontLeft = (gamepad.left_stick_y + gamepad.left_stick_x + gamepad.right_stick_x) * -1;
//        mPO.backRight = -gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x;
//        mPO.backLeft = (gamepad.left_stick_y - gamepad.left_stick_x + gamepad.right_stick_x) * -1;
//        return mPO;
//    }
//    public void runOpMode ()
//    {
//        /*
//         * Pre - init functionality
//         */
//        waitForStart();
//        /*
//         * Post - init functionality
//         * Yay sensors!!
//         */
//        telemetry.addLine("waiting for start");
//        sensors.init(hardwareMap);
//        servos.init(hardwareMap);
//        motorsDrive.init(hardwareMap);
//        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        while (opModeIsActive())
//        {
//            Motors_Drive.motorPowerObject mPOfX = motorsDrive.new motorPowerObject();
//            Motors_Drive.motorPowerObject mPOfA = motorsDrive.new motorPowerObject();
//            Motors_Drive.motorPowerObject mPOfG = motorsDrive.new motorPowerObject();
//            if (gamepad1.a) mPOfA = alignment(sensors.getDifferential(), sensors.distanceSensorRight.getDistanceCm(), sensors.distanceSensorLeft.getDistanceCm(), sensors.alignDistance.getDistanceCm());
//            if (!gamepad1.a && !gamepad1.b) mPOfG = meccanum(gamepad1);
//            if (gamepad1.b) mPOfX = xalignment(sensors.alignDistance.getDistanceCm());
//            if (!gamepad1.b && !gamepad1.a)
//            {
//                FrontRight.setPower(mPOfG.frontRight / 2);
//                FrontLeft.setPower(mPOfG.frontLeft / 2);
//                BackRight.setPower(mPOfG.backRight / 2);
//                BackLeft.setPower(mPOfG.backLeft / 2);
//            }
//            if (gamepad1.b)
//            {
//                FrontRight.setPower(mPOfX.frontRight);
//                FrontLeft.setPower(mPOfX.frontLeft);
//                BackRight.setPower(mPOfX.backRight);
//                BackLeft.setPower(mPOfX.backLeft);
//            }
//            else if (gamepad1.a)
//            {
//                FrontRight.setPower(mPOfA.frontRight);
//                FrontLeft.setPower(mPOfA.frontLeft);
//                BackRight.setPower(mPOfA.backRight);
//                BackLeft.setPower(mPOfA.backLeft);
//            }
//            else
//            {
////                FrontRight.setPower(0);
////                FrontLeft.setPower(0);
////                BackRight.setPower(0);
////                BackLeft.setPower(0);
//            }
//            if (gamepad1.x) alignArm.UpdateToggle("ON");
//            else alignArm.UpdateToggle("OFF");
//            if (alignArm.Toggle()) servos.alignmentArm.setPosition(0.45);
//            else servos.alignmentArm.setPosition(1);
//            if (gamepad1.y) grabber.UpdateToggle("ON");
//            else grabber.UpdateToggle("OFF");
//            if (grabber.Toggle()) servos.gripperServo.setPosition(0);
//            else servos.gripperServo.setPosition(1);
//            telemetry.addData("frontRight", mPOfA.frontRight + ", " + mPOfG.frontRight);
//            telemetry.addData("frontLeft", mPOfA.frontLeft + ", " + mPOfG.frontLeft);
//            telemetry.addData("backRight", mPOfA.backRight + ", " + mPOfG.backRight);
//            telemetry.addData("backLeft", mPOfA.backLeft + ", " + mPOfG.backLeft);
//            telemetry.addData("Right Distance Sensor", sensors.distanceSensorRight.getDistanceCm());
//            telemetry.addData("Left Distance Sensor", sensors.distanceSensorLeft.getDistanceCm());
//            telemetry.addData("Align Arm Distance Sensor", sensors.alignDistance.getDistanceCm());
//            telemetry.update();
//        }
//    }
//}
