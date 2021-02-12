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

public class ShooterThread extends Thread {
    DcMotor encoder;
    boolean running = false;
    double prevPos;
    double rps = 0;
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    public ShooterThread(DcMotor encoder) {
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
                    rps = (((cpos - prevPos) / 500.0) * 1000.0)/(28.0 * 3/2);
                    double angularVelocity = rps * 2 * Math.PI;
                    double linearVelocity = angularVelocity * 1.5; //TODO change radius
//            Telemetry.addData("RPM", "RPM", ":", String.valueOf(rpm));
                    prevPos = cpos;
                }
            }, 0, 500, TimeUnit.MILLISECONDS);
//        while (running) {
//
//        }
    }

    public double getSpeed() {
        return rps; // TODO get to generated speed
    }

    public void stopThread() {
        running = false;
        exec.shutdown();
    }
}
