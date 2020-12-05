package me.wobblyyyy.pathfinder.recording;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

public interface RecorderInterface {
    void startRecording(long duration, long delay, long interval);
    void executeRecording();
    void saveRecording(String filePath, String fileName);
    void loadRecording(String filePath, String fileName);
    void update(PfMotorPower motorPower);
}
