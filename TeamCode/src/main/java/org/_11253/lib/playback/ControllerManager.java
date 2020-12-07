package org._11253.lib.playback;

import org._11253.lib.controllers.Controller;

public class ControllerManager {
    private Controller controller;
    private Recording recording = new Recording();

    public ControllerManager(Controller controller) {
        this.controller = controller;
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
        ControllerState state = recording.getRecording().get(timestamp);
        assert state != null;
        PlaybackControllerManager.setControllerState(
                controller,
                state
        );
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

    public Recording getRecording() {
        return recording;
    }
}
