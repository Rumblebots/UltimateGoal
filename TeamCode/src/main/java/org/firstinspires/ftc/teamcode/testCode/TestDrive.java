/****
 * Made by Tejas Mehta
 * Made on Saturday, December 05, 2020
 * File Name: TestDrive
 * Package: org.firstinspires.ftc.teamcode.testCode*/
package org.firstinspires.ftc.teamcode.testCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import org._11253.lib.utils.gen.Toggle;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.ShooterThread;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems.OdometryThread;

@TeleOp(name = "Actual Meccanum", group = "Test")
public class TestDrive extends OpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor flywheel1;
    DcMotor flywheel2;
    DcMotor intake;
    CRServo intakeServo;
    CRServo upperIntakeServo;
    Servo intakeMover;
    Servo loader;
    Servo pusher;
    double multiplier = 0.5;
    Toggle t = new Toggle();
    Toggle loadToggle = new Toggle();
    Toggle pushToggle = new Toggle();
    ShooterThread shooterThread;
    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        flywheel1 = hardwareMap.get(DcMotor.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotor.class, "flywheel2");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        upperIntakeServo = hardwareMap.get(CRServo.class, "upperIntakeServo");
        intakeMover = hardwareMap.get(Servo.class, "intakeMover");
        loader = hardwareMap.get(Servo.class, "loader");
        pusher = hardwareMap.get(Servo.class, "pusher");
        flywheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        t.state = false;
        loadToggle.state = false;
        pushToggle.state = false;
        intakeMover.setPosition(0.33);
        shooterThread = new ShooterThread(flywheel2);
        shooterThread.start();
        try {
            OdometryThread.getInstance();
        } catch (Exception e) {
            OdometryThread.initialize(42, backLeft, backRight, frontRight);
        }
    }

    @Override
    public void loop() {
        if (gamepad1.left_trigger != 0 && gamepad1.right_trigger == 0) {
            multiplier = 0.25;
        } else {
            multiplier = 0.5;
        }

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger != 0) {
            multiplier = 1;
        } else {
            multiplier = 0.5;
        }

        double fr = -gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
        double br = -gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        double fl = gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        double bl = gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
        frontRight.setPower(fr * multiplier);
        backRight.setPower(br * multiplier);
        frontLeft.setPower(fl * multiplier);
        backLeft.setPower(bl * multiplier);


        if (gamepad2.a) {
            t.onPress();
        } else {
            t.onRelease();
        }

//        if (gamepad2.b) {
//            loadToggle.onPress();
//        } else {
//            loadToggle.onRelease();
//        }

        if (gamepad2.right_bumper) {
            pusher.setPosition(0.6);
        } else {
            pusher.setPosition(1);
        }
//
//        if (pushToggle.state) {
//        } else {
//            pusher.setPosition(1);
//        }

//        if (loadToggle.state) {
//            loader.setPosition((180.0-36.0)/180.0);
//        } else {
//            loader.setPosition(1);
//        }

        if (gamepad2.left_trigger > gamepad2.right_trigger && gamepad2.left_trigger > 0.3) {
            intake.setPower(1.0);
            intakeServo.setPower(0.8);
            upperIntakeServo.setPower(0.8);
        } else if (gamepad2.right_trigger > gamepad2.left_trigger && gamepad2.right_trigger > 0.3) {
            loader.setPosition(1);
            intake.setPower(-1);
            intakeServo.setPower(-0.8);
            upperIntakeServo.setPower(-0.8);
        } else {
            loader.setPosition((180.0-36.0)/180.0);
            intake.setPower(0);
            intakeServo.setPower(0);
            upperIntakeServo.setPower(0);
        }

        if (t.state) {
            double neededVel = calculateMissing(true, 27);
            if (neededVel == -1) {
                System.out.println("BAD");
            }
            spinToSpeed(neededVel);
        } else {
            flywheel1.setPower(0);
            flywheel2.setPower(0);
        }
        telemetry.addData("Shooter Speed: ", shooterThread.getSpeed());
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
        shooterThread.stopThread();
    }

    void spinToSpeed(double neededVelocity) {
        double speed = 0.7;
        System.out.println("NEEDED VEL: " + neededVelocity);
        do {
            System.out.println("Calcd: " + calculateMissing(false, 27));
            speed+=0.05;
            System.out.println("cSpeed: " + speed);
            System.out.println("CVEL: " + shooterThread.getSpeed());
            flywheel1.setPower(speed);
            flywheel2.setPower(speed);
        } while (calculateMissing(false, 27) > (80/39.37) && speed <= 1.0);
    }

    // Angle: 45
    // Horizontal from center (x dist): 144.5 mm
    // Vertical to bottom (offset): 258.823 mm -- 10.18in -- .258823 m
    // Goal to top 33 in, 35.5 to middle of top goal
    // Goal to mid 21 in, 27 to middle of middle goal
    // yDist = 30
    // xDist = Calculated below
    // Equation: .64(v * cos(45))^2 - (v * cos(45))^2 * x + 4.9x^2 = 0 <-- Solve for distance
    // Equation: (v * cos(45))^2 = -4.9x^2 / (.64 - x) <-- Solve for velocity
    double calculateMissing(boolean vMode, double yDist) {
        double height = (yDist/39.37) - .258823;
        double startToGoal = 135.5;
        double angleRads = Math.toRadians(45);
        double cosCalc = Math.cos(angleRads);
        double tanCalc = Math.tan(angleRads);
        if (vMode) {
            double distToGoal = (startToGoal - getCurrentPos().getY() -55.5)/39.37;
            double constant = -4.9 * (distToGoal * distToGoal);
            double vVal = (height * cosCalc) - (cosCalc * distToGoal * tanCalc);
            double rootable = constant/vVal;
            if (rootable < 0) {
                return -1;
            }
            return Math.sqrt(rootable);
        } else {
            double speed = shooterThread.getSpeed();
            System.out.println("SPeed: " + speed);
            double aVal = 4.9;
            double bVal = -speed * Math.sin(angleRads);
            double cVal = -.258823;
            double root = (bVal * bVal) - 4 * aVal * cVal;
            System.out.println("Root: " + root);
            double rooted = Math.sqrt(root);
            double sol1 = (-bVal + rooted) / (2 * aVal);
            double sol2 = (-bVal - rooted) / (2 * aVal);
            double t = Math.max(sol1, sol2);
            return speed * cosCalc * t;
//            double constant = height * Math.pow(speed * cosCalc, 2); // C value in a quadratic
//            double singleCoefficient = Math.pow(speed * cosCalc * tanCalc, 2); // B value in a quadratic
//            double quadraticCoefficient = 4.9;
//            double radicand = (singleCoefficient * singleCoefficient) - (4 * quadraticCoefficient * constant);
//            if (radicand < 0) {
//                return -1;
//            }
//            double rooted = Math.sqrt(radicand);
//            double sol1 = ((-singleCoefficient + rooted)/(2 * quadraticCoefficient)) * 3.281;
//            double sol2 = ((-singleCoefficient - rooted)/(2 * quadraticCoefficient)) * 3.281;
//            return Math.max(sol1, sol2);
        }
    }

    public OdometryPosition getCurrentPos() {
        OdometryPosition currentPosition = OdometryThread.getInstance().getCurrentPosition();
        return OdometryThread.getInstance().getCurrentPosition();
    }
}
