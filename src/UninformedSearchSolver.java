import java.util.*;

public abstract class UninformedSearchSolver {

    protected Collection<Node> frontierNodes;
    private final Set<GameBoard> visitedStates;
    private int expandedNodesAmount = 0;

    protected UninformedSearchSolver() {
        frontierNodes = new ArrayList<>();
        visitedStates = new HashSet<>();
    }

    public void solve(GameState initialState) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Node solutionNode = startSearch(initialState);
        long endTime = System.currentTimeMillis();
        if (solutionNode != null) {
            long elapsedTime = endTime - startTime;
            printSolutionInformation(solutionNode, elapsedTime);
        } else {
            System.out.println("Solution not found...");
        }
    }

    public Node startSearch(GameState initialState) throws InterruptedException {
        Node root = new Node(initialState);
        addFrontierNode(root);

        while(!frontierNodes.isEmpty()) {
            Node currentNode = extractFrontierNode();
            if(currentNode.getState().isGameWon()) {
                return currentNode;
            } else {
                addNodeSuccessor(currentNode, Direction.RIGHT);
                addNodeSuccessor(currentNode, Direction.LEFT);
                addNodeSuccessor(currentNode, Direction.UP);
                addNodeSuccessor(currentNode, Direction.DOWN);
            }
        }

        return null;
    }

    private void addNodeSuccessor(Node currentNode, Direction newAction) {
        final GameState currentState = currentNode.getState();
        GameState newState = new GameState(currentState);
        newState.movePlayer(newAction);
        if (!visitedStates.contains(newState.getGameBoard()) && !newState.isGameStuck() && !newState.getPlayerPosition().equals(currentState.getPlayerPosition())) {
            Node newSuccessor = new Node(currentNode, newState, newAction);
            currentNode.addChild(newSuccessor);
            addFrontierNode(newSuccessor);
            visitedStates.add(newState.getGameBoard());
            expandedNodesAmount += 1;
        }
    }

    private void printSolutionInformation(Node solutionNode, long elapsedTime) {
        System.out.println("Game won");
        ArrayList<Direction> actionsToWin = new ArrayList<>();
        Node current = solutionNode;
        // Here we iterate the tree bottom-up, checking the actions taken to win
        while (current != null) {
            actionsToWin.add(current.getActionTaken());
            current = current.getParent();
        }
        Collections.reverse(actionsToWin);
        System.out.println("[SOLUTION] Actions taken to win:");
        System.out.println(actionsToWin);
        System.out.println("Solution depth: " + actionsToWin.size());
        System.out.println("Expanded nodes: " + expandedNodesAmount);
        System.out.println("Frontier nodes: " + frontierNodes.size());
        System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
    }

    protected abstract void addFrontierNode(Node node);
    protected abstract Node extractFrontierNode();
}
