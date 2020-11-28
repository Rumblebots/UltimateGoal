package org._11253.lib.odometry.fieldMapping.frames;

import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.types.ZoneCollision;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A group of any shape drawn on the playing field, along with
 * their zones.
 *
 * <p>
 * This is useful for doing collision detection or predicting collisions.
 * New frames can be drawn with different positions in order to attempt to
 * see whether or not a certain movement is possible with/without a
 * collision. This is especially useful in path-finding; being able to
 * predict what collisions may occur where and how to prevent them changes
 * the path to be a path which is actually successful.
 * </p>
 * <p>
 * Frames are designed to be very lightweight, so as not to waste any processor
 * time which would be available otherwise. It's also worth noting that once a
 * frame is created (it must be created with all of the needed information) the
 * values shouldn't be changed. As it's name ("Frame") would suggest, frames are
 * split-second snapshots. If you want a constantly updating picture, create
 * multiple frames. It's my suggestion that frames are calculated asynchronously
 * to generate a moving picture and when absolutely necessary (think pathfinding).
 * </p>
 */
public class Frame {
    /**
     * A map, containing all of the zones and their names.
     *
     * <p>
     * String names are determined with the Zone interface's
     * getName() function. Although this could be an ArrayList,
     * doing this as a map instead makes it a fair bit easier
     * to get zones based on their names.
     * </p>
     * <p>
     * The most common zone names, which will probably be inside
     * every single one of these HashMaps, are...
     * <pre>
     * 1. Field (the playing field itself)
     * 2. Robot (the movable robot's zone)
     * </pre>
     * </p>
     */
    public HashMap<String, Zone> zones;

    /**
     * The robot's zone.
     *
     * <p>
     * The zone itself is immobile and needs to be re-created
     * every time you'd like to move. Remember, frames must also
     * be re-constructed every time you'd like to change the position
     * of something.
     * </p>
     */
    public Zone robotZone;

    /**
     * A list which contains all of the detected collisions.
     *
     * <p>
     * This should have a value set once the constructor finishes
     * executing, meaning the frame is entirely analyzed upon it's
     * initialization.
     * </p>
     */
    public ArrayList<ZoneCollision> collisions;

    /**
     * A list of potential zone collisions that should be checked.
     *
     * <p>
     * This is designed to be used internally. I'm not entirely sure
     * what uses you could possibly find for a list of potential
     * collisions that will be checked in the future, but you do you.
     * </p>
     */
    public ArrayList<ZoneCollision> collisionsToBeChecked;

    /**
     * A list of any collisions which have to do with the robot.
     *
     * <p>
     * These are ONLY the collisions that involve any zone named
     * "2dRobot". If you name your robot anything other than that,
     * collisions won't be detected here. Sucks to suck, I guess.
     * </p>
     */
    public ArrayList<ZoneCollision> robotCollisions;

    /**
     * Constructor - sets up the entire frame.
     *
     * <p>
     * Frames, as their name would suggest, are single split-second
     * snapshots of what's going on on the field, or at least whatever
     * the robot perceives to be going on on the field. This means that
     * frames can not and should not be updated - this is quite literally
     * analogous to pausing a YouTube video on a specific frame and only
     * looking at that frame.
     * </p>
     *
     * @param zones all of the zones on the frame (including the robot)
     */
    public Frame(ArrayList<Zone> zones) {
        for (Zone z : zones) {
            this.zones.put(
                    z.getName(),
                    z
            );
            if (z.getName().equals("2dRobot")) {
                robotZone = z;
            }
        }
        checkCollisions();
    }

    /**
     * Check to see if the robot is colliding with anything anywhere.
     *
     * <p>
     * We always know what the robot's zone's name will be, so we can
     * simply fetch the robot zone and use collision-checking to see
     * if it's in contact with any other zone.
     * </p>
     */
    public void checkRobotCollisions() {
        for (ZoneCollision zc : collisions) {
            if (zc.getA().getName() == "2dRobot" ||
                    zc.getB().getName() == "2dRobot") {
                robotCollisions.add(zc);
            }
        }
    }

    /**
     * A method to call other methods to check collisions.
     *
     * <p>
     * I'm assuming this will be used near entirely nowhere but
     * other portions of this library, but you never know.
     * </p>
     */
    public void checkCollisions() {
        determineCollisionsToCheck();
        checkEveryCollision();
        checkRobotCollisions();
    }

    /**
     * Determine, based on the HashMap of all of the zones, which zones
     * could possibly be intercepted.
     *
     * <p>
     * This is likely in need of some serious optimization. It currently
     * just checks every potential combination, which is quite time-consuming.
     * Thankfully for us, Java is a rather fast programming language and FTC
     * fields are relatively minimalistic in the sense they don't have many
     * objects which we need to render onto our virtual field.
     * </p>
     */
    public void determineCollisionsToCheck() {
        ArrayList<Zone> collidable = new ArrayList<>();
        for (HashMap.Entry<String, Zone> entry : zones.entrySet()) {
            Zone zone = entry.getValue();
            Shape shape = zone.getParentShape();
            boolean canCollide = shape.isCollidableExterior();
            if (canCollide) collidable.add(zone);
        }
        // Let's go over the following sets...
        // |------------|--------------------|
        // | Collidable | Combinations (a/b) |
        // |------------|--------------------|
        // | ABC        |                    |
        // | XYZ        |                    |
        // | RQS        |                    |
        // | JFK        |                    |
        // | NYC        |                    |
        // |------------|--------------------|
        // Going over the loop for the first time, we
        // get each of the following combinations:
        // ABC, XYZ (1)
        // ABC, RQS (2)
        // ABC, JFK (3)
        // ABC, NYC (4)
        // XYZ, RQS (5)
        // XYZ, JFK (6)
        // XYZ, NYC (7)
        // RQS, JFK (8)
        // RQS, NYC (9)
        // JFK, NYC (10)
        // That's a total of 10 combinations, so this
        // shouldn't take up too much processing power.
        // Hopefully. Maybe. Who knows. Whatever.
        for (Zone z1 : collidable) {
            for (Zone z2 : collidable) {
                ZoneCollision c = new ZoneCollision(z1, z2);
                for (ZoneCollision zc : collisionsToBeChecked) {
                    if (!ZoneCollision.isSameCollision(c, zc)) {
                        collisionsToBeChecked.add(c);
                    }
                }
            }
        }
    }

    /**
     * Get a list of every single zone which the robot is in.
     *
     * <p>
     * This only gets the zones which the center of the robot is in.
     * There will be another method which allows you to check all the
     * zones which any portion of the robot is in, but that's for another
     * day.
     * </p>
     *
     * @return an ArrayList of the zones the robot is in
     */
    public ArrayList<Zone> getRobotPositions() {
        ArrayList<Zone> zonesRobotIsIn = new ArrayList<>();
        for (HashMap.Entry<String, Zone> e : zones.entrySet()) {
            Zone z = e.getValue();
            Zone r = robotZone;
            // Check if the center of the robot (the origin or whatever)
            // is contained within a certain shape. If it is, the zone is
            // added to our array list before it's returned.
            if (z.isPointInZone(r.getParentShape().getCenterPoint())) {
                zonesRobotIsIn.add(z);
            }
        }
        return zonesRobotIsIn;
    }

    /**
     * Get the priorities of each position.
     *
     * @return the priorities of every zone the robot is in
     */
    public ArrayList<Integer> getRobotPositionsPriorities() {
        ArrayList<Integer> priorities = new ArrayList<>();
        for (Zone z : getRobotPositions()) {
            priorities.add(z.getZonePriority());
        }
        return priorities;
    }

    public void checkEveryCollision() {
        for (ZoneCollision zc : collisionsToBeChecked) {
            checkZoneCollision(zc.getA(), zc.getB());
        }
    }

    /**
     * Check for a collision between two zones.
     *
     * <p>
     * This works by determining if two shapes are intersecting. If those
     * two shapes are, in fact, intersecting, then they'll be added to a
     * running list of all of the zones where there is any intersection.
     * </p>
     *
     * @param zoneA the first zone
     * @param zoneB the second zone
     */
    public void checkZoneCollision(Zone zoneA, Zone zoneB) {
        // Check if they're both rectangles. Two rectangles makes collision
        // checking quite easy.
        Shape shapeA = zoneA.getParentShape();
        Shape shapeB = zoneB.getParentShape();
        // They have the same class. This could mean there's two circles,
        // two triangles, or two rectangles, but it's most likely two
        // rectangles. Regardless, we have to check anyways.
        if (shapeA.getClass() == shapeB.getClass()) {
            // They're both rectangles, we're good to go.
            if (shapeA.getClass() == Rectangle.class) {
                Rectangle a = (Rectangle) shapeA;
                Rectangle b = (Rectangle) shapeB;
                if (a.doesRectangleIntersect(b)) {
                    collisions.add(new ZoneCollision(zoneA, zoneB));
                }
            }
        }
    }
}
