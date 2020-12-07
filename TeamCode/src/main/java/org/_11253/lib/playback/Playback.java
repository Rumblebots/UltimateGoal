package org._11253.lib.playback;

import org._11253.lib.controllers.Controller;
import org._11253.lib.op.Template;
import org._11253.lib.playback.data.RwInterface;
import org._11253.lib.playback.gson.GsonLoad;
import org._11253.lib.playback.gson.GsonSave;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

import java.util.HashMap;

public class Playback {
    private ControllerManager controller1;
    private ControllerManager controller2;

    private long offset = 0;

    private static String saveName = "fpb: Data Saver";
    private static String recorderName = "fpb: Data Recorder";
    private static String playbackName = "fpb: Data Playback";
    private static String offsetName = "fpb: Offset Manager";
    private static String closerName = "fpb: Closer Manager";
    private static String c1f = "c1f_data.json";
    private static String c2f = "c2f_data.json";

    public Playback(Controller controller1,
                    Controller controller2,
                    Template template) {
        this.controller1 = new ControllerManager(controller1, template);
        this.controller2 = new ControllerManager(controller2, template);
    }

    /**
     * Create a new recording and save it locally.
     *
     * @param name     the name of the recording. This is used in saving the recording locally
     *                 in order to enable loading the recording and eventually playing it back.
     * @param duration the duration of the recording - how long it is. Generally, the duration
     *                 of the recording should be as short as possible without having to cut
     *                 out some gameplay. Due to the restrictions of the phones that are used
     *                 by the FTC, performance issues may be encountered as durations get
     *                 increasingly large. About 10 seconds (10,000 ms) is typically fairly alright.
     * @param delay    how long before the recording starts. Usually, 0 is your best bet. However,
     *                 if you want to wait 5-10 seconds before starting a recording, this is your
     *                 space to do so.
     * @param interval how long (ms) between when controller states are recorded. As with the duration
     *                 parameter of this function, you generally want to have optimized to not cause
     *                 any performance issues. A value of about 50 is fairly good.
     */
    public void record(final String name,
                       long duration,
                       long delay,
                       long interval) {
        // Schedule the offset manager event.
        // Should have a duration equal to how long it takes for the delay to finish.
        StringEvents.schedule(
                offsetName,
                delay,
                0,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                offset = System.currentTimeMillis();
                            }
                        };
                    }
                },
                false
        );

        // Schedule the recorder manager.
        // Duration = Interval.
        // Delay = Delay.
        // This event should repeat - it will be cancelled by another event.
        long execs = duration / interval;
        long ptr = 1;
        while (ptr != execs) {
            StringEvents.schedule(
                    recorderName,
                    delay + (ptr * interval),
                    0,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    long currentOffset = System.currentTimeMillis() - offset;
                                    controller1.recordState(currentOffset);
                                    controller2.recordState(currentOffset);
                                }
                            };
                        }
                    },
                    false
            );
            ptr++;
        }

        // Schedule the file saver.
        StringEvents.schedule(
                saveName,
                duration + delay + 100,
                0,
                new Timed() {
                    @Override
                    public Runnable close() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                saveRecording(name);
                            }
                        };
                    }
                },
                false
        );
    }

    /**
     * Load a recording and play it back.
     *
     * @param name the name of the recording. This is used in saving the recording locally
     *             in order to enable loading the recording and eventually playing it back.
     */
    public void play(String name) {
        loadRecording(name);

        for (final HashMap.Entry<Long, ControllerState> entry : controller1.getRecording().getRecording().entrySet()) {
            StringEvents.schedule(
                    playbackName,
                    entry.getKey(),
                    0,
                    new Timed() {
                        @Override
                        public Runnable open() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    controller1.playState(entry.getKey());
                                    controller2.playState(entry.getKey());
                                }
                            };
                        }
                    },
                    false
            );
        }
    }

    private void saveRecording(String name) {
        HashMap<Long, ControllerState> r1 = controller1.getRecording().getRecording();
        HashMap<Long, ControllerState> r2 = controller2.getRecording().getRecording();

        String json1 = GsonSave.getJsonString(r1);
        String json2 = GsonSave.getJsonString(r2);

        RwInterface.writer.write(
                wPrf(name),
                c1f,
                json1
        );
        RwInterface.writer.write(
                wPrf(name),
                c2f,
                json2
        );
    }

    private void loadRecording(String name) {
        controller1.getRecording().setRecording(
                GsonLoad.getHashMap(
                        RwInterface.reader.read(
                                wPrf(name),
                                c1f
                        )
                )
        );
        controller2.getRecording().setRecording(
                GsonLoad.getHashMap(
                        RwInterface.reader.read(
                                wPrf(name),
                                c2f
                        )
                )
        );
    }

    private String wPrf(String name) {
        return wSep("recordings") + wSep(name);
    }

    private String wSep(String name) {
        return name + ".";
    }
}
