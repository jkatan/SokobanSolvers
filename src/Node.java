import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Node parent;
    private final List<Node> children;
    private final GameState gameState;
    private final Direction actionTaken;
    private final int depth;

    public Node(Node parent, GameState gameState, Direction newAction, int depth) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.gameState = gameState;
        this.actionTaken = newAction;
        this.depth = depth;
    }

    public Node(GameState gameState) {
        actionTaken = Direction.NONE;
        children = new ArrayList<>();
        this.gameState = gameState;
        parent = null;
        depth = 0;
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
}
