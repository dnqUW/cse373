package graphs.minspantrees;

import disjointsets.DisjointSets;
// import disjointsets.QuickFindDisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;
// import graphs.shortestpaths.ShortestPath;

import java.util.ArrayList;
// import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
// import java.util.Objects;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        // return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        List<E> edges = new ArrayList<>(graph.allEdges());
        List<V> vertices = new ArrayList<>(graph.allVertices());
        DisjointSets<V> disjointSets = createDisjointSets();
        Set<E> finalMST = new HashSet<>();
        if (vertices.size() <= 1) {
            return new MinimumSpanningTree.Success<>();
        }
        for (V vertex : vertices) {
            disjointSets.makeSet(vertex); // processed vertex
        }
        edges.sort(Comparator.comparingDouble(E::weight));
        for (E edge : edges) {
            V u = edge.from();
            V v = edge.to();
            if (disjointSets.union(u, v)) {
                finalMST.add(edge);
                if (finalMST.size() == vertices.size() - 1) {
                    return new MinimumSpanningTree.Success<>(finalMST);
                }
            }
        }
        return new MinimumSpanningTree.Failure<>();
    }
}
