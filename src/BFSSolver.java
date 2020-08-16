import java.util.LinkedList;

public class BFSSolver extends UninformedSearchSolver {

    public BFSSolver() {
        frontierNodes = new LinkedList<>();
    }

    @Override
    protected void addFrontierNode(Node node) {
        frontierNodes.add(node);
    }

    @Override
    protected Node extractFrontierNode() {
        return ((LinkedList<Node>)frontierNodes).poll();
    }
}
