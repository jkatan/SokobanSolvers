package solvers.informed;

import states.Node;

public interface Heuristic {
    int apply(Node node);
}
