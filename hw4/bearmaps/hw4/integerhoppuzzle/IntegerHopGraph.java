package bearmaps.hw4.integerhoppuzzle;

import bearmaps.hw4.AStarGraph;
import bearmaps.hw4.WeightedEdge;
import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.*;

/**
 * The Integer Hop puzzle implemented as a graph.
 * Created by hug & Atlantic (Y).
 */
public class IntegerHopGraph implements AStarGraph<Integer> {

    @Override
    public List<WeightedEdge<Integer>> neighbors(Integer v) {
        ArrayList<WeightedEdge<Integer>> neighbors = new ArrayList<>();
        neighbors.add(new WeightedEdge<>(v, v * v, 10));
        neighbors.add(new WeightedEdge<>(v, v * 2, 5));
        neighbors.add(new WeightedEdge<>(v, v / 2, 5));
        neighbors.add(new WeightedEdge<>(v, v - 1, 1));
        neighbors.add(new WeightedEdge<>(v, v + 1, 1));
        return neighbors;
    }

    @Override
    public double estimatedDistanceToGoal(Integer s, Integer goal) {
        // possibly fun challenge: Try to find an admissible heuristic that
        // speeds up your search. This is tough!

        double add1 = Math.abs(goal - (s + 1.0));
        double sub1 = Math.abs(goal - (s - 1.0));
        double mult2 = Math.abs(goal - s * 2.0);
        double div2 = Math.abs(goal - s / 2.0);
        double square = Math.abs(goal - s * s);

        HashMap<String, Double> keysWeight = new HashMap<>();
        keysWeight.put("add1", 1.0);
        keysWeight.put("sub1", 1.0);
        keysWeight.put("mult2", 5.0);
        keysWeight.put("div2", 5.0);
        keysWeight.put("square", 10.0);

        ArrayHeapMinPQ<String> candidates = new ArrayHeapMinPQ<>();
        candidates.add("add1", -add1);
        candidates.add("sub1", -sub1);
        candidates.add("mult2", -mult2);
        candidates.add("div2", -div2);
        candidates.add("square", -square);
        String keyH =  candidates.getSmallest();
        return keysWeight.get(keyH);

//        Double[] arrH = new Double[]{add1, sub1, mult2, div2, square};
//        standardize(arrH, 10);

//        standardize(arrH, keysWeight.get(keyH));
//        HashMap<String, Double> keysHeuristic  = new HashMap<>();
//        keysHeuristic.put("add1", arrH[0]);
//        keysHeuristic.put("sub1", arrH[1]);
//        keysHeuristic.put("mult2", arrH[2]);
//        keysHeuristic.put("div2", arrH[3]);
//        keysHeuristic.put("square", arrH[4]);
//        return keysHeuristic.get(keyH);


    }

    /** standardize the array in to the range of (0, rg) */
    private Double[] standardize(Double[] arrH, double rg) {
        double sum = 0.0;
        for (double x : arrH) {
            sum += x;
        }
        double mean = sum / arrH.length;

        double sse = 0.0;
        for (double x :arrH) {
            sse += (x - mean) * (x - mean);
        }
        double std = Math.sqrt(sse) / arrH.length;

        for (int i = 0; i < arrH.length; i++) {
            arrH[i] = rg * (arrH[i] - mean) / std;
        }
        return arrH;
    }
}
