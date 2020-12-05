package org._11253.lib.ints;

import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

/**
 * Bacon, lettuce, tomato manager.
 */
public abstract class BLTManager implements Manager, Sbm {
    public boolean isActive = true;

    private Timed asyncOverride = new Timed() {
        @Override
        public Runnable open() {
            return new Runnable() {
                @Override
                public void run() {
                    canBeEnabled = false;
                    canBeDisabled = false;
                }
            };
        }

        @Override
        public Runnable close() {
            return new Runnable() {
                @Override
                public void run() {
                    canBeEnabled = true;
                    canBeDisabled = true;
                }
            };
        }
    };

    private Timed asyncMode(final boolean state) {
        final boolean previousState = isEnabled;
        return new Timed() {
            @Override
            public Runnable open() {
                return new Runnable() {
                    @Override
                    public void run() {
                        isEnabled = state;
                    }
                };
            }

            @Override
            public Runnable close() {
                return new Runnable() {
                    @Override
                    public void run() {
                        isEnabled = previousState;
                    }
                };
            }
        };
    }

    private boolean isEnabled = false;
    private boolean canBeEnabled = true;
    private boolean canBeDisabled = true;

    public final void tick() {
        if (enableCondition() && canBeEnabled) isEnabled = true;
        if (disableCondition() && canBeDisabled) isEnabled = false;
    }

    public final void override(boolean state,
                         int duration) {
        // Schedule an event to enable and disable the canBeEnabled
        // and canBeDisabled portions of this beautiful bit of code.
        StringEvents.schedule(
                "Manager_override",
                duration,
                0,
                asyncOverride,
                false
        );
        // Schedule an event to set the state of the manager.
        // This is independent of the first event, but runs in the
        // same "thread" thingy.
        StringEvents.schedule(
                "Manager_override",
                duration,
                0,
                asyncMode(state),
                false
        );
    }
}
