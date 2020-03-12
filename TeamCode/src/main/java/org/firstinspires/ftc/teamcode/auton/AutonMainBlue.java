/****
 * Made by Tejas Mehta
 */
package org.firstinspires.ftc.teamcode.auton;

import android.util.Log;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.hardware.MotorsDrive;
import org.firstinspires.ftc.teamcode.hardware.v2.Motors_Drive;
import org.firstinspires.ftc.teamcode.hardware.v2.Sensors.Sensors;
import org.firstinspires.ftc.teamcode.hardware.v2.Servos;
import org.firstinspires.ftc.teamcode.hardware.v2.WebcamTFOD;

import java.util.List;

@Autonomous(name = "AutonBlueMain", group = "MainAuto")
public class AutonMainBlue extends LinearOpMode {
    Motors_Drive drive_motors = new Motors_Drive();
    MotorsDrive motors = new MotorsDrive();
    Sensors sensors = new Sensors();
    Motors_Drive.Auton mainMotors = new Motors_Drive().new Auton();
    WebcamTFOD webcam = new WebcamTFOD();
    //    Sensors sensors = new Sensors();
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
        motors.init(hardwareMap);
        webcam.init(hardwareMap);
        sensors.init(hardwareMap);
        webcam.activateTfod();
        telemetry.update();
        boolean left = false, right = false, center = true;
        String pos = "";
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
                telemetry.update();
            }
        }
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                encoderDrive(4, 3, "BACKWARD", 0.2);
                if (pos.equals("L")) {
                    encoderStrafe(4, 28, "RIGHT", 0.3);
                    moveUntilDistance(7);
                    sleep(500);
                    driveToFoundation(72, 2300);
                    break;
                }
                if (pos.equals("C")) {
                    encoderDrive(4, 18, "RIGHT", 0.5);
                    moveUntilDistance(7);
                    sleep(500);
                    driveToFoundation(66, 2400);
                    break;
                }
                if (pos.equals("R")) {
                    encoderStrafe(4, 8, "RIGHT", 0.5);
                    moveUntilDistance(7);
                    sleep(500);
                    driveToFoundation(60, 2500);
                }
                break;
            }
        }

    }
    void driveToFoundation(int strafeDistance, int time) {
        grabBlock();
        encoderDrive(4, 6, "FORWARD", 0.5);
        strafeTime("RIGHT", 1, time);
        //encoderStrafe(10, strafeDistance, "LEFT", 0.6);
        moveUntilDistance(4);
        dropBlock();
        encoderDrive(4, 12, "FORWARD", 0.5);
        strafeTime("LEFT", 1, time+1200);
        //encoderStrafe(15, strafeDistance + 18, "RIGHT", 0.6);
        moveUntilDistance(6);
        verifyColorStrafe("RIGHT", 0.5);
        grabBlock();
        encoderDrive(4, 6, "FORWARD", 0.5);
        strafeTime("RIGHT", 1, time+700);
        //encoderStrafe(15, strafeDistance + 18, "LEFT", 0.6);
        moveUntilDistance(4);
        dropBlock();
        moveFoundation();
        encoderDrive(4, 40, "FORWARD", 0.8);
    }
    void moveUntilDistance(int dist) {
        mainMotors.RunToPos(false);
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        while (Double.isNaN(sensors.distanceSensorLeft.getDistanceIn()) || sensors.distanceSensorLeft.getDistanceIn() > dist) {
            telemetry.addData("Distance: ", sensors.distanceSensorLeft.getDistanceIn());
            mover.MeccanumDirection("BACKWARD", 0.3);
        }
        mover.Brake();
        mainMotors.RunToPos(true);
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
    private void moveFoundation() {
        encoderDrive(4, 6, "BACKWARD", 0.4);
        servos.foundationServo1.setPosition(0.0);
        sleep(500);
        encoderDrive(4, 30, "FORWARD", 0.5);
//        encoderTurn(4, 20, "90", 0.5);
        gyroTurn(-90, 0.5);
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
        sleep(1000);
        servos.armRControl.setPosition(0.5);
        sleep(1000);
        servos.blockGrabberR.setPosition(0.2);
        sleep(1000);
        servos.armRControl.setPosition(0.9);
        sleep(1000);
    }
    void encoderDrive(int timeoutS, double inches, String fb, double power) {
        mainMotors.ResetEncoders();
        driveAndStop(inches, fb, power, timeoutS);
    }
    void encoderStrafe(int timeoutS, double inches, String rl, double power) {
        mainMotors.ResetEncoders();
        driveAndStop(inches/.6, rl, power, timeoutS);
    }
    void encoderTurn(int timeoutS, double inches, String amnt, double power) {
        mainMotors.ResetEncoders();
        switch (amnt) {
            case "45":
                encoderDrive(4, 20, "TURN_R", power);
                break;
            case "90":
                encoderDrive(4, 40, "TURN_R", power);
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
            Log.i("POS", String.valueOf(inches));
            mainMotors.setTargetDirection(direction, (int)inches);
            mainMotors.RunToPos(true);
            runtime.reset();
            mainMotors.autonMove(power);
            int tenthCnt = 9;
            int oldTenthCnt = 0;
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (mainMotors.frontLeftBusy() && mainMotors.frontRightBusy() && mainMotors.backLeftBusy() && mainMotors.backLeftBusy())) {
                if (direction.equals("FORWARD") || direction.equals("LEFT")) {
                    double distanceLeft = (inches * COUNTS_PER_INCH) - mainMotors.getFrontRightPos();
                    System.out.println("LEFT: " + distanceLeft);
                    double currentTenth = (inches * COUNTS_PER_INCH)/10;
                    System.out.println("CNT: " + tenthCnt);
                    double distIncPos = .15/currentTenth;
                    double distIncNeg = .25/currentTenth;
                    if (currentTenth*3 >= distanceLeft) {
                        power += distIncPos;
                    } else {
                        power-= distIncNeg;
                    }
//
//                    if (oldTenthCnt != tenthCnt && tenthCnt > 4) {
//                        power+=0.01;
//                        oldTenthCnt = tenthCnt;
//                        if (distanceLeft < currentTenth * tenthCnt) {
//                            tenthCnt--;
//                        }
//                        System.out.println("INC");
//                    } else {
//                        power-=0.01;
//                        oldTenthCnt = tenthCnt;
//                        if (distanceLeft < currentTenth * tenthCnt) {
//                            tenthCnt--;
//                        }
//                        power -= 0.01;
//                        System.out.println("DEC");
//                    }
//                    if (distanceLeft > ((inches*COUNTS_PER_INCH)/2)) {
//                        System.out.println("INC");
//                        power+=0.001;
//                    } else {
//                        power -= 0.01;
//                        System.out.println("DEC");
//                    }
                    mainMotors.autonMove(power);
                } else {
                    double distanceLeft = (inches * COUNTS_PER_INCH) + mainMotors.getFrontRightPos();
                    System.out.println("LEFT: " + distanceLeft);
                    double currentTenth = (inches * COUNTS_PER_INCH)/10;
                    System.out.println("CNT: " + tenthCnt);
                    double distIncPos = .15/currentTenth;
                    double distIncNeg = .25/currentTenth;
                    if (currentTenth*3 >= distanceLeft) {
                        power += distIncPos;
                    } else {
                        power-= distIncNeg;
                    }
//                    if (tenthCnt > 4) {
//                        if (oldTenthCnt != tenthCnt) {
//                            power += 0.1;
//                        }
//                        oldTenthCnt = tenthCnt;
//                        if (distanceLeft < currentTenth * tenthCnt) {
//                            tenthCnt--;
//                        }
//                        System.out.println("INC: " + currentTenth);
//                    } else {
//                        if (oldTenthCnt != tenthCnt) {
//                            power -= 0.07;
//                        }
//                        oldTenthCnt = tenthCnt;
//                        if (distanceLeft < currentTenth * tenthCnt) {
//                            tenthCnt--;
//                        }
//                        System.out.println("DEC: " + currentTenth);
//                    }
                    mainMotors.autonMove(power);
                }
                telemetry.addData("Path2", "Running at %7d :%7d",
                        mainMotors.getFrontLeftPos(),
                        mainMotors.getFrontRightPos());
                telemetry.update();

            }
            Motors_Drive.Common mover = new Motors_Drive().new Common();
            mover.Brake();
            mainMotors.RunToPos(false);
        }
    }
    void gyroTurn(int angle, double power) {
        Motors_Drive.Common mover = new Motors_Drive().new Common();
        if (angle > 0) {
            while (getCurrentOrientation().thirdAngle < angle) {
                mover.MeccanumDirection("TURN_R", power);
            }
        } else {
            while (getCurrentOrientation().thirdAngle < angle) {
                mover.MeccanumDirection("TURN_L", power);
            }
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
}
