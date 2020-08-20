# SokobanSolvers
A set of search algorithms implementations to find a solution for a given initial state in the game Sokoban. These search algorithms include:
* DFS
* BFS
* IDDFS
* A*
* Global greedy search
* IDA*

## Configurations
Configurations can be found in `src/config.properties`. There you can swap out the different search methods and depth analysis, as well as select which map you'd like to play on.

## Running the game
In this directory, you can find a *build.xml* file for running *ant* tasks. In the terminal, run:
* `ant compile` to compile the program.
* `ant jar` to generate a .jar of the program.
* `ant run` to execute the .jar using the properties specified in the `src/config.properties` file.
* `ant clean` to delete build folder.
* You can also execute the .jar that comes with the repository, using `ant run` or `java -jar SokobanSolver.jar`
