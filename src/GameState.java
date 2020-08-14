import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameState {
    private List<List<Tile>> gameBoard;
    private Point playerPosition;
    private Integer currentTargetCellsCompleted;
    private Set<Point> targetCellsPositions;
    private Boolean stuckBox;

    public GameState(String configurationFilepath) throws IOException {
        gameBoard = new ArrayList<List<Tile>>();
        currentTargetCellsCompleted = 0;
        targetCellsPositions = new HashSet<>();
        stuckBox = false;
        int row = 0;
        int col = 0;

        File configurationFile = new File(configurationFilepath);
        BufferedReader br = new BufferedReader(new FileReader(configurationFile));
        String line;
        while ((line = br.readLine()) != null) {
            gameBoard.add(new ArrayList<>());
            for (Character character : line.toCharArray()) {
                gameBoard.get(row).add(processTileFromChar(row, col, character));
                col += 1;
            }
            row += 1;
            col = 0;
        }
    }

    public GameState() {
        playerPosition = new Point(0,0);
    }

    public void movePlayer(Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, playerPosition);
        System.out.println(targetPosition);
        Tile targetTile = gameBoard.get(targetPosition.x).get(targetPosition.y);
        System.out.println(targetTile);
        switch (targetTile) {
            case FLOOR:
                playerPosition = targetPosition;
                break;
            case WALL:
                //cant move
                break;
            case BOX:
                if (canMoveBox(targetPosition, moveDirection)) {
                    playerPosition = targetPosition;
                    moveBox(targetPosition, moveDirection);
                } else {
                    System.out.println("Cant move box");
                }
        }

        System.out.println(currentTargetCellsCompleted);
    }

    public Boolean isGameWon() {
        return currentTargetCellsCompleted.equals(targetCellsPositions.size());
    }

    public Boolean isGameStuck() {
        return stuckBox;
    }

    public String toString() {
        StringBuilder boardToString = new StringBuilder();
        int row = 0;
        int col = 0;
        for (List<Tile> boardRow : gameBoard) {
            for (Tile tile : boardRow) {
                switch (tile) {
                    case FLOOR:
                        Point tilePosition = new Point(row, col);
                        if (playerPosition.equals(tilePosition)) {
                            boardToString.append('@');
                        } else if (targetCellsPositions.contains(tilePosition)) {
                            boardToString.append('.');
                        } else {
                            boardToString.append(' ');
                        }
                        break;
                    case WALL:
                        boardToString.append('#');
                        break;
                    case BOX:
                        boardToString.append('$');
                        break;
                }
                col += 1;
            }
            row += 1;
            col = 0;
            boardToString.append('\n');
        }

        return boardToString.toString();
    }

    private Point getTargetPositionFromDirection(Direction moveDirection, Point currentPosition) {

        Point targetPosition = new Point(currentPosition);

        switch (moveDirection) {
            case RIGHT:
                targetPosition.y += 1;
                break;
            case LEFT:
                targetPosition.y -= 1;
                break;
            case DOWN:
                targetPosition.x += 1;
                break;
            case UP:
                targetPosition.x -= 1;
                break;
        }

        return targetPosition;
    }

    private Boolean canMoveBox(Point boxPosition, Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, boxPosition);
        Tile targetTile = gameBoard.get(targetPosition.x).get(targetPosition.y);
        return targetTile == Tile.FLOOR;
    }

    private void moveBox(Point boxPosition, Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, boxPosition);
        gameBoard.get(boxPosition.x).set(boxPosition.y, Tile.FLOOR);
        gameBoard.get(targetPosition.x).set(targetPosition.y, Tile.BOX);

        if (targetCellsPositions.contains(boxPosition)) {
            currentTargetCellsCompleted -= 1;
        }

        if (targetCellsPositions.contains(targetPosition)) {
            currentTargetCellsCompleted += 1;
        } else if (isBoxBlocked(targetPosition)) {
            stuckBox = true;
        }
    }

    private Tile processTileFromChar(int row, int col, Character character) {
        switch (character) {
            case '#':
                return Tile.WALL;
            case '.':
                targetCellsPositions.add(new Point(row, col));
                return Tile.FLOOR;
            case '$':
                return Tile.BOX;
            case '@':
                playerPosition = new Point(row, col);
                return Tile.FLOOR;
        }

        return Tile.FLOOR;
    }

    private Boolean isBoxBlocked(Point boxPosition) {
        return !((gameBoard.get(boxPosition.x).get(boxPosition.y + 1) == Tile.FLOOR
                && gameBoard.get(boxPosition.x).get(boxPosition.y - 1) == Tile.FLOOR)
                || ((gameBoard.get(boxPosition.x + 1).get(boxPosition.y) == Tile.FLOOR)
                && gameBoard.get(boxPosition.x - 1).get(boxPosition.y) == Tile.FLOOR));
    }
}
