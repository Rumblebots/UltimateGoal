/****
 * Made by Tejas Mehta
 * Made on Tuesday, September 29, 2020
 * File Name: Auton
 * Package: org._11253.lib.op*/
package org._11253.lib.op;

import org._11253.lib.utils.telem.Telemetry;

public class Auton extends Template{
    public Auton() {
        addToBeforeStart();
        addToStart();
        addToOnFinihed();
    }

    /**
     * Add some telemtery to the before start execution
     * <p>
     * This will run before the program starts and will add some Telemetry
     * </p>
     */
    private void addToBeforeStart() {
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                Telemetry.addData("Autonomous", "Press play to start", "");
            }
        });
    }

    /**
     * Add a running status on the general telemetry (no loop)
     * <p>
     * Just adds some telemetry
     * </p>
     */
    private void addToStart() {
        onStart.add(new Runnable() {
            @Override
            public void run() {
                Telemetry.addData("Autonomous", "Running...", "");
            }
        });
    }

    /**
     * Add a finished status on the general telemetry (no loop)
     * <p>
     * Just adds some extra telemetry
     * </p>
     */
    private void addToOnFinihed() {
        onFinish.add(new Runnable() {
            @Override
            public void run() {
                Telemetry.addData("Autonomous", "Finished!", "");
            }
        });
    }
}
