package solvers.informed;

import solvers.informed.heuristics.Heuristic;
import states.Node;

import java.util.PriorityQueue;

/**
 * A-star Solver
 */
public class AStarSolver extends InformedSearchSolver {

    public AStarSolver(Heuristic heuristic) {
        super(heuristic);
        frontierNodes = new PriorityQueue<>();
    }

    @Override
    protected void addFrontierNode(Node node) {
        applyHeuristic(node);
        int cost = calculateCost(node.getParent(), node);
        if (node.getParent() == null) {
            node.setAccumulatedCost(cost);
        } else {
            node.setAccumulatedCost(node.getParent().getAccumulatedCost() + cost);
        }

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
    //TODO
    //Calcula el costo de moverse de un nodo a otro. Actualmente en 1. Una vez que tengamos implementados los
    //algoritmos con este calculo de costo y heuristica naive, vemos como podemos mejorarlos. Pero al menos
    //tener algo que funciona primero.
    protected int calculateCost(Node from, Node to) {
        if (from == null) {
            return 0;
        }

        return 1;
    }
}
