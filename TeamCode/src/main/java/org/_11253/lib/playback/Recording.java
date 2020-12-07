package org._11253.lib.playback;

import java.util.HashMap;

public class Recording {
    private HashMap<Long, ControllerState> recording = new HashMap<>();

    public HashMap<Long, ControllerState> getRecording() {
        return recording;
    }

    public void setRecording(HashMap<Long, ControllerState> recording) {
        this.recording = recording;
    }

    public void addState(long timestamp,
                         ControllerState controllerState) {
        recording.put(timestamp, controllerState);
    }
}
