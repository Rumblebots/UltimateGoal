package org._11253.lib.odometry.fieldMapping.zones.specialized;

import org._11253.lib.odometry.fieldMapping.zones.Zone;

/**
 * An abstract zone designed to condense some potential code.
 *
 * <p>
 * This is in no way needed - all it does is make it a little
 * bit easier to write clean and condensed code. Normally, anything
 * that implements zone would need to have a grand total of... a lot
 * of different methods in it. However, using HighLevelZone (and it's
 * children, RectangleZone and CircleZone) reduce the number of
 * required methods by a significant amount, hopefully saving you
 * anywhere in the vicinity of 20-30 SLOC.
 * </p>
 *
 * <p>
 * ****************************************************************
 * *** read me? plz? maybe? cool. thank you :)                  ***
 * ****************************************************************
 * Confused about what in the absolute hell is going on here? Fear not!
 * Just go to the Zone interface which this class implements - there,
 * you'll meet new people, see new places, and, of course, JavaDocs galore.
 * I have a pretty strong feeling that's the only part you care about anyways,
 * but oh well.
 * {@link Zone}
 * </p>
 */
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

    @Override
    public boolean isField() {
        return true;
    }

    @Override
    public boolean isCollidableWithField() {
        return false;
    }
}
