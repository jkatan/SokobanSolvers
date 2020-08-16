import java.util.Stack;

public class DFSSolver extends UninformedSearchSolver{
    private final Boolean useDepthLimitedSearch;

    public DFSSolver(Boolean useDepthLimitedSearch) {
        frontierNodes = new Stack<>();
        this.useDepthLimitedSearch = useDepthLimitedSearch;
    }

    @Override
    protected void addFrontierNode(Node node) {
        ((Stack<Node>)frontierNodes).push(node);
    }

    @Override
    protected Node extractFrontierNode() {
        return ((Stack<Node>)frontierNodes).pop();
    }

    @Override
    protected Boolean validateSuccessorExpansion(GameState currentState, GameState newState, int successorDepth) {
        if (!useDepthLimitedSearch) {
            return super.validateSuccessorExpansion(currentState, newState, successorDepth);
        }

        return !newState.isGameStuck() && !newState.getPlayerPosition().equals(currentState.getPlayerPosition())
                && !(visitedStatesDepth.containsKey(newState.getGameBoard())
                && visitedStatesDepth.get(newState.getGameBoard()) < successorDepth);
    }
}
