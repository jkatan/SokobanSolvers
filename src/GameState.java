import java.awt.Point;

public class GameState {
    private final GameBoard gameBoard;
    private boolean stuckBox;

    public GameState(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        stuckBox = false;
    }

    public void movePlayer(Direction moveDirection) {
        Point targetPosition = getTargetPositionFromDirection(moveDirection, gameBoard.getPlayerPosition());
        System.out.println("Target position: " + targetPosition);
        Tile targetTile = gameBoard.getGameBoard().get(targetPosition.x).get(targetPosition.y);
        System.out.println("In the space there's a " + targetTile);
        switch (targetTile) {
            case FLOOR:
                gameBoard.setPlayerPosition(targetPosition);
                break;
            case WALL:
                // Can't move
                break;
            case BOX:
                if (canMoveBox(targetPosition, moveDirection)) {
                    moveBox(targetPosition, moveDirection);
                    gameBoard.setPlayerPosition(targetPosition);
                } else {
                    System.out.println("Can't move box");
                }
        }
//        System.out.println("Target cells filled: " + gameBoard.getCurrentTargetCellsCompleted());
    }

    public boolean isGameWon() {
        // todo: optimize
        return gameBoard.getBoxCellsPositions().equals(gameBoard.getTargetCellsPositions());
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
//        int currentTargetCellsCompleted = gameBoard.getCurrentTargetCellsCompleted();
        Point targetPosition = getTargetPositionFromDirection(moveDirection, boxPosition);
        gameBoard.getGameBoard().get(boxPosition.x).set(boxPosition.y, Tile.FLOOR);
        gameBoard.getGameBoard().get(targetPosition.x).set(targetPosition.y, Tile.BOX);

//        if (gameBoard.getTargetCellsPositions().contains(boxPosition)) {
//            gameBoard.setCurrentTargetCellsCompleted(currentTargetCellsCompleted - 1);
//        }
//
//        if (gameBoard.getTargetCellsPositions().contains(targetPosition)) {
//            gameBoard.setCurrentTargetCellsCompleted(currentTargetCellsCompleted + 1);
//        } else if (isBoxBlocked(targetPosition)) {
//            stuckBox = true;
//        }
        if (isBoxBlocked(targetPosition)) {
            stuckBox = true;
        }
    }

    private boolean isBoxBlocked(Point boxPosition) {
        return !((gameBoard.getGameBoard().get(boxPosition.x).get(boxPosition.y + 1) == Tile.FLOOR
                && gameBoard.getGameBoard().get(boxPosition.x).get(boxPosition.y - 1) == Tile.FLOOR)
                || ((gameBoard.getGameBoard().get(boxPosition.x + 1).get(boxPosition.y) == Tile.FLOOR)
                && gameBoard.getGameBoard().get(boxPosition.x - 1).get(boxPosition.y) == Tile.FLOOR))
                && (gameBoard.getGameBoard().get(boxPosition.x).get(boxPosition.y) == Tile.TARGET);
    }
}
