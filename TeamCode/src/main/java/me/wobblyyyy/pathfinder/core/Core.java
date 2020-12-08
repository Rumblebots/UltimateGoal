package me.wobblyyyy.pathfinder.core;

import me.wobblyyyy.pathfinder.core.pfc.PfcManager;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;

/**
 * An interwoven implementation of Pathfinder's field-mapping and
 * PathfindingCore's mathematical approach to finding the quickest
 * possible to path from point A to point B - literally.
 *
 * @author Colin Robertson
 */
public class Core {
    private Map map;
    private Odometry odometry;
    private PfcManager pfcManager;
    private ZoneGenerator zoneGenerator;

    public Core(Map map,
                Odometry odometry) {
        this.map = map;
        this.odometry = odometry;
        zoneGenerator = new ZoneGenerator(
                new ArrayList<>(map.fieldZones.list),
                0,
                0
        );
        GridCell[][] cells = MapCellConvertor.getCells(
                zoneGenerator.getZones()
        );
        pfcManager = new PfcManager(cells);
    }

    public ArrayList<Coordinate<Double>> getPathFromPoint(Coordinate<Double> start,
                                                          Coordinate<Double> end) {
        return pfcManager.finder.getCoordinatePath(
                start,
                end
        );
    }

    public ArrayList<Coordinate<Double>> getPath(Coordinate<Double> target) {
        return pfcManager.finder.getCoordinatePath(
                odometry.getPosition().getCoordinate(),
                target
        );
    }
}
