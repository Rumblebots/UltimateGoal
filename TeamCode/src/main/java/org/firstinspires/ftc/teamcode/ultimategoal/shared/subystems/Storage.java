package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import org._11253.lib.utils.telem.Telemetry;

public class Storage {
    private static int count = 0;

    public static void increment() {
        if (count++ > 3) count = 3;
    }

    public static void decrement() {
        if (count-- < 0) count = 0;
    }

    public static int getCount() {
        return count;
    }

    public static boolean canShoot() {
        return count - 1 < 0;
    }

    public static boolean canIntake() {
        return count + 1 > 3;
    }

    public static void showCount() {
        Telemetry.addData(
                "Storage_ring_count",
                "Rings in storage",
                ":",
                String.valueOf(count)
        );
    }
}
