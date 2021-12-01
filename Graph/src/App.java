import java.util.Collection;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        Graph<Integer> graph = new Graph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 1);

        System.out.println(graph.numEdges());
        System.out.println(graph.edgeExists(2,1));
        System.out.println(graph.degree(2));
        System.out.println(graph.toString());
    }
}
