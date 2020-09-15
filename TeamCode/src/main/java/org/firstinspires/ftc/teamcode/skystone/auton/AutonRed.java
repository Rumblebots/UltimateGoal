package org.firstinspires.ftc.teamcode.skystone.auton;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.skystone.hardware.MotorsDrive;
import org.firstinspires.ftc.teamcode.skystone.hardware.Servos;

import java.util.List;
@Disabled
@Deprecated
public class AutonRed extends LinearOpMode {
    //For encoders(drive distances)
    static final double COUNTS_PER_MOTOR_REV = 537.6;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 6;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.3;
    static final double TURN_SPEED = 0.3;
    MotorsDrive motors = new MotorsDrive();
    Servos servos = new Servos();
    private ElapsedTime runtime = new ElapsedTime();

    //For tfod (skystone detection)
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY =
            "AaMHb/L/////AAABmV+wis0GQEysrIClzwptLXclDtwtlDiJsCLQCHpCy1cOp3M/aXwpSkDw0nLPjbqIZHUN0T5e3MU4L5Mu0NXOeKtHc8yawpUtGVmKA74pVOo8fBr/STmcWEIiproB4WBFMMds2s1MgcxtwGPQ15u96F+MkztyTmO1fUrHGnOilm0up4R42pldHeJjvFge4N7xa1oUujNtFniuUp6jiN48gLNI/DHFGySf+vB4fDMLCTAKyFh8Ca0haun8kQGntckEvGhvXpL/l2usagU5rHrQyiB9er5UXd5wDKZxv9+YACQpQ9Qcl4LyQa2YelQ/mljey0flEtKfMEzGWjbS+/1yBFeFUWf8EAJwi1AaBeK1xRii";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        int inMovd = 0;
        boolean skystone = false;
        String stone1 = "";
        Boolean isSkystone = false;
        String stone2 = "";
        int objCnt = 0;

        motors.init(hardwareMap);
        servos.init(hardwareMap);
        motors.resetEncoders();
        waitForStart();
        if (opModeIsActive()){
//            encoderDrive(0.2, 20, 20, 4.0);
//            sleep(500);
//            motors.resetEncoders();
            encoderDrive(0.5, -37, -37, 4.0);
            motors.resetEncoders();
            encoderStrafe(0.5, -15, -15, 4.0);
            motors.resetEncoders();

        }

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        objCnt = updatedRecognitions.size();
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            if (recognition.getLabel().equals("Skystone")) {
                                isSkystone = true;
                            }
                            if (i == 0) {
                                stone1 = recognition.getLabel();
                            }
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            i++;
                        }
                        telemetry.update();
                    }
                    System.out.println("STONE: " + stone1);
                    sleep(1000);
                    if (isSkystone) {
                        //TODO Move to stone and grab and drop on other side, backing up all the way after, then clear all vars
                        motors.resetEncoders();
                        encoderStrafe(0.5, -13, -13, 4.0);
                        motors.resetEncoders();
                        if (inMovd != 0 && inMovd <= 8) {
                            encoderDrive(0.4, 8, 8, 4.0);
                        } else if (inMovd > 8 && inMovd <= 12) {
                            encoderDrive(0.4, 3, 3, 4.0);
                        } else if (inMovd > 12) {
                            encoderDrive(0.4, -3, -3, 4.0);
                        } else {
                            encoderDrive(0.4, 3, 3, 4.0);
                        }
                        sleep(300);
                        servos.autonRed.setPower(1.0);
                        sleep(1500);
                        motors.resetEncoders();
                        encoderDrive(0.3, 3, 3, 4.0);
                        servos.autonRed.setPower(0.0);
                        motors.resetEncoders();
                        encoderStrafe(0.5, 20, 20, 4.0);
                        motors.resetEncoders();
                        encoderDrive(0.5, 110-inMovd, 110-inMovd, 4.0);
                        servos.autonRed.setPower(-1.0);
                        sleep(1000);
                        servos.autonRed.setPower(0.0);
                        motors.resetEncoders();
                        encoderDrive(0.5, -85+inMovd, -85-+inMovd, 4.0);
                        sleep(300);
                        motors.resetEncoders();
                        encoderStrafe(0.5, -15, -15, 4.0);
                        sleep(300);
                        servos.autonRed.setPower(1.0);
                        sleep(1000);
                        servos.autonRed.setPower(0.0);
                        motors.resetEncoders();
                        encoderStrafe(0.5, 20, 20, 4.0);
                        motors.resetEncoders();
                        encoderDrive(0.5, 70-inMovd, 70-inMovd, 4.0);
                        servos.autonRed.setPower(-1.0);
                        sleep(500);
                        servos.autonRed.setPower(0.0);
                        motors.resetEncoders();
                        encoderDrive(0.5, -20+inMovd, -20+inMovd, 4.0);
                        isSkystone = false;
                        stone1 = "";
                        stone2 = "";
                        break;
                    } else if (updatedRecognitions != null && objCnt != 0) {
                        //TODO Move up to next stone and wait for a redetection(clear both vars)
                        motors.resetEncoders();
                        encoderDrive(0.5, 4, 4, 4.0);
                        inMovd+=4;
                        stone1 = "";
                        stone2 = "";
                    }
                }

            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }

    }
    public void encoderStrafe(double speed,
                              double leftInches, double rightInches,
                              double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int offset = 0;
        double newLeft = -leftInches/.6;
        double newRight = -rightInches /.6;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = motors.motorFrontLeft.getCurrentPosition() + (int) (newLeft * COUNTS_PER_INCH);
            newRightTarget = motors.motorFrontRight.getCurrentPosition() + (int) (newRight * COUNTS_PER_INCH);

            motors.setTargetPos(-newLeftTarget, -newRightTarget, newLeftTarget, newRightTarget);

            // Turn On RUN_TO_POSITION
            motors.setRunToPos();
            // reset the timeout time and start motion.
            runtime.reset();
            motors.motorFrontLeft.setPower(Math.abs(speed));
            motors.motorFrontRight.setPower(Math.abs(speed));
            motors.motorBackRight.setPower(Math.abs(speed));
            motors.motorBackLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.motorFrontLeft.isBusy() && motors.motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        motors.motorFrontLeft.getCurrentPosition(),
                        motors.motorFrontRight.getCurrentPosition());
                telemetry.update();
            }
            motors.stopAll();
            motors.stopRunToPos();
            //  sleep(250);   // optional pause after each move
        }
    }
//    Thread downServo = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while (true) {
//                servos.autonRed.setPosition(0.0);
//            }
//        }
//    });
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        double newLeft = -leftInches;
        double newRight = -rightInches;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = motors.motorFrontLeft.getCurrentPosition() + (int) (newLeft * COUNTS_PER_INCH);
            newRightTarget = motors.motorFrontRight.getCurrentPosition() + (int) (newRight * COUNTS_PER_INCH);

            motors.setTargetPos(-newLeftTarget, newRightTarget, -newLeftTarget, newRightTarget);

            // Turn On RUN_TO_POSITION
            motors.setRunToPos();
            // reset the timeout time and start motion.
            runtime.reset();
            motors.motorFrontLeft.setPower(Math.abs(speed));
            motors.motorFrontRight.setPower(Math.abs(speed));
            motors.motorBackRight.setPower(Math.abs(speed));
            motors.motorBackLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.motorFrontLeft.isBusy() && motors.motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        motors.motorFrontLeft.getCurrentPosition(),
                        motors.motorFrontRight.getCurrentPosition());
                telemetry.update();
            }
            motors.stopAll();
            motors.stopRunToPos();
            //  sleep(250);   // optional pause after each move
        }
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcamRed");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.2;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
