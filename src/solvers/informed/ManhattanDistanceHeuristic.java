package solvers.informed;

import board.GameBoard;
import states.Node;

import java.awt.*;
import java.util.List;

public class ManhattanDistanceHeuristic implements Heuristic {
    @Override
    public int apply(Node node) {
        GameBoard gameBoard = node.getState().getGameBoard();
        List<Point> boxPositions = gameBoard.getBoxPositions();
        int currentBox = 0;
        int currentDistance = 0;
        Point currentBoxPosition;
        for (Point targetCellPosition : gameBoard.getTargetCellsPositions()) {
            currentBoxPosition = boxPositions.get(currentBox);
            currentDistance += Math.abs(targetCellPosition.x - currentBoxPosition.x)
                    + Math.abs(targetCellPosition.y - currentBoxPosition.y);
            currentBox += 1;
        }

        return currentDistance;
    }
}
