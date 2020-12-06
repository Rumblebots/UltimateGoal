package me.wobblyyyy.pathfinder_ftc;

import me.wobblyyyy.pathfinder.localizer.PfMotorPower;
import me.wobblyyyy.pathfinder.recording.Recorder;
import me.wobblyyyy.pathfinder.recording.RecorderInterface;
import me.wobblyyyy.pathfinder.recording.data.PfRwNormal;
import me.wobblyyyy.pathfinder.recording.gson.GsonLoad;
import me.wobblyyyy.pathfinder.recording.gson.GsonSave;

/**
 * An implementation of the Recorder code provided in this library.
 *
 * <p>
 * Rather than using the raw Recorder, you're probably going to have a better
 * time just using this class. Using this shouldn't exactly be too difficult,
 * you're just going to have to make sure to read all of the JavaDocs here.
 * I promise, reading some documentation will help. It's crazy, I know, but
 * it works.
 * </p>
 *
 * @author Colin Robertson
 */
public class RecorderAndroid implements RecorderInterface {
    /**
     * The file reading and writing system. You don't really have to
     * worry about how this works - the short answer is... well, it
     * does, and that's all you need to know.
     */
    private PfRwNormal rw = new PfRwNormal();

    /**
     * Start a new recording. Keep in mind - this will override any loaded recording.
     *
     * @param duration the duration of the recording. I don't really want to type out
     *                 all of my reasoning behind this, but I would suggest that you
     *                 try to keep the duration of the recordings to about 10 seconds.
     *                 10 seconds as a max, that is. Shorter recordings don't have any
     *                 performance issues, but the longer the recording is - and thus
     *                 the more memory it takes up - the worse the performance will be.
     * @param delay    how long before the recording starts. This is really only useful
     *                 if you're trying to record during an OpMode - say, for example,
     *                 you want to record, but want to have a couple seconds of prep.
     * @param interval how long between times the motor power of the robot is logged.
     *                 Once again, I'm just going to ask you to trust me on this one.
     *                 Keep this to a minimum of about 50ms. 100ms, even, would have
     *                 very little impact on the operation of the robot. Performance
     *                 will shrink (and you may encounter crashes) if you make this
     *                 value too low or too high. 50ms is optimal for 10s recordings.
     */
    @Override
    public void startRecording(long duration, long delay, long interval) {
        Recorder.startRecording(
                duration,
                delay,
                interval
        );
    }

    /**
     * Execute the loaded recording.
     *
     * <p>
     * Obviously, you can't run this before you actually either create or load
     * a recording to use.
     * </p>
     */
    @Override
    public void executeRecording() {
        Recorder.execute();
    }

    /**
     * Save a recording to a file.
     *
     * @param filePath the path of the file.
     * @param fileName the name of the file.
     */
    @Override
    public void saveRecording(String filePath, String fileName) {
        rw.save(
                filePath,
                fileName,
                GsonSave.getJsonString(
                        Recorder.record
                )
        );
    }

    /**
     * Load a recording from the local files of the device.
     *
     * @param filePath the path of the file.
     * @param fileName the name of the file.
     */
    @Override
    public void loadRecording(String filePath, String fileName) {
        Recorder.record = GsonLoad.getHashMap(
                rw.read(
                        filePath,
                        fileName
                )
        );
    }

    /**
     * Update the motorPower of the recording. For your own good, you should
     * really strongly consider reading the rest of this documentation.
     *
     * <p>
     * This should be updated as frequently as possible without encountering any
     * potential performance downsides. Updating the motorPower every single ms
     * is obviously optimal - you'll get extremely accurate positional data on
     * the robot, and, as a result, the recording will function most optimally.
     * </p>
     *
     * <p>
     * You also have to keep something in mind here - every single recorded instance
     * of power given to the motors takes up a bit of a couple of things...
     * <ul>
     *     <li>File space. Large files take longer to read and write.</li>
     *     <li>Memory. Large files require more memory.</li>
     *     <li>Performance. Less memory means less potential performance.</li>
     *     <li>A LOT of asynchronous events. Based on some of my previous experimentation
     *     with the async functionality of this library, instances of StringEvents can
     *     typically have up to 300 or so handles before performance begins to take a serious
     *     hit.</li>
     * </ul>
     * </p>
     *
     * @param motorPower the power the motors are currently receiving.
     */
    @Override
    public void update(PfMotorPower motorPower) {
        Recorder.update(motorPower);
    }
}
