package org.firstinspires.ftc.teamcode.ultimategoal.shared.subystems;

import org._11253.lib.utils.telem.Telemetry;

public class Storage {
    private int count = 0;

    public void increment() {
        if (count++ > 3) count = 3;
    }

    public void decrement() {
        if (count-- < 0) count = 0;
    }

    public int getCount() {
        return count;
    }

    public boolean canShoot() {
        return count - 1 < 0;
    }

    public boolean canIntake() {
        return count + 1 > 3;
    }

    public void showCount() {
        Telemetry.addData(
                "Storage_ring_count",
                "Rings in storage",
                ":",
                String.valueOf(count)
        );
    }
}
