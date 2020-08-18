package solvers.informed.heuristics;

import board.GameBoard;
import states.GameState;
import states.Node;

import java.awt.*;
import java.util.List;

public class ManhattanDistanceHeuristic implements Heuristic {
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
                    distance = getManhattanDistanceDistance(targetCellPosition, boxPosition);
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

    protected int getManhattanDistanceDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    protected Boolean validateBoxPositionsCheck(List<Point> boxPositions, Point targetCellPosition, GameState state) {
        return true;
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
