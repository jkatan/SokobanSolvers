package board;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameBoard {
    // actual map being played:
    private final List<List<Tile>> gameBoard;
    // where the target tiles are:
    private final Set<Point> targetCellsPositions;
    // actual amount of cells filled
    private int currentTargetCellsCompleted;
    // the player's position on the map:
    private Point playerPosition;

    public GameBoard(String configurationFilepath) throws IOException {
        this.gameBoard = new ArrayList<>();
        targetCellsPositions = new HashSet<>();
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

    public GameBoard(GameBoard gameBoard) {
        targetCellsPositions = gameBoard.targetCellsPositions;
        currentTargetCellsCompleted = gameBoard.currentTargetCellsCompleted;
        playerPosition = new Point(gameBoard.playerPosition);
        this.gameBoard = new ArrayList<>();
        for (List<Tile> row : gameBoard.gameBoard) {
            this.gameBoard.add(new ArrayList<>(row));
        }
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

    public int getCurrentTargetCellsCompleted() {
        return currentTargetCellsCompleted;
    }

    public void setCurrentTargetCellsCompleted(int currentTargetCellsCompleted) {
        this.currentTargetCellsCompleted = currentTargetCellsCompleted;
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
                return Tile.BOX;
            case '@':
                playerPosition = new Point(row, col);
                return Tile.FLOOR;
        }

        return Tile.FLOOR;
    }

    public List<Point> getBoxPositions() {
        List<Point> boxPositions = new ArrayList<>();
        int rows = 0;
        int cols = 0;
        for (List<Tile> row : gameBoard) {
            for (Tile tile : row) {
                if (tile == Tile.BOX) {
                    boxPositions.add(new Point(rows, cols));
                }
                cols += 1;
            }
            cols = 0;
            rows += 1;
        }

        return boxPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard1 = (GameBoard) o;
        return currentTargetCellsCompleted == gameBoard1.currentTargetCellsCompleted &&
                gameBoard.equals(gameBoard1.gameBoard) &&
                targetCellsPositions.equals(gameBoard1.targetCellsPositions) &&
                playerPosition.equals(gameBoard1.playerPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameBoard, targetCellsPositions, currentTargetCellsCompleted, playerPosition, getBoxPositions());
    }
}
