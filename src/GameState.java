import java.awt.Point;

public class GameState {
    private final GameBoard gameBoard;
    private Point playerPosition;
    private boolean stuckBox;

    public GameState(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        stuckBox = false;
    }

    public Point movePlayer(Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, playerPosition);
        System.out.println(targetPosition);
        Tile targetTile = gameBoard.getGameBoard().get(targetPosition.x).get(targetPosition.y);
        System.out.println(targetTile);
        switch (targetTile) {
            case FLOOR:
                playerPosition = targetPosition;
                break;
            case WALL:
                //can't move
                break;
            case BOX:
                if (canMoveBox(targetPosition, moveDirection)) {
                    playerPosition = targetPosition;
                    moveBox(targetPosition, moveDirection);
                } else {
                    System.out.println("Can't move box");
                }
        }

        System.out.println(gameBoard.getCurrentTargetCellsCompleted());

        return playerPosition;
    }

    public boolean isGameWon() {
        return gameBoard.getCurrentTargetCellsCompleted() == gameBoard.getTargetCellsPositions().size();
    }

    public boolean isGameStuck() {
        return stuckBox;
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

    private boolean canMoveBox(Point boxPosition, Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, boxPosition);
        Tile targetTile = gameBoard.getGameBoard().get(targetPosition.x).get(targetPosition.y);
        return targetTile == Tile.FLOOR;
    }

    private void moveBox(Point boxPosition, Direction moveDirection) {
        int currentTargetCellsCompleted = gameBoard.getCurrentTargetCellsCompleted();
        Point targetPosition = getTargetPositionFromDirection(moveDirection, boxPosition);
        gameBoard.getGameBoard().get(boxPosition.x).set(boxPosition.y, Tile.FLOOR);
        gameBoard.getGameBoard().get(targetPosition.x).set(targetPosition.y, Tile.BOX);

        if (gameBoard.getTargetCellsPositions().contains(boxPosition)) {
            gameBoard.setCurrentTargetCellsCompleted(currentTargetCellsCompleted - 1);
        }

        if (gameBoard.getTargetCellsPositions().contains(targetPosition)) {
            gameBoard.setCurrentTargetCellsCompleted(currentTargetCellsCompleted + 1);
        }

        if (isBoxBlocked(targetPosition)) {
            stuckBox = true;
        }
    }

    private boolean isBoxBlocked(Point boxPosition) {
        return !((gameBoard.getGameBoard().get(boxPosition.x).get(boxPosition.y + 1) == Tile.FLOOR
                && gameBoard.getGameBoard().get(boxPosition.x).get(boxPosition.y - 1) == Tile.FLOOR)
                || ((gameBoard.getGameBoard().get(boxPosition.x + 1).get(boxPosition.y) == Tile.FLOOR)
                && gameBoard.getGameBoard().get(boxPosition.x - 1).get(boxPosition.y) == Tile.FLOOR));
    }
}
