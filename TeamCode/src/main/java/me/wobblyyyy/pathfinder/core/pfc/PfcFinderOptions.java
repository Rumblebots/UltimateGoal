package me.wobblyyyy.pathfinder.core.pfc;

import org.xguzm.pathfinding.Heuristic;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import org.xguzm.pathfinding.grid.finders.JumpPointFinder;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;
import org.xguzm.pathfinding.grid.heuristics.ManhattanDistance;

/**
 * Contains options used for setting up our instanced {@link ThetaStarGridFinder}.
 *
 * @author Colin Robertson
 */
public class PfcFinderOptions {
    /**
     * Options for a grid finder.
     */
    public static final GridFinderOptions options;

    /**
     * Whether or not diagonal movement is allowed within the grid.
     *
     * <p>
     * <b>Note:</b> This will be ignored in {@link JumpPointFinder}, as diagonal movement is required for it
     * <p>
     * <p>
     * Default value is true.
     */
    private static final boolean allowDiagonal = true;

    /**
     * When true, diagonal movement requires both neighbors to be open.
     * When false, diagonal movement can be achieved by having only one open neighbor
     * <p>
     * <p>
     * Example: To go from (1,1) to (2,2) when this is set to true, where (x) denotes a non walkable cell,
     * the following applies
     *
     * <pre>
     *                 Valid           Invalid
     *             +---+---+---+    +---+---+---+
     *             |   |   | 0 |    |   | x | 0 |
     *             +---+---+---+    +---+---+---+
     * when True   |   | 0 |   |    |   | 0 |   |
     *             +---+---+---+    +---+---+---+
     *             |   |   |   |    |   |   |   |
     *             +---+---+---+    +---+---+---+
     *
     *
     *             +---+---+---+
     *             |   | x | 0 |
     *             +---+---+---+
     * when false  |   | 0 |   |    none
     *             +---+---+---+
     *             |   |   |   |
     *             +---+---+---+
     * </pre>
     * <p>
     * If {@link #allowDiagonal} is false, this setting is ignored.
     * <p>
     * Default value is true.
     */
    private static final boolean dontCrossCorners = true;

    /**
     * A way to calculate the distance between two nodes on some form of
     * a navigation graph.
     */
    private static final Heuristic heuristic = new ManhattanDistance();

    /**
     * When false, (0,0) is located at the bottom left of the grid. When true, (0,0) is located
     * at the top left of the grid
     *
     * <p>
     * Default value is false.
     */
    private static final boolean isYDown = false;

    /**
     * The cost of moving one cell over the x or y axis.
     */
    private static final float orthogonalMovementCost = 1;

    /**
     * The cost of moving one cell over both the x and y axis.
     *
     * <p>
     * Determine the hypotenuse of a triangle with two sides with a side length of
     * one. This can be visualized as so:
     * <pre>
     *     a
     *   +-----/
     *   |    /
     *   |   /
     * b |  /   c
     *   | /
     *   |/
     *   /
     * a = 1.0;
     * b = 1.0;
     * c = sqrt((a^2)+(b^2));
     * </pre>
     * </p>
     */
    private static final float diagonalMovementCost = (float) Math.hypot(1, 1);

    static {
        // Set up the grid finder's options.
        options = new GridFinderOptions(
                allowDiagonal,
                dontCrossCorners,
                heuristic,
                isYDown,
                orthogonalMovementCost,
                diagonalMovementCost
        );
    }
}
