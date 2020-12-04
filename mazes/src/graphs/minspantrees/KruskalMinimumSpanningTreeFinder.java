package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;
import graphs.shortestpaths.ShortestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        // return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));
        List<V> vertices = new ArrayList<>(graph.allVertices());
        DisjointSets<V> disjointSets = createDisjointSets();
        Set<E> finalMST = new HashSet<>();

        // So if we get two islands, disconnected, or if one vertex is not conncetd to the MST
        // If check for failure if (any vertices are disconnected, fail)
        // As long as all vertices are connected, you can find an MST Success

        for (V vertex : vertices) {
            disjointSets.makeSet(vertex); // processed vertex
        }
        for (E edge : edges) {
            if (disjointSets.findSet(edge.from()) != disjointSets.findSet(edge.to())) {
                finalMST.add(edge);
                disjointSets.union(edge.from(), edge.to());
            }
            if (edges.size() == vertices.size() - 1) { // reduce the size of the list edges
                return new MinimumSpanningTree.Success<>(finalMST);
            }
        }


        // for (E edge : edges) {
        //     V u = edge.from();
        //     V v = edge.to();
        //     if (u == null && v == null) {
        //         return new MinimumSpanningTree.Failure<>();
        //     }
        //     int uMST = disjointSets.findSet(u);
        //     int vMST = disjointSets.findSet(v);
        //     if (uMST != vMST) {
        //         finalMST.add(edge);
        //         disjointSets.union(u, v);
        //     }
        // }

        // for (E edge : edges) {
        //     V u = edge.from();
        //     V v = edge.to();
        //     // finalMST.add(edge);
        //     if (disjointSets.findSet(u) != disjointSets.findSet(v)) {
        //
        //     }
        //     // edges.remove(edge);
        // }
        // if (edges.size() == vertices.size() - 1) { // reduce the size of the list edges
        //     return new MinimumSpanningTree.Success<>();
        // }
        return new MinimumSpanningTree.Failure<>();
    }
}
