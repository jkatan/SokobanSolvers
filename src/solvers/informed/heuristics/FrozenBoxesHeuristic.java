package solvers.informed.heuristics;

import board.GameBoard;
import states.GameState;
import states.Node;

import java.awt.*;
import java.util.List;

/*Esta heuristica tiene en cuenta que no es necesario calcular las distancias a targets que ya estan ocupados
* por cajas que no se pueden mover*/
public class FrozenBoxesHeuristic extends ManhattanDistanceHeuristic {
    @Override
    protected Boolean validateBoxPositionsCheck(List<Point> boxPositions, Point targetCellPosition, GameState state) {
        return !boxPositions.contains(targetCellPosition) || !state.isBoxBlocked(targetCellPosition);
    }
}
