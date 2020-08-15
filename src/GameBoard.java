import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameBoard {
    // actual map being played:
    private final List<List<Tile>> gameBoard;
    // where the target tiles are:
    private Set<Point> targetCellsPositions;
    // actual amount of cells filled
//    private int currentTargetCellsCompleted;
    private Set<Point> boxCellsPositions;
    // the player's position on the map:
    private Point playerPosition;

    public GameBoard(String configurationFilepath) throws IOException {
        this.gameBoard = new ArrayList<>();
        targetCellsPositions = new HashSet<>();
        boxCellsPositions = new HashSet<>();
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

    public GameBoard(List<List<Tile>> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public List<List<Tile>> getGameBoard() {
        return gameBoard;
    }

    public Set<Point> getTargetCellsPositions() {
        return targetCellsPositions;
    }

    public Point getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Point playerPosition) {
        this.playerPosition = playerPosition;
    }

//    public int getCurrentTargetCellsCompleted() {
//        return currentTargetCellsCompleted;
//    }
//
//    public void setCurrentTargetCellsCompleted(int currentTargetCellsCompleted) {
//        this.currentTargetCellsCompleted = currentTargetCellsCompleted;
//    }

    public Set<Point> getBoxCellsPositions() {
        return boxCellsPositions;
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

    private Tile processTileFromChar(int row, int col, Character character) {
        switch (character) {
            case '#':
                return Tile.WALL;
            case '.':
                targetCellsPositions.add(new Point(row, col));
                return Tile.FLOOR;
            case '$':
                boxCellsPositions.add(new Point(row, col));
                return Tile.BOX;
            case '@':
                playerPosition = new Point(row, col);
                return Tile.FLOOR;
        }

        return Tile.FLOOR;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        GameBoard gameBoard1 = (GameBoard) o;
//        return currentTargetCellsCompleted == gameBoard1.currentTargetCellsCompleted &&
//                gameBoard.equals(gameBoard1.gameBoard) &&
//                targetCellsPositions.equals(gameBoard1.targetCellsPositions) &&
//                playerPosition.equals(gameBoard1.playerPosition);
//    }
//
//    @Override
//    public int hashCode() {
//        // fixme: this doesn't take into account the position of the player or the boxes, just the amount of targets filled
//        return Objects.hash(gameBoard, targetCellsPositions, currentTargetCellsCompleted, playerPosition);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard1 = (GameBoard) o;
        return Objects.equals(gameBoard, gameBoard1.gameBoard) &&
                Objects.equals(targetCellsPositions, gameBoard1.targetCellsPositions) &&
                Objects.equals(boxCellsPositions, gameBoard1.boxCellsPositions) &&
                Objects.equals(playerPosition, gameBoard1.playerPosition);
    }

    @Override
    public int hashCode() {
        // fixme: this doesn't take into account the position of the player?
        return Objects.hash(gameBoard, targetCellsPositions, boxCellsPositions, playerPosition);
    }
}
