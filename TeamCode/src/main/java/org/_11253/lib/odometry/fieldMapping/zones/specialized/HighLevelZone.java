package org._11253.lib.odometry.fieldMapping.zones.specialized;

import org._11253.lib.odometry.fieldMapping.zones.Zone;

public abstract class HighLevelZone implements Zone {
    @Override
    public int getComponents() {
        return getParentShape().getComponentCount();
    }

    @Override
    public double getDriveSpeedMultiplier() {
        return 1.0;
    }

    @Override
    public int getZonePriority() {
        return 1;
    }
}
