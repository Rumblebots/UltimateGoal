package org._11253.lib.odometry.fieldMapping.types;

import org._11253.lib.odometry.fieldMapping.zones.Zone;

public class ZoneCollision {
    private Zone a;
    private Zone b;

    public ZoneCollision(Zone a, Zone b) {
        this.a = a;
        this.b = b;
    }

    public static boolean isSameCollision(ZoneCollision ca,
                                          ZoneCollision cb) {
        Zone caa = ca.getA(); String caan = caa.getName();
        Zone cab = ca.getB(); String cabn = cab.getName();
        Zone cba = cb.getA(); String cban = cba.getName();
        Zone cbb = cb.getB(); String cbbn = cbb.getName();

        // ca_ca = first  combination of CA zone names
        // ca_cb = second combination of CA zone names
        // cb_ca = first  combination of CB zone names
        // cb_cb = second combination of CB zone names
        // The following combinations need to be checked...
        // CA vs CB, of course. This can be split up like:
        // 1st CA vs 1st CB and 2nd CB
        // 2nd CA vs 1st CB and 2nd CB
        String ca_ca = caan + cabn; String ca_cb = cabn + caan;
        String cb_ca = cban + cbbn; String cb_cb = cbbn + cban;

        // 1st CA vs 1st CB
        // 1st CA vs 2nd CB
        // 2nd CA vs 1st CB
        // 2nd CA vs 2nd CB
        if (ca_ca.equals(cb_ca)) return true;
        else if (ca_ca.equals(cb_cb)) return true;
        else if (ca_cb.equals(cb_ca)) return true;
        else if (ca_cb.equals(cb_cb)) return true;
        else return false;
    }

    public Zone getA() {
        return a;
    }

    public Zone getB() {
        return b;
    }
}
