package org.firstinspires.ftc.teamcode.testCode.recording;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org._11253.lib.controllers.Controller;
import org._11253.lib.drives.Meccanum;
import org._11253.lib.playback.Playback;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

@TeleOp(name = "Record", group = "Test")
public class RecordOpMode extends Meccanum {
    private Playback playback;
    private boolean hasExec = false;

    public RecordOpMode() {
        onStart.add(
                new Runnable() {
                    @Override
                    public void run() {
                        _init();
                    }
                }
        );
        run.add(
                new Runnable() {
                    @Override
                    public void run() {
                        _loop();
                    }
                }
        );
    }

    private void _init() {
        playback = new Playback(
                controller1,
                controller2,
                this
        );
    }

    private void _loop() {
        if (!hasExec) {
            playback.record(
                    "demoRecording",
                    10000,
                    0,
                    50
            );
            StringEvents.schedule(
                    "demoRecordingPlayback",
                    11000,
                    0,
                    new Timed() {
                        @Override
                        public Runnable close() {
                            return new Runnable() {
                                @Override
                                public void run() {
                                    playback.play("demoRecording", 10000);
                                }
                            };
                        }
                    },
                    false
            );
            hasExec = true;
        }
    }
}
