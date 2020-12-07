package mazes.logic.carvers;

// import graphs.BaseEdge;
import graphs.EdgeWithData;
// import graphs.Graph;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

// import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        // Hint: you'll probably need to include something like the following:
        List<EdgeWithData<Room, Wall>> edges = walls.stream()
            .map(wall -> new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), rand.nextDouble(), wall))
            .collect(Collectors.toList());
        Set<Wall> removed = new HashSet<>();
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> mst =
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        for (EdgeWithData<Room, Wall> wall : mst.edges()) {
            removed.add(wall.data());
        }
        return removed;
    }

}
