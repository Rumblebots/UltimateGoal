/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 10:58 PM
 * Part of the _1125c library
 *
 * **
 *
 * Permission is granted, free of charge, to any person obtaining
 * a copy of this software and / or any of it's related source code or
 * documentation ("Software") to copy, merge, modify, publish,
 * distribute, sublicense, and / or sell copies of Software.
 *
 * All Software included is provided in an "as is" state, without any
 * type or form of warranty. The Authors and Copyright Holders of this
 * piece of software, documentation, or source code waive all
 * responsibility and shall not be liable for any claim, damages, or
 * other forms of liability, regardless of the form it may take.
 *
 * Any form of re-distribution of Software is required to have this same
 * copyright notice included in any source files or forms of documentation
 * which have stemmed or branched off of the original Software.
 *
 * **
 *
 */

package org._11253.lib.op;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org._11253.lib.Global;
import org._11253.lib.utils.async.event.StringEvents;
import org._11253.lib.utils.jrep.ListWrapper;
import org._11253.lib.utils.math.Math;
import org._11253.lib.utils.telem.Telemetry;

import java.util.ArrayList;

/**
 * Template LinearOpMode which extends the default (FTC) LinearOpMode.
 * <p>
 * This should be used by user op modes as a very core basis.
 * </p>
 * TODO try to see if there's a better method of fucking with the lists -
 * maybe see about making them objects so the code here is smaller and there's
 * no internal functions?
 *
 * @author Colin Robertson
 */
public class Template extends LinearOpMode {
    public int executionTime = 0;
    public double avgExecTime = 1;

    public Telemetry telem;
    public StringEvents events = new StringEvents();

    /**
     * List of Runnables to be run before the start of the
     * operation mode. This is before waitForStart() finishes;
     */
    public ListWrapper<Runnable> beforeStart = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * List of Runnables to be run after waitForStart() finishes.
     */
    public ListWrapper<Runnable> onStart = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * List of Runnables to run at the start of the loop.
     */
    public ListWrapper<Runnable> onStartRun = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * List of Runnables to run as the main portion of the loop.
     */
    public ListWrapper<Runnable> run = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * List of Runnables to run after the loop finishes.
     */
    public ListWrapper<Runnable> onFinishRun = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * List of Runnables to be run after the loop finishes being, well,
     * looped.
     */
    public ListWrapper<Runnable> onFinish = new ListWrapper<>(new ArrayList<Runnable>());

    /**
     * Run all of the runnables contained in a list.
     *
     * @param list List<'Runnable'> which should be run.
     */
    public final void runList(ListWrapper<Runnable> list) {
        for (Runnable runnable : list.list) {
            runnable.run();
        }
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void fBeforeStart() {
        runList(beforeStart);
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void fOnStart() {
        runList(onStart);
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void fOnStartRun() {
        runList(onStartRun);
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void fRun() {
        runList(run);
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void fOnFinishRun() {
        runList(onFinishRun);
    }

    /**
     * Internal function, just don't mess with it and you're good.
     */
    private void onFinish() {
        runList(onFinish);
    }

    /**
     * Should be used to append stuff to lists.
     * <p>
     * The user should override this in their custom implementation
     * of the template OpMode. This runs right before waitForStart(),
     * so they have access to all the init-ed stuff. This isn't required,
     * but if you'd like to utilize the lists provided, you should probably
     * use it. I'm guessing you want to use the lists. Either that, or
     * you have no idea what you're doing. Or you're just reading the
     * documentation for some reason - honestly, I got no idea, but hey,
     * it's not my problem to deal with, now is it?
     * </p>
     */
    @Deprecated
    public void initOp() {

    }

    /**
     * Base / template code that every op mode uses.
     * <p>
     * This code should NOT be overridden by the user, as everything they could possibly need to do
     * can be done externally.
     * </p>
     * <p>
     * Note that this framework was primarily designed with TeleOp in mind, so it makes sense
     * why this might seem a little awkward to use for auton. However, it's still pretty easy to do so -
     * all you have to do is EXACTLY what you'd do otherwise. If you can't understand how to do that,
     * I'm not entirely sure how or why you're reading this in the first place.
     * </p>
     */
    @Override
    public final void runOpMode() {
        Global.setHwMap(hardwareMap);
        Global.setTelem(telemetry);
        telem = new Telemetry();

        fBeforeStart();

        waitForStart();

        Telemetry.scheduleTelemetryUpdater();

        fOnStart();

        while (opModeIsActive()) {
            executionTime = (int) System.currentTimeMillis();
            fOnStartRun();

            fRun();

            fOnFinishRun();
            executionTime = (int) System.currentTimeMillis() - executionTime;
            avgExecTime = Math.average(executionTime, avgExecTime);
            Telemetry.addData("_1125c_AVG_EXEC_TIME",
                    "Average execution time",
                    (Math.round(avgExecTime * 100) / 100) + "ms");

            StringEvents.tick();
        }

        fOnFinishRun();
    }
}
