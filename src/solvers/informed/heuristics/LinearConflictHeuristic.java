package solvers.informed.heuristics;

import board.GameBoard;
import board.Tile;
import states.Direction;
import states.GameState;
import states.Node;

import java.awt.*;
import java.util.List;

/*Esta heuristica, en caso de haber caminos directos entre targetCell y una caja, tiene en cuenta
 si en dicho camino hay un obstaculo que haga que se tenga que desviar la caja*/
public class LinearConflictHeuristic extends ManhattanDistanceHeuristic {
    @Override
    public int apply(Node node) {
        GameBoard gameBoard = node.getState().getGameBoard();
        List<Point> boxPositions = gameBoard.getBoxPositions();
        int minDistance = 0;
        int distance = 0;
        int heuristicValue = 0;
        int currentIteration = 0;
        for (Point targetCellPosition : gameBoard.getTargetCellsPositions()) {
            if (validateBoxPositionsCheck(boxPositions, targetCellPosition, node.getState())) {
                for (Point boxPosition : boxPositions) {
                    distance = getManhattanDistanceDistance(targetCellPosition, boxPosition)
                            + calculateLinearConflictModifier(boxPosition, targetCellPosition, node.getState());
                    if (currentIteration == 0) {
                        minDistance = distance;
                    } else if (distance < minDistance) {
                        minDistance = distance;
                    }
                    currentIteration += 1;
                }

                heuristicValue += minDistance;
            }
        }

        return heuristicValue;
    }

    private int calculateLinearConflictModifier(Point boxPosition, Point targetCellPosition, GameState state) {
        int modifier = 0;

        if (boxPosition.equals(targetCellPosition)) {
            return modifier;
        }

        if (boxPosition.x == targetCellPosition.x) {
            if (targetCellPosition.y > boxPosition.y) {
                modifier = checkForBlockedPath(boxPosition, targetCellPosition, state.getGameBoard(), Direction.RIGHT);
            } else if (targetCellPosition.y < boxPosition.y) {
                modifier = checkForBlockedPath(boxPosition, targetCellPosition, state.getGameBoard(), Direction.LEFT);
            }
        } else if (boxPosition.y == targetCellPosition.y) {
            if (targetCellPosition.x > boxPosition.x) {
                modifier = checkForBlockedPath(boxPosition, targetCellPosition, state.getGameBoard(), Direction.UP);
            } else {
                modifier = checkForBlockedPath(boxPosition, targetCellPosition, state.getGameBoard(), Direction.DOWN);
            }
        }

        return modifier;
    }

    /*Esta funcion hace un 'sweep' desde la caja hacia el target en linea recta para ver si hay algo en el medio*/
    private int checkForBlockedPath(Point boxPosition, Point targetCellPosition, GameBoard board, Direction direction) {
        int step = 1;
        Point boxSweepPosition = new Point(boxPosition);
        while (!boxSweepPosition.equals(targetCellPosition)) {
            switch (direction) {
                case UP:
                    boxSweepPosition.x += step;
                    break;
                case DOWN:
                    boxSweepPosition.x -= step;
                    break;
                case RIGHT:
                    boxSweepPosition.y += 1;
                    break;
                case LEFT:
                    boxSweepPosition.y -= 1;
                    break;
                case NONE:
                    break;
            }

            Tile currentTile = board.getGameBoard().get(boxSweepPosition.x).get(boxSweepPosition.y);
            if (currentTile.equals(Tile.WALL) || currentTile.equals(Tile.BOX)) {
                //Si hay un bloqueo en el camino recto de la caja al target, la caja tendra que moverse al menos
                //2 veces para rodear al bloqueo
                return 2;
            }
        }

        return 0;
    }
}
