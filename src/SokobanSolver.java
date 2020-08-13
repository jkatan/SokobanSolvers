import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class SokobanSolver {
    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        GameBoard gameBoard = new GameBoard(filePath);
        HashSet<Integer> gameBoardVisited = new HashSet<>();
        System.out.println(gameBoard);

        Scanner inputReader = new Scanner(System.in);
        String input;
        while(!(input = inputReader.nextLine()).equals("exit")) {
            GameState gameState = new GameState(gameBoard);
            Point newPlayerPosition = gameBoard.getPlayerPosition();
            switch (input) {
                case "right": case "d":
                    newPlayerPosition = gameState.movePlayer(Direction.RIGHT);
                    break;
                case "up": case "w":
                    newPlayerPosition = gameState.movePlayer(Direction.UP);
                    break;
                case "left": case "a":
                    newPlayerPosition = gameState.movePlayer(Direction.LEFT);
                    break;
                case "down": case "s":
                    newPlayerPosition = gameState.movePlayer(Direction.DOWN);
                    break;
                default:
                    System.out.println("Wrong command. Accepted commands are " +
                            "'up', 'left', 'down', 'right' or 'w', 'a', 's', 'd'. You can also exit with 'exit'.\n");
            }
            System.out.println(gameBoard.toString(newPlayerPosition));

            if (gameBoardVisited.contains(gameBoard.hashCode())) {
                System.out.println("You've already visited this state! No need to continue down this path again...\n");
            }

            if (gameState.isGameWon()) {
                System.out.println("You won!");
                return;
            }

            if (gameState.isGameStuck()) {
                System.out.println("A box is stuck, can't keep playing");
                return;
            }
            gameBoardVisited.add(gameBoard.hashCode());
        }
    }
}
