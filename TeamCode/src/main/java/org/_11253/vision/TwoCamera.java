package org._11253.vision;

import org._11253.lib.Global;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

/**
 * A holder for two cameras.
 *
 * @author Colin Robertson
 */
public class TwoCamera {
    /**
     * The first of the two external webcams.
     *
     * <p>
     * Webcams =/= internal cameras. These are entirely different and are initialized
     * in a different manner. Sucks to suck, you know?
     * </p>
     */
    public OpenCvCamera camera1;

    /**
     * The second of the two external webcams.
     *
     * <p>
     * Webcams =/= internal cameras. These are entirely different and are initialized
     * in a different manner. Sucks to suck, you know?
     * </p>
     */
    public OpenCvCamera camera2;

    /**
     * The name of the first webcam.
     */
    public String camera1name = "Webcam 1";

    /**
     * The name of the second webcam.
     */
    public String camera2name = "Webcam 2";

    /**
     * Get the camera monitor's ID.
     *
     * @return the camera monitor view id.
     */
    private int getCameraMonitorViewId() {
        return Global.getHwMap().appContext.getResources().getIdentifier(
                "cameraMonitorViewId",
                "id",
                Global.getHwMap().appContext.getPackageName()
        );
    }

    /**
     * Get a list of all of the viewport container IDs.
     *
     * @return viewport container IDs.
     */
    private int[] getViewportContainerIds() {
        return OpenCvCameraFactory.getInstance().splitLayoutForMultipleViewports(
                getCameraMonitorViewId(),
                2,
                OpenCvCameraFactory.ViewportSplitMethod.VERTICALLY
        );
    }

    /**
     * Load the two webcams.
     *
     * <p>
     * This is dependent on the global hardware map being initialized.
     * </p>
     */
    public void loadCameras() {
        int[] ids = getViewportContainerIds();

        camera1 = OpenCvCameraFactory.getInstance().createWebcam(
                Global.getHwMap().get(
                        WebcamName.class,
                        camera1name
                ),
                ids[0]
        );

        camera2 = OpenCvCameraFactory.getInstance().createWebcam(
                Global.getHwMap().get(
                        WebcamName.class,
                        camera2name
                ),
                ids[1]
        );
    }

    /**
     * Get the FPS of the webcams.
     *
     * @return the FPS of the webcams, you fucking moron. What did you expect?
     */
    public int[] getFps() {
        return new int[] {
                (int) camera1.getFps(),
                (int) camera2.getFps()
        };
    }
}
