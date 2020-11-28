package me.wobblyyyy.pathfinder.fieldMapping.types;

import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;

/**
 * A collision between two zones.
 *
 * <p>
 * These aren't ordered pairs or whatever. A collision with A and
 * B is the same collision as any other collision with A and B. If
 * you throw a C in there, things get messy or something, but I
 * don't know, or care.
 * </p>
 */
public class ZoneCollision {
    /**
     * The "first" of the two zones.
     */
    private Zone a;

    /**
     * The "second" of the two zones.
     */
    private Zone b;

    /**
     * Create a new zone collision.
     *
     * @param a the first of the two zones
     * @param b the second of the two zones
     */
    public ZoneCollision(Zone a, Zone b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Check if two collisions are between the same two zones.
     *
     * @param ca the first collision
     * @param cb the second collision
     * @return if both of the collisions are between the same zones
     */
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

    /**
     * Get the first zone.
     *
     * @return the first zone.
     */
    public Zone getA() {
        return a;
    }

    /**
     * Get the second zone.
     *
     * @return the second zone.
     */
    public Zone getB() {
        return b;
    }
}
