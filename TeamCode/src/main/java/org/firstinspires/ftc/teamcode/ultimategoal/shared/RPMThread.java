/****
 * Made by Tejas Mehta
 * Made on Thursday, February 11, 2021
 * File Name: RPMThread
 * Package: org.firstinspires.ftc.teamcode.ultimategoal.shared*/
package org.firstinspires.ftc.teamcode.ultimategoal.shared;

import com.qualcomm.robotcore.hardware.DcMotor;
import org._11253.lib.utils.telem.Telemetry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RPMThread extends Thread {
    DcMotor encoder;
    boolean running = false;
    double prevPos;
    double rpm = 0;
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    public RPMThread(DcMotor encoder) {
        this.encoder = encoder;
        prevPos = encoder.getCurrentPosition();
        running = true;
    }

    @Override
    public void run() {
        exec.scheduleAtFixedRate(
            new Runnable() {
                @Override
                public void run() {
                    double cpos = encoder.getCurrentPosition();
                    rpm = (((cpos - prevPos) / 500.0) * 1000.0 * 60.0)/(28.0 * 3/2);
//            Telemetry.addData("RPM", "RPM", ":", String.valueOf(rpm));
                    prevPos = cpos;
                }
            }, 0, 500, TimeUnit.MILLISECONDS);
//        while (running) {
//
//        }
    }

    public double getRPM() {
        return rpm;
    }

    public void stopThread() {
        running = false;
        exec.shutdown();
    }
}
