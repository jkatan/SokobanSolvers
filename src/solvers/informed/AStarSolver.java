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

    protected int calculateCost(Node from, Node to) {
        if (from == null) {
            return 0;
        }

        /*El costo de mover una caja fuera de un target cell es al menos 2, porque luego va a haber que mover esa misma caja o alguna otra
         a ese target*/
        if (to.getState().getGameBoard().getCurrentTargetCellsCompleted() < from.getState().getGameBoard().getCurrentTargetCellsCompleted()) {
            return 2;
        }

        /*Actualmente estamos validando de no movernos a un estado stuck, pero en caso de que no lo validemos, este costo nos asegura de que la solucion
         no vaya hacia estos nodos atascados con costo alto*/
        if (to.getState().isGameStuck()) {
            return 100000;
        }

        return 1;
    }
}
