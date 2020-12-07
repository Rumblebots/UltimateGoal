package org._11253.lib.playback;

import org._11253.lib.controllers.Controller;
import org._11253.lib.op.Template;

public class ControllerManager {
    private Controller controller;
    private Template template;
    private Recording recording = new Recording();
    private ControllerState stateToPlay = new ControllerState();

    public ControllerManager(final Controller controller,
                             Template template) {
        this.controller = controller;
        this.template = template;
        template.beforeStartRun.add(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!stateToPlay.isStateEmpty()) {
                            PlaybackControllerManager.setControllerState(
                                    controller,
                                    stateToPlay
                            );
                        }
                    }
                }
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void recordState(long timestamp) {
        recording.addState(
                timestamp,
                PlaybackControllerManager.getControllerState(controller)
        );
    }

    public void playState(long timestamp) {
        stateToPlay = recording.getRecording().get(timestamp);
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

    public Recording getRecording() {
        return recording;
    }
}
