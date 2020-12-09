/****
 * Made by Tejas Mehta
 * Made on Thursday, October 15, 2020
 * File Name: AutoRed
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.auton*/
package org.firstinspires.ftc.teamcode.ultimategoal.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.tejasmehta.OdometryCore.OdometryCore;
import com.tejasmehta.OdometryCore.localization.EncoderPositions;
import com.tejasmehta.OdometryCore.localization.HeadingUnit;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import org._11253.lib.op.Auton;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.robot.phys.assm.drivetrain.MotionType;
import org._11253.lib.robot.phys.components.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.OdometryThread;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Shooter;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.Storage;

@Autonomous(name = "Main Auo", group = "Auton")

public class GenericAuto extends LinearOpMode {

//    public Drivetrain drivetrain = new Drivetrain();
//    public Shooter shooter = new Shooter(new Storage(), 0, 0, 0, 0, 0, 0);

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor flywheel1;
    DcMotor flywheel2;
    DcMotor intake;
    CRServo intakeServo;
    Servo loader;
    Servo pusher;

//    public GenericAuto(final double offsetPos) {
//        onFinish.add(new Runnable() {
//            @Override
//            public void run() {
//                OdometryThread.getInstance().stopThread();
//            }
//        });
//    }

    public OdometryPosition getCurrentPos() {
        OdometryPosition currentPosition = OdometryThread.getInstance().getCurrentPosition();
        System.out.println(currentPosition);
        telemetry.addData("Odometry Pos: ", "x: " + currentPosition.getX() + ", y: " + currentPosition.getY() + ", heading: " + currentPosition.getHeadingDegrees());
        telemetry.update();
        return OdometryThread.getInstance().getCurrentPosition();
    }

    public void odometryMove(double x, double y) {
        if (y + 2 > getCurrentPos().getY()) {
            while (y > getCurrentPos().getY()) {
                meccanumDrive(MotionType.FORWARD, 0.3);
            }
        } else if (y - 2 < getCurrentPos().getY()){
            System.out.println("Y BACK");
            while (y < getCurrentPos().getY()) {
                System.out.println("Y POS" + getCurrentPos().getY());
                meccanumDrive(MotionType.BACKWARD, 0.5);
            }
        }

        if (x + 2 > getCurrentPos().getX()) {
            System.out.println("X VAL R");
            while (x > getCurrentPos().getX()) {
                meccanumDrive(MotionType.RIGHT, 0.25);
            }
        } else if (x - 2 < getCurrentPos().getX()){
            while (x < getCurrentPos().getX()) {
                meccanumDrive(MotionType.LEFT, 0.5);
            }
        }

        setPower(0, 0, 0, 0);
    }

    public void odometryTurnMove(double x, double y) {
        double radHeading = Math.atan2(y-getCurrentPos().getY(), x-getCurrentPos().getX());
        odometryTurn(Math.toDegrees(radHeading), true);
        while (getCurrentPos().getX() < x) {
            meccanumDrive(MotionType.FORWARD, 0.5);
        }
        setPower(0, 0, 0, 0);
    }

    public void odometryTurn(double x, double y) {
        double radHeading = Math.atan2(Math.abs(x-getCurrentPos().getX()), Math.abs(y-getCurrentPos().getY())); // y:  114, x: 1.5
        System.out.println("RAD HEADING: " + radHeading);
        System.out.println("DEG HEADING: " + Math.toDegrees(radHeading));
        if (x > getCurrentPos().getX()) radHeading *= -1;
        odometryTurn(Math.toDegrees(radHeading), false);
    }

    public void odometryTurn(double headingDegrees, boolean isAbsolute) {
        double targetDegrees = !isAbsolute ? getCurrentPos().getHeadingDegrees() + headingDegrees : headingDegrees;
        if (targetDegrees < 0) {
            while (getCurrentPos().getHeadingDegrees() > targetDegrees) {
                meccanumDrive(MotionType.TURN_CW, 0.25);
            }
        } else if (targetDegrees > 0) {
            while (getCurrentPos().getHeadingDegrees() < targetDegrees) {
                meccanumDrive(MotionType.TURN_CCW, 0.25);
            }
        } else {
            if (getCurrentPos().getHeadingDegrees() > 0) {
                while (getCurrentPos().getHeadingDegrees() > 0) {
                    meccanumDrive(MotionType.TURN_CW, 0.25);
                }
            } else if (getCurrentPos().getHeadingDegrees() < 0) {
                while (getCurrentPos().getHeadingDegrees() < 0) {
                    meccanumDrive(MotionType.TURN_CCW, 0.25);
                }
            }
        }
        setPower(0, 0, 0, 0);
    }

    public void shoot() {
        loader.setPosition(26.0/180);
        sleep(500);
        pusher.setPosition(0);
        sleep(1000);
        pusher.setPosition(0.5);
        sleep(500);
    }

    public void meccanumDrive(MotionType type, double power) {
        switch (type) {
            case FORWARD:
                setPower(-power, power, -power, power);
                break;
            case BACKWARD:
                setPower(power, -power, power, -power);
                break;
            case LEFT:
                setPower(power, -power, -power, power);
                break;
            case RIGHT:
                setPower(-power, power, power, -power);
                break;
            case TURN_CW:
                setPower(power, power, power, power);
                break;
            case TURN_CCW:
                setPower(-power, -power, -power, -power);
                break;
        }
    }

    public void setPower(double fr, double fl, double br, double bl) {
        frontRight.setPower(fr);
        frontLeft.setPower(fl);
        backRight.setPower(br);
        backLeft.setPower(bl);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel2");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
        OdometryThread.initialize(42, frontLeft, backLeft, backRight);
        System.out.println("HERE");
        getCurrentPos();
        waitForStart();
        odometryMove(getCurrentPos().getX(), getCurrentPos().getY() + 18);
//        odometryTurn(45, 126);
        flywheel1.setPower(1.0);
        flywheel2.setPower(1.0);
        sleep(500);
        shoot();
        sleep(500);
//        odometryMove(49, 14);
        odometryTurn(44, 126);
        shoot();
        sleep(500);
//        odometryMove(65, 12);
        odometryTurn(44, 126);
        shoot();
        sleep(500);
        flywheel1.setPower(0.0);
        flywheel2.setPower(0.0);
        odometryMove(getCurrentPos().getX(), 60);
        OdometryThread.getInstance().stopThread();
    }
}
