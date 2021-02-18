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
    double prevPos;
    double speed = 0;
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    public ShooterThread(DcMotor encoder) {
        this.encoder = encoder;
        prevPos = encoder.getCurrentPosition();
    }

    @Override
    public void run() {
        exec.scheduleAtFixedRate(
            new Runnable() {
                @Override
                public void run() {
                    double cPos = encoder.getCurrentPosition();
                    double countsToRotations = 28.0 * 3.0/2.0; // cpr * gear ratio
                    double rotations = (cPos - prevPos)/countsToRotations; // Get rotation count
                    double rps = (rotations / 250.0) * 1000.0; // Get rotations per second
                    double angularVelocity = rps * 2 * Math.PI;
                    speed = angularVelocity * 2/3.281; //Angular velocity (m/sec)
                    // Angle: 45
                    // Horizontal from center (x dist): 144.5
                    // Vertical to bottom (offset): 258.823
                    // Goal to top 33 in, 35.5 to middle of top goal
                    // Goal to mid 21 in, 27 to middle of middle goal
                    prevPos = cPos;
                }
            }, 0, 250, TimeUnit.MILLISECONDS);
    }

    public double getSpeed() {
        return speed;
    }

    public void stopThread() {
        exec.shutdown();
    }
}
