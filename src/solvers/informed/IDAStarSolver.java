package solvers.informed;

import solvers.informed.heuristics.Heuristic;
import states.Direction;
import states.GameState;
import states.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Iterative Deepening A-star Solver
 */
public class IDAStarSolver extends AStarSolver {

    private final Set<Integer> excededThresholds;

    public IDAStarSolver(Heuristic heuristic) {
        super(heuristic);
        frontierNodes = new Stack<>();
        excededThresholds = new HashSet<>();
    }

    @Override
    public Node startSearch(GameState initialState, int threshold) throws InterruptedException {
        Node root = new Node(initialState);
        addFrontierNode(root);
        while(!frontierNodes.isEmpty()) {
            Node currentNode = extractFrontierNode();
            int currentCost = currentNode.getAccumulatedCost() + currentNode.getStateHeuristicValue();
            int currentDepth = currentNode.getDepth();
            if(currentNode.getState().isGameWon()) {
                return currentNode;
            } else if (currentCost <= threshold){
                int successorDepth = currentDepth + 1;
                addNodeSuccessor(currentNode, Direction.RIGHT, successorDepth);
                addNodeSuccessor(currentNode, Direction.LEFT, successorDepth);
                addNodeSuccessor(currentNode, Direction.UP, successorDepth);
                addNodeSuccessor(currentNode, Direction.DOWN, successorDepth);
            } else {
                excededThresholds.add(currentCost);
            }
        }

        return null;
    }

    @Override
    public void solve(GameState initialState, int maxDepth, String methodUsed) throws InterruptedException {
        int initialThreshold = heuristic.apply(new Node(initialState));
        int currentIteration = 1;
        long startTime = System.currentTimeMillis();
        System.out.println("Iteration number " + currentIteration);
        Node solutionNode = startSearch(initialState, initialThreshold);
        int minThreshold = Collections.min(excededThresholds);
        while (solutionNode == null) {
            currentIteration += 1;
            System.out.println("Iteration number " + currentIteration);
            solutionNode = startSearch(initialState, minThreshold);
            minThreshold = Collections.min(excededThresholds);
            if (solutionNode == null) {
                resetState();
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        printSolutionInformation(solutionNode, elapsedTime, currentIteration, "idastar");
    }

    @Override
    protected void addFrontierNode(Node node) {
        applyHeuristic(node);
        int cost = calculateCost(node.getParent(), node);
        if (node.getParent() == null) {
            node.setAccumulatedCost(cost);
        } else {
            node.setAccumulatedCost(node.getParent().getAccumulatedCost() + cost);
        }

        ((Stack<Node>)frontierNodes).push(node);
    }

    @Override
    protected Node extractFrontierNode() {
        return ((Stack<Node>)frontierNodes).pop();
    }

    @Override
    protected Boolean validateSuccessorExpansion(GameState currentState, GameState newState, int successorDepth) {
        return !newState.isGameStuck() && !newState.getPlayerPosition().equals(currentState.getPlayerPosition())
                && !(visitedStatesDepth.containsKey(newState.getGameBoard())
                && visitedStatesDepth.get(newState.getGameBoard()) < successorDepth);
    }

    @Override
    protected void resetState() {
        super.resetState();
        excededThresholds.clear();
    }
}
