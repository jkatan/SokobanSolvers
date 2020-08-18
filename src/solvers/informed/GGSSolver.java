package solvers.informed;

import solvers.informed.heuristics.Heuristic;
import states.Node;

import java.util.PriorityQueue;

/**
 * Global Greedy Search Solver
 */
public class GGSSolver extends InformedSearchSolver {

    public GGSSolver(Heuristic heuristic) {
        super(heuristic);
        frontierNodes = new PriorityQueue<>();
    }

    @Override
    protected void addFrontierNode(Node node) {
        applyHeuristic(node);
        frontierNodes.add(node);
    }

    @Override
    protected Node extractFrontierNode() {
        return ((PriorityQueue<Node>)frontierNodes).poll();
    }

    @Override
    protected void applyHeuristic(Node node) {
        int heuristicValue = heuristic.apply(node);
        node.setStateHeuristicValue(heuristicValue);
    }
}
