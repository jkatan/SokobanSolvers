import java.util.Stack;

public class DFSSolver extends UninformedSearchSolver{

    public DFSSolver() {
        frontierNodes = new Stack<>();
    }

    @Override
    protected void addFrontierNode(Node node) {
        ((Stack<Node>)frontierNodes).push(node);
    }

    @Override
    protected Node extractFrontierNode() {
        return ((Stack<Node>)frontierNodes).pop();
    }
}
