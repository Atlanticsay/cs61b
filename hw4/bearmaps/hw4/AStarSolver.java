package bearmaps.hw4;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.*;

/**@author Atlantic (Y)
 * A* Tree Search (memory optimized A* algorithm):
 * Find the shortest path from the source to the goal. */

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private SolverOutcome outcome; // the signature of the status of the operated solver
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored; // The total number of priority queue dequeue operations
    private double explorationTime;
    private final double INF = Double.POSITIVE_INFINITY;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Vertex> PQ = new ArrayHeapMinPQ<>();
        Map<Vertex, Double> disTo = new HashMap(); // distance from the s to v
        Map<Vertex, Vertex> edgeTo = new HashMap(); // the edge: to v1 from v2
        HashSet<Vertex> markedV = new HashSet<>(); // mark the used vertex
        solution = new LinkedList<> ();

        disTo.put(start, 0.0);
        edgeTo.put(start, null);
        PQ.add(start, -input.estimatedDistanceToGoal(start, end));

        while (!PQ.isEmpty()) {
            Vertex v = PQ.removeSmallest();
            numStatesExplored += 1; // time of deque ++
            explorationTime = sw.elapsedTime();
            markedV.add(v);
//          this solution doesn't hold true for vertices accidentally with same weights
//          see the solutionHelper function for more
//            solution.add(v);

            // stop if we reach the goal
            if (v.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solution = solutionHelper(edgeTo, start, end);
                solutionWeight = disTo.get(end);
                return;
            }
            // stop if timeout
            if (explorationTime > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solutionWeight = 0;
                return;
            }
            // continue:
            // loop through the neighbors of v and do relaxation for the adjacent edges of v
            List<WeightedEdge<Vertex>> adjEdges = input.neighbors(v);
            for (WeightedEdge<Vertex> e : adjEdges) {
                Vertex fromV = e.from();
                Vertex toV = e.to();
                double w = e.weight();
                boolean flag = true;

                // Relaxation always fails on edges to visited (marked) vertices
                if (markedV.contains(toV)) {
                    continue;
                }
                // whether the toV is in the PQ
                if (!PQ.contains(toV)) {
                    disTo.put(toV, INF);
                    flag = false;
                }
                // relax vertex connected by e
                double disNew = disTo.get(fromV) + w;
                if (disNew < disTo.get(toV)) {
                    disTo.put(toV, disNew);
                    edgeTo.put(toV, fromV);
                    double priorityNew = disNew + input.estimatedDistanceToGoal(toV, end);
                    if (flag) {
                        // special neg PQ: the smaller the second arg, the lower the priority
                        PQ.changePriority(toV, - priorityNew);
                    } else {
                        PQ.add(toV, - priorityNew);
                    }
                } // if
            } // for
        } // while
        outcome = SolverOutcome.UNSOLVABLE;
        solutionWeight = 0;
        explorationTime = sw.elapsedTime();
    }

    /** @return the final status of the operated solver.
     * Should be SOLVED if the AStarSolver was able to complete all work in the time given.
     * UNSOLVABLE if the priority queue became empty.
     * TIMEOUT if the solver ran out of time.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /** @return a list of vertices corresponding to a solution.
     *  Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public List<Vertex> solution() {
        return solution;
    }

    /** Return the solution list using edgeTo
     * @param edgeTo represents the edge, map<toV1, fromV2>
     * @param start the source vertex
     * @param end the goal vertex */
    private List<Vertex> solutionHelper(Map<Vertex, Vertex> edgeTo, Vertex start, Vertex end) {
        LinkedList<Vertex> solution = new LinkedList<>();
        Vertex v = end;
        solution.add(v);
        if (start.equals(end)) {
            return solution;
        }
        do {
            v = edgeTo.get(v);
            solution.addFirst(v);
        } while (!v.equals(start));
         return solution;
    }

    /** @return the total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /** @return the total number of priority queue dequeue operations
     */
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    /** @return the total time spent in seconds by the constructor
     */
    @Override
    public double explorationTime() {
        return explorationTime;
    }

}
