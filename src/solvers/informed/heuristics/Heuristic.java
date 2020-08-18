package solvers.informed.heuristics;

import states.Node;

public interface Heuristic {
    int apply(Node node);
}
