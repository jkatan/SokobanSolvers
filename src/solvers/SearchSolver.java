package solvers;

import states.GameState;
import states.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SearchSolver {
    protected Collection<Node> frontierNodes;
    protected int expandedNodesAmount;
    protected Map<Integer, Integer> visitedStatesDepth;

    protected SearchSolver() {
        frontierNodes = new ArrayList<>();
        expandedNodesAmount = 0;
        visitedStatesDepth = new HashMap<>();
    }

    public abstract void solve(GameState initialState, int maxDepth);

    protected abstract Node startSearch(GameState initialState, int maxDepth);
}
