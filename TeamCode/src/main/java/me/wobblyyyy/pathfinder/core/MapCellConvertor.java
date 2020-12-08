package me.wobblyyyy.pathfinder.core;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;

/**
 * Convert coordinates and zones to a two-dimensional array that's compatible
 * with PathfindingCore and its native math.
 *
 * @author Colin Robertson
 */
public class MapCellConvertor {
    public static GridCell[][] getCells(ArrayList<Zone> zones) {
        GridCell[][] cells = new GridCell[144][144];

        pass1(
                zones,
                cells
        );
//        pass2(
//                zones,
//                cells
//        );

        return cells;
    }

    /*
     * First pass:
     * Fill in the entire GridCell 2d array.
     */
    private static void pass1(ArrayList<Zone> zones,
                              GridCell[][] cells) {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                int currentRow = row + 1;
                int currentCol = col + 1;

                Coordinate<Double> coordinate = generateCoordinate(
                        currentCol,
                        currentRow
                );

                boolean result = checkCoordinate(zones, coordinate);

                cells[row][col] = new GridCell(
                        currentCol,
                        currentRow,
                        result
                );
            }
        }

    }

    /*
     * Second pass:
     * Fix the Y positions of the entire GridCell 2d array.
     */
    private static void pass2(ArrayList<Zone> zones,
                              GridCell[][] cells) {
        int originalHeight = cells.length - 1;

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                int newY = originalHeight - row;

                cells[row][col].setY(newY);
            }
        }

    }

    private static Coordinate<Double> generateCoordinate(int x,
                                                         int y) {
        return new Coordinate<Double>(
                x + 0.0,
                y + 0.0
        );
    }

    private static boolean checkCoordinate(ArrayList<Zone> zones,
                                           Coordinate<Double> coordinate) {
        for (Zone zone : zones) {
//            if (zone.getParentShape().isCollidableExterior() ||
//                    zone.getParentShape().isCollidableInterior()) {
                if (zone.isPointInZone(coordinate)) return false;
//            }
        }

        return true;
    }
}
