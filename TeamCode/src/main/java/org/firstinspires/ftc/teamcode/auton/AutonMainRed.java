package org.firstinspires.ftc.teamcode.auton;

import android.util.Log;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;
import org.firstinspires.ftc.teamcode.hardware.v2.Servos;
import org.firstinspires.ftc.teamcode.hardware.v2.WebcamTFOD;

import java.util.List;

@Autonomous(name = "AutonRedMain", group = "MainAuto")
public class AutonMainRed extends LinearOpMode {
    Motors_Drive drive_motors = new Motors_Drive();
    Motors_Drive.Auton mainMotors = new Motors_Drive().new Auton();
    WebcamTFOD webcam = new WebcamTFOD();
    Sensors sensors = new Sensors();
    Servos servos = new Servos();
    private static BNO055IMU imu;
    private static Orientation getCurrentOrientation ()
    {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    }
    private ElapsedTime runtime = new ElapsedTime();
    static final double COUNTS_PER_MOTOR_REV = 537.6;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 6;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    @Override
    public void runOpMode() {
        servos.init(hardwareMap);
        drive_motors.init(hardwareMap);
        webcam.init(hardwareMap);
        webcam.activateTfod();
        initGyro();
        sensors.init(hardwareMap);
        boolean left = false, right = false, center = true;
        String pos = "";
        mainMotors.ResetEncoders();
        //grabBlock();
        while (!isStarted()) {
            List<Recognition> updatedRecognitions = webcam.getUpdatedRecognitions();
//            telemetry.addData("SIZE", updatedRecognitions.size());
//            pos = webcam.autoInitDetect();
//            Log.i("POS", pos);
//            telemetry.addData("POS", pos);
//            telemetry.update();
            if (updatedRecognitions != null && updatedRecognitions.size() == 3) {
                int skystone = -1;
                int stone = -1;
                int stone2 = -1;
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals("Skystone")) {
                        skystone = (int) recognition.getLeft();
                    } else if (stone == -1) {
                        stone = (int) recognition.getLeft();
                    } else {
                        stone2 = (int) recognition.getLeft();
                    }
                }
                if (skystone != -1 && stone != -1 && stone2 != -1) {
                    if (skystone < stone && skystone < stone2) {
                        pos = "L";
                        left = true;
                        right = false;
                        center = false;
                        telemetry.addData("Skystone Position", "Left");
                    } else if (skystone > stone && skystone > stone2) {
                        pos = "R";
                        left = false;
                        right = true;
                        center = false;
                        telemetry.addData("Skystone Position", "Right");
                    } else {
                        pos = "C";
                        left = false;
                        right = false;
                        center = true;
                        telemetry.addData("Skystone Position", "Center");
                    }
                } else {
                    telemetry.addData("Skystone Position", "Not Known");
                }
                telemetry.addData("Distance: ", sensors.distanceSensorLeft.getDistanceIn());
                telemetry.update();
            }
            //telemetry.update();
        }
        waitForStart();
        //webcam.st opTFOD();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //encoderDrive(4, 3, "BACKWARD", 0.2);
                //encoderStrafe(4, 22, "RIGHT", 0.45);
                if (pos.equals("R")) {
                    encoderStrafe(4, 18, "LEFT", 0.5);
                    //encoderStrafe(4, 6, "RIGHT", 0.3);
//                    encoderDrive(4, 31, "BACKWARD", 0.2);
                    moveUntilDistance(7);
                    sleep(500);
                    driveAndMoveFoundation(60);
                    break;
                }
                if (pos.equals("C")) {
                    encoderStrafe(4, 12, "LEFT", 0.3);
//                    encoderDrive(4, 26, "BACKWARD", 0.3);
                    moveUntilDistance(7);
                    sleep(500);
                    driveAndMoveFoundation(55);
                    break;
                }
                if (pos.equals("L")) {
                    encoderStrafe(4, 4, "LEFT", 0.3);
//                    encoderDrive(4, 39, "BACKWARD", 0.2);
                    moveUntilDistance(7);
                    sleep(500);
                    driveAndMoveFoundation(50);
                }
                break;
            }
        }
//        encoderDrive(4, 20, 20, 0.5);
        //encoderDrive(4, -20, 20, 0.3);
        //encoderStrafe(.45, -24, -24, 4);
    }
    void moveUntilDistance(int dist) {
        mainMotors.RunToPos(false);
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        double distRead = sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH);
        while ((Double.isNaN(distRead) || distRead > dist) && opModeIsActive()) {
            Log.i("DIST", String.valueOf(sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.addData("Distance: ", sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH));
            mover.MeccanumDirection("BACKWARD", 0.4);
            distRead = sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH);
        }
        mover.Brake();
        //mainMotors.RunToPos(true);
    }
    void strafeTime(String direction, double power, int time) {
        mainMotors.RunToPos(false);
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        mover.MeccanumDirection(direction, power);
        sleep(time);
        mover.Brake();
    }
    void strafeUntilDistance(int dist, String direction, double power) {
        mainMotors.RunToPos(false);
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        while ((Double.isNaN(sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH)) || sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH) > dist) && opModeIsActive()) {
            Log.i("DIST", String.valueOf(sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.addData("Distance: ", sensors.distanceSensorLeft.distanceSensor.getDistance(DistanceUnit.INCH));
            mover.MeccanumDirection(direction, power);
        }
        mover.Brake();
    }
    void verifyColorStrafe(String direction, double power) {
        mainMotors.RunToPos(false);
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        while (sensors.getVerifySensorVals().get("argb") != 0) {
            mover.MeccanumDirection(direction, power);
        }
        mover.Brake();
    }
    void driveAndMoveFoundation(int strafeDistance) {
        grabBlock();
        encoderDrive(4, 6, "FORWARD", 0.5);
        sleep(100);
        strafeTime("LEFT", 0.8, 2300);
        moveUntilDistance(4);
        dropBlock();
        encoderDrive(4, 12, "FORWARD", 0.5);
        sleep(100);
        strafeTime("RIGHT", 0.8, 3500);
        moveUntilDistance(6);
        verifyColorStrafe("LEFT", 0.5);
        grabBlock();
        encoderDrive(4, 6, "FORWARD", 0.5);
        strafeTime("LEFT", 0.8, 3000);
        //encoderStrafe(15, strafeDistance + 18, "LEFT", 0.3);
        moveUntilDistance(4);
        dropBlock();
        moveFoundation();
        encoderDrive(4, 40, "FORWARD", 0.5);
    }

    private void moveFoundation() {
        encoderDrive(4, 6, "BACKWARD", 0.4);
        servos.foundationServo1.setPosition(0.0);
        sleep(500);
        encoderDrive(4, 30, "FORWARD", 0.5);
//        encoderTurn(4, 20, "90", 0.5);
        gyroTurn(90, 0.5);
        encoderDrive(4, 20, "BACKWARD", 0.5);
        servos.foundationServo1.setPosition(1.0);
        sleep(250);
    }

    private void dropBlock() {
        servos.armRControl.setPosition(0.5);
        servos.blockGrabberR.setPosition(1.0);
        sleep(500);
        servos.armRControl.setPosition(0.8);
    }

    private void grabBlock() {
        servos.blockGrabberR.setPosition(1.0);
        sleep(500);
        servos.armRControl.setPosition(0.2);
        sleep(600);
        servos.blockGrabberR.setPosition(0.0);
        sleep(1000);
        servos.armRControl.setPosition(0.5);
        sleep(500);
    }

    void encoderDrive(int timeoutS, double inches, String fb, double power) {
        driveAndStop(inches, fb, power, timeoutS);
    }
    void encoderStrafe(int timeoutS, double inches, String rl, double power) {
        driveAndStop(inches/.6, rl, power, timeoutS);
    }
    void encoderTurn(int timeoutS, double inches, String amnt, double power) {
        switch (amnt) {
            case "45":
                encoderDrive(4, 20, "TURN_R", power);
                break;
            case "90":
                encoderDrive(4, 36, "TURN_R", power);
                break;
            case "180":
                encoderDrive(4, 80, "TURN_R", power);
                break;
            case "270":
                encoderDrive(4, 120, "TURN_R", power);
                break;
            case "-45":
                encoderDrive(4, 20, "TURN_L", power);
                break;
            case "-90":
                encoderDrive(4, 40, "TURN_L", power);
                break;
            case "-180":
                encoderDrive(4, 80, "TURN_L", power);
                break;
            case "-270":
                encoderDrive(4, 120, "TURN_L", power);
                break;
        }
    }
    void driveAndStop(double inches, String direction, double power, int timeoutS) {
        if (opModeIsActive()) {
            mainMotors.ResetEncoders();
            Log.i("POS", String.valueOf(inches));
            //mainMotors.setTargetDirection(direction, (int)inches);
            mainMotors.runWithEncoders(true);
            runtime.reset();
            //mainMotors.autonMove(power);
            System.out.println("COUNT: " + (inches * COUNTS_PER_INCH));
            int tenthCnt = 9;
            int oldTenthCnt = 0;
            int motorTarget = mainMotors.getMotorTarget(direction, (int)inches);
            Motors_Drive.Common mover = new Motors_Drive().new Common();
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && mainMotors.needsMove(direction, motorTarget)) {
                power = mainMotors.gradualSpeed(direction, power, (int)inches);
                Log.i("POW", String.valueOf(power));
                mover.MeccanumDirection(direction, power);
                System.out.println("CURRENT POS: " + mainMotors.getFrontRightPos());
//                telemetry.addData("Path2", "Running at %7d :%7d",
//                        mainMotors.getFrontLeftPos(),
//                        mainMotors.getFrontRightPos());
//                telemetry.update();
            }
            mover.Brake();
//            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
//                    (mainMotors.frontLeftBusy() && mainMotors.frontRightBusy() && mainMotors.backLeftBusy() && mainMotors.backLeftBusy())) {
//                if (direction.equals("FORWARD") || direction.equals("LEFT")) {
//                    double distanceLeft = (inches * COUNTS_PER_INCH) - mainMotors.getFrontRightPos();
//                    System.out.println("LEFT: " + distanceLeft);
//                    double currentTenth = (inches * COUNTS_PER_INCH) / 10;
//                    System.out.println("CNT: " + tenthCnt);
//                    double distIncPos = .15 / currentTenth;
//                    double distIncNeg = .25 / currentTenth;
//                    if (currentTenth * 3 >= distanceLeft) {
//                        power += distIncPos;
//                        if (power >= 1) {
//                            power = 1;
//                        }
//                        mainMotors.autonMove(power);
//                    } else {
//                        power -= distIncNeg;
//                        if (power <= 0.2) {
//                            power = 0.2;
//                        }
//                        mainMotors.autonMove(power);
//                    }
//
////                if (oldTenthCnt != tenthCnt && tenthCnt > 4) {
////                    power+=0.01;
////                    oldTenthCnt = tenthCnt;
////                    if (distanceLeft < currentTenth * tenthCnt) {
////                        tenthCnt--;
////                    }
////                    System.out.println("INC");
////                } else {
////                    power-=0.01;
////                    oldTenthCnt = tenthCnt;
////                    if (distanceLeft < currentTenth * tenthCnt) {
////                        tenthCnt--;
////                    }
////                    power -= 0.01;
////                    System.out.println("DEC");
////                }
////                if (distanceLeft > ((inches*COUNTS_PER_INCH)/2)) {
////                    System.out.println("INC");
////                    power+=0.001;
////                } else {
////                    power -= 0.01;
////                    System.out.println("DEC");
////                }
//                } else {
//                    double distanceLeft = (inches * COUNTS_PER_INCH) + mainMotors.getFrontRightPos();
//                    System.out.println("LEFT: " + distanceLeft);
//                    double currentTenth = (inches * COUNTS_PER_INCH) / 10;
//                    System.out.println("CNT: " + tenthCnt);
//                    double distIncPos = .15 / currentTenth;
//                    double distIncNeg = .25 / currentTenth;
//                    if (currentTenth*3 >= distanceLeft) {
//                        power += distIncPos;
//                        if (power >= 1) {
//                            power = 1;
//                        }
//                        mainMotors.autonMove(power);
//                    } else {
//                        power -= distIncNeg;
//                        if (power <= 0.2) {
//                            power = 0.2;
//                        }
//                        mainMotors.autonMove(power);
//                    }
////                    if (tenthCnt > 4) {
////                        if (oldTenthCnt != tenthCnt) {
////                            power += 0.1;
////                        }
////                        oldTenthCnt = tenthCnt;
////                        if (distanceLeft < currentTenth * tenthCnt) {
////                            tenthCnt--;
////                        }
////                        System.out.println("INC: " + currentTenth);
////                    } else {
////                        if (oldTenthCnt != tenthCnt) {
////                            power -= 0.2;
////                        }
////                        oldTenthCnt = tenthCnt;
////                        if (distanceLeft < currentTenth * tenthCnt) {
////                            tenthCnt--;
////                        }
////                        System.out.println("DEC: " + currentTenth);
////                    }
//                }
//
//                System.out.println("CURRENT POS: " + mainMotors.getFrontRightPos());
//                telemetry.addData("Path2", "Running at %7d :%7d",
//                        mainMotors.getFrontLeftPos(),
//                        mainMotors.getFrontRightPos());
//                telemetry.update();
//            }
            //mainMotors.autonMove(0.0);
        }
    }
    void gyroTurn(int angle, double power) {
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        while (getCurrentOrientation().thirdAngle < angle) {
            mover.MeccanumDirection("TURN_R", power);
        }
    }
    void initGyro() {
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.calibrationDataFile = "CalibrationData.json";
        params.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(params);
    }
    private double getRawHeading() {
        return getCurrentOrientation().firstAngle;
    }

    /**
     * @return the robot's current heading in radians
     */
    public double getHeading() {
        return (getRawHeading()) % (2.0 * Math.PI);
    }
}
