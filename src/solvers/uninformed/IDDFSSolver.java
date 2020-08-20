package solvers.uninformed;

import states.Node;
import states.GameState;

public class IDDFSSolver extends DFSSolver {

    public IDDFSSolver() {
        super(true);
    }

    public void iddfsSolver(GameState initialState, int iterations) throws InterruptedException {
        Node solution;
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= iterations; i++) {
            System.out.println("Iteration number " + i);
            solution = startSearch(initialState, i);
            if (solution != null) {
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                printSolutionInformation(solution, elapsedTime, iterations, "iddfs");
                return;
            }
            resetState();
        }
        System.out.println("No solution found");
    }
}