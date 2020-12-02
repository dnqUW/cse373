package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> spt =  new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();
        distTo.put(start, 0.0);
        ExtrinsicMinPQ<V> pq = new DoubleMapMinPQ<>();
        pq.add(start, 0);
        while (!pq.isEmpty()) {
            V u = pq.removeMin();
            for (E edge : graph.outgoingEdgesFrom(u)) {
                double oldDist;
                oldDist = distTo.getOrDefault(edge.to(), Double.POSITIVE_INFINITY);
                double newDist = distTo.get(u) + edge.weight();
                if (newDist < oldDist) {
                    distTo.put(edge.to(), newDist);
                    spt.put(edge.to(), edge);
                    if (pq.contains(edge.to())) {
                        pq.changePriority(edge.to(), newDist);
                    } else {
                        pq.add(edge.to(), newDist);
                    }
                }
            }
        }
        return spt;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        E edge = spt.get(end);
        if (spt.get(edge) == null) {
            return new ShortestPath.Failure<>();
        }
        if (start.equals(end)) {
            return new ShortestPath.SingleVertex<>(end);
        }

        List<E> list = new ArrayList<>(spt.size());
        while (edge.from() != null) {
            list.add(edge);
            edge = spt.get(edge.from());
        }
        return new ShortestPath.Success<>(list);
    }

}
