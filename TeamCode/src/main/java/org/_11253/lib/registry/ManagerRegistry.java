package org._11253.lib.registry;

import org._11253.lib.ints.BLTManager;
import org._11253.lib.utils.jrep.ListWrapper;

public class ManagerRegistry {
    public static ListWrapper<BLTManager> registry;

    public static void register(BLTManager... managers) {
        registry.add(managers);
    }

    public static void tickRegistry() {
        registry.forEach(
                new Runnable() {
                    @Override
                    public void run() {
                        registry.value.tick();
                    }
                }
        );
    }
}
