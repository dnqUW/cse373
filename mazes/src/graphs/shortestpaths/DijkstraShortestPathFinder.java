package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        DoubleMapMinPQ<V> pq = new DoubleMapMinPQ<>();
        pq.add(start, 0);
        while (!pq.isEmpty()) {
            V u = pq.removeMin();
            if (Objects.equals(u, end)) {
                return spt;
            }
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
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        E edge = spt.get(end);
        List<E> list = new ArrayList<>();
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }
        while (spt.get(end) != null) {
            list.add(spt.get(end));
            end = spt.get(end).from();
        }
        Collections.reverse(list);
        return new ShortestPath.Success<>(list);
    }

}
