package states;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    private final Node parent;
    private final List<Node> children;
    private final GameState gameState;
    private final Direction actionTaken;
    private final int depth;
    private int accumulatedCost;
    private int stateHeuristicValue;

    public Node(Node parent, GameState gameState, Direction newAction, int depth) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.gameState = gameState;
        this.actionTaken = newAction;
        this.depth = depth;
        accumulatedCost = 0;
        stateHeuristicValue = 0;
    }

    public Node(GameState gameState) {
        actionTaken = Direction.NONE;
        children = new ArrayList<>();
        this.gameState = gameState;
        parent = null;
        depth = 0;
        accumulatedCost = 0;
        stateHeuristicValue = 0;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public GameState getState() {
        return gameState;
    }

    public Direction getActionTaken() {
        return actionTaken;
    }

    public int getDepth() { return depth; }

    public void setAccumulatedCost(int accumulatedCost) {
        this.accumulatedCost = accumulatedCost;
    }

    public int getAccumulatedCost() {
        return accumulatedCost;
    }

    public int getStateHeuristicValue() {
        return stateHeuristicValue;
    }

    public void setStateHeuristicValue(int stateHeuristicValue) {
        this.stateHeuristicValue = stateHeuristicValue;
    }

    @Override
    public int compareTo(Node otherNode) {
        int compare = (stateHeuristicValue + accumulatedCost) - (otherNode.stateHeuristicValue + otherNode.accumulatedCost);
        if (compare == 0) {
            return stateHeuristicValue - otherNode.stateHeuristicValue;
        }
        return compare;
    }
}
