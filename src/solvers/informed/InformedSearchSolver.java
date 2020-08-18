package solvers.informed;

import solvers.SearchSolver;
import solvers.informed.heuristics.Heuristic;
import states.Node;

public abstract class InformedSearchSolver extends SearchSolver {

    protected final Heuristic heuristic;

    public InformedSearchSolver(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    /* Calculates the heuristic for the state of the given node, and sets that value to the Node */
    protected abstract void applyHeuristic(Node node);
}
