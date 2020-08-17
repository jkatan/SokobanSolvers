package solvers.informed;

import solvers.SearchSolver;
import states.GameState;
import states.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class InformedSearchSolver extends SearchSolver {
    protected List<String> heuristics;

    public InformedSearchSolver() {
        this.heuristics = new LinkedList<>();
        // todo: add default heuristics?
    }

    public InformedSearchSolver(String[] heuristics) {
        this.heuristics = new LinkedList<>(Arrays.asList(heuristics));
    }

    public void solve(GameState initialState, int maxDepth) {}

    protected Node startSearch(GameState initialState, int maxDepth) {
        return null;
    }

    protected int applyHeuristics() {
        int cost = 0;
        for (String s: heuristics) {
            cost += applyHeuristic(s);
        }
        return cost;
    }

    private int applyHeuristic(String heuristic) {
        return 0;
    }
}
