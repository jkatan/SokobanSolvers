package solvers.uninformed;

import java.util.*;

import solvers.SearchSolver;
import states.Direction;
import states.GameState;
import states.Node;

public abstract class UninformedSearchSolver extends SearchSolver {
    public static final int IGNORE_DEPTH = -1;

    public void solve(GameState initialState, int maxDepth) {
        long startTime = System.currentTimeMillis();
        Node solutionNode = startSearch(initialState, maxDepth);
        long endTime = System.currentTimeMillis();
        if (solutionNode != null) {
            long elapsedTime = endTime - startTime;
            printSolutionInformation(solutionNode, elapsedTime, maxDepth);
        } else {
            System.out.println("Solution not found...");
        }
    }

    public Node startSearch(GameState initialState, int maxDepth) {
        Node root = new Node(initialState);
        addFrontierNode(root);
        while(!frontierNodes.isEmpty()) {
            Node currentNode = extractFrontierNode();
            int currentDepth = currentNode.getDepth();
            if(currentNode.getState().isGameWon()) {
                return currentNode;
            } else if (maxDepth == IGNORE_DEPTH || currentDepth < maxDepth){
                int successorDepth = currentDepth + 1;
                addNodeSuccessor(currentNode, Direction.RIGHT, successorDepth);
                addNodeSuccessor(currentNode, Direction.LEFT, successorDepth);
                addNodeSuccessor(currentNode, Direction.UP, successorDepth);
                addNodeSuccessor(currentNode, Direction.DOWN, successorDepth);
            }
        }
        return null;
    }

    private void addNodeSuccessor(Node currentNode, Direction newAction, int successorDepth) {
        final GameState currentState = currentNode.getState();
        GameState newState = new GameState(currentState);
        newState.movePlayer(newAction);
        if (validateSuccessorExpansion(currentState, newState, successorDepth)) {
            Node newSuccessor = new Node(currentNode, newState, newAction, successorDepth);
            currentNode.addChild(newSuccessor);
            addFrontierNode(newSuccessor);
            visitedStatesDepth.put(newState.getGameBoard().hashCode(), successorDepth);
            expandedNodesAmount += 1;
        }
    }

    protected void printSolutionInformation(Node solutionNode, long elapsedTime, int maxDepth) {
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
        System.out.println("Maximum depth used: " + (maxDepth == IGNORE_DEPTH ? "none specified" : maxDepth));
        System.out.println("Solution depth: " + (actionsToWin.size() - 1));
        System.out.println("Expanded nodes: " + expandedNodesAmount);
        System.out.println("Frontier nodes: " + frontierNodes.size());
        System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
    }

    protected void resetState() {
        expandedNodesAmount = 0;
        frontierNodes.clear();
    }

    protected Boolean validateSuccessorExpansion(GameState currentState, GameState newState, int successorDepth) {
        return !visitedStatesDepth.containsKey(newState.getGameBoard().hashCode()) && !newState.isGameStuck()
                && !newState.getPlayerPosition().equals(currentState.getPlayerPosition());
    }

    protected abstract void addFrontierNode(Node node);
    protected abstract Node extractFrontierNode();
}
