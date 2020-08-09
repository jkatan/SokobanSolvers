import java.io.IOException;
import java.util.Scanner;

public class SokobanSolver {
    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        GameState gameState = new GameState(filePath);
        System.out.println(gameState);

        Scanner inputReader = new Scanner(System.in);
        String input;
        while(!(input = inputReader.nextLine()).equals("exit")) {
            if (input.equals("right")) {
                gameState.movePlayer(Direction.RIGHT);
            }
            if (input.equals("up")) {
                gameState.movePlayer(Direction.UP);
            }
            if (input.equals("left")) {
                gameState.movePlayer(Direction.LEFT);
            }
            if (input.equals("down")) {
                gameState.movePlayer(Direction.DOWN);
            }

            System.out.println(gameState);

            if (gameState.isGameWon()) {
                System.out.println("You won!");
                return;
            }

            if (gameState.isGameStuck()) {
                System.out.println("A box is stuck, cant keep playing");
                return;
            }
        }
    }
}
