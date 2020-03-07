package org.firstinspires.ftc.teamcode.hardware.v2;

import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class WebcamTFOD {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private static final String VUFORIA_KEY =
            "AaMHb/L/////AAABmV+wis0GQEysrIClzwptLXclDtwtlDiJsCLQCHpCy1cOp3M/aXwpSkDw0nLPjbqIZHUN0T5e3MU4L5Mu0NXOeKtHc8yawpUtGVmKA74pVOo8fBr/STmcWEIiproB4WBFMMds2s1MgcxtwGPQ15u96F+MkztyTmO1fUrHGnOilm0up4R42pldHeJjvFge4N7xa1oUujNtFniuUp6jiN48gLNI/DHFGySf+vB4fDMLCTAKyFh8Ca0haun8kQGntckEvGhvXpL/l2usagU5rHrQyiB9er5UXd5wDKZxv9+YACQpQ9Qcl4LyQa2YelQ/mljey0flEtKfMEzGWjbS+/1yBFeFUWf8EAJwi1AaBeK1xRii";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    public void activateTfod() {
        if (tfod != null) {
            tfod.activate();
        }
    }
    public List<Recognition> getUpdatedRecognitions() {
        return tfod.getUpdatedRecognitions();
    }
    public void init(HardwareMap hwMap) {
        initVuforia(hwMap);
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod(hwMap);
        }
    }
    private void initVuforia(HardwareMap hardwareMap) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.6;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    public String autoInitDetect() {
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        String returner = "";
        if (updatedRecognitions != null && updatedRecognitions.size() == 3) {
            Log.i("POS", "NOT NULL");
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
                    returner = "L";
                } else if (skystone > stone && skystone > stone2) {
                    returner = "R";
                } else {
                    returner = "C";
                }
            } else {
                returner = "UNKNOWN";
            }
        } else {
            Log.i("POS", "NULL");
            returner = "LESS THAN 3";
        }
        return returner;
    }
    public void stopTFOD() {
        tfod.shutdown();
    }
}
