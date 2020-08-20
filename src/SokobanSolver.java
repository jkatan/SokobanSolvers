import board.GameBoard;
import solvers.SearchSolver;
import solvers.informed.AStarSolver;
import solvers.informed.GGSSolver;
import solvers.informed.IDAStarSolver;
import solvers.informed.heuristics.FrozenBoxesHeuristic;
import solvers.informed.heuristics.Heuristic;
import solvers.informed.heuristics.LinearConflictHeuristic;
import solvers.informed.heuristics.ManhattanDistanceHeuristic;
import solvers.uninformed.BFSSolver;
import solvers.uninformed.DFSSolver;
import solvers.uninformed.IDDFSSolver;
import states.Direction;
import states.GameState;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

public class SokobanSolver {
    public static final int IGNORE_DEPTH = -1;

    public static void main(String[] args) throws IOException, InterruptedException {
        String sokobanConfigPath = "src/config.properties";
        final Properties properties = new Properties();
        properties.load(new FileInputStream(sokobanConfigPath));
        GameBoard gameBoard = new GameBoard("src/board/resources/maps/" + properties.getProperty("map"));
        GameState initialState = new GameState(gameBoard);

        SearchSolver searchSolver;
        int maxDepth = Integer.parseInt(properties.getProperty("depth"));
        Heuristic heuristicToUse = getHeuristicFromProperty(properties.getProperty("heuristic"));
        String methodToUse = properties.getProperty("methodType");
        System.out.println("Solver will use method: " + methodToUse);
        switch (methodToUse) {
            case "bfs":
                searchSolver = new BFSSolver();
                searchSolver.solve(initialState, maxDepth);
                break;
            case "dfs":
                searchSolver = new DFSSolver(maxDepth != IGNORE_DEPTH);
                searchSolver.solve(initialState, maxDepth);
                break;
            case "iddfs":
                searchSolver = new IDDFSSolver();
                ((IDDFSSolver) searchSolver).iddfsSolver(initialState, maxDepth);
                break;
            case "astar":
                searchSolver = new AStarSolver(heuristicToUse);
                searchSolver.solve(initialState, maxDepth);
                break;
            case "ggs":
                searchSolver = new GGSSolver(heuristicToUse);
                searchSolver.solve(initialState, maxDepth);
                break;
            case "idastar":
                searchSolver = new IDAStarSolver(heuristicToUse);
                searchSolver.solve(initialState, maxDepth);
                break;
            case "interactive":
                startInteractiveMode(gameBoard);
                break;
            default:
                System.out.println("Invalid argument, enter [bfs] [maxDepth], [dfs] [maxDepth], [iddfs] [iterations] or [interactive]");
                break;
        }
    }

    private static Heuristic getHeuristicFromProperty(String property) {
        Heuristic chosenHeuristic = null;
        switch (property) {
            case "manhattanDistance" :
                chosenHeuristic = new ManhattanDistanceHeuristic();
                break;
            case "linearConflicts" :
                chosenHeuristic = new LinearConflictHeuristic();
                break;
            case "frozenBoxes" :
                chosenHeuristic = new FrozenBoxesHeuristic();
                break;
        }
        return chosenHeuristic;
    }

    private static void startInteractiveMode(GameBoard gameBoard) {
        HashSet<Integer> gameBoardVisited = new HashSet<>();
        System.out.println(gameBoard.toString());

        Scanner inputReader = new Scanner(System.in);
        String input;
        while(!(input = inputReader.nextLine()).equals("exit")) {
            GameState gameState = new GameState(gameBoard);
            switch (input) {
                case "right": case "d":
                    gameState.movePlayer(Direction.RIGHT);
                    break;
                case "up": case "w":
                    gameState.movePlayer(Direction.UP);
                    break;
                case "left": case "a":
                    gameState.movePlayer(Direction.LEFT);
                    break;
                case "down": case "s":
                    gameState.movePlayer(Direction.DOWN);
                    break;
                default:
                    System.out.println("Wrong command. Accepted commands are " +
                            "'up', 'left', 'down', 'right' or 'w', 'a', 's', 'd'. You can also exit with 'exit'.\n");
            }
            System.out.println(gameBoard.toString());

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
