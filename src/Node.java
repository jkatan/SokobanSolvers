import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Node parent;
    private List<Node> children;
    private final GameState gameState;
    private final Direction actionTaken;

    public Node(Node parent, GameState gameState, Direction newAction) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.gameState = gameState;
        this.actionTaken = newAction;
    }

    public Node(GameState gameState) {
        this.actionTaken = Direction.NONE;
        children = new ArrayList<>();
        this.gameState = gameState;
        parent = null;
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
}
