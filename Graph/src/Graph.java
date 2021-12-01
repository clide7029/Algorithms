import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;



/**
 * An interface describing a Graph object. The graph is directed
 * 
 * @author sbenjamin
 */
public class Graph<V> implements GraphIfc<V>{

    protected Map<V, Set<V>> adjList;
    protected int edges;

    public <V> Graph() {

        adjList = new HashMap<>();
        edges = 0;
    }

    /**
     * Returns the number of vertices in the graph
     * 
     * @return The number of vertices in the graph
     */
    public int numVertices(){
        return adjList.size();
    }

    /**
     * Returns the number of edges in the graph
     * 
     * @return The number of edges in the graph
     */
    public int numEdges(){
        return edges;
    }

    /**
     * Removes all vertices from the graph
     */
    public void clear(){
        adjList.clear();
        edges = 0;
    }

    /**
     * Adds a vertex to the graph. This method has no effect if the vertex already
     * exists in the graph.
     * 
     * @param v The vertex to be added
     */
    public void addVertex(V v){
        if(!adjList.containsKey(v)){
            Set<V> set = new HashSet<V>();
            adjList.put(v, set);
        }
    }

    /**
     * Adds an edge between vertices u and v in the graph.
     * 
     * @param u A vertex in the graph
     * @param v A vertex in the graph
     * @throws IllegalArgumentException if either vertex does not occur in the
     *                                  graph.
     */
    public void addEdge(V u, V v){
        if(!(adjList.containsKey(u)) && adjList.containsKey(v)){
            throw new IllegalArgumentException();
        }
        adjList.get(u).add(v);
        edges += 1;
    }

    /**
     * Returns the set of all vertices in the graph.
     * 
     * @return A set containing all vertices in the graph
     */
    public Set<V> getVertices(){
        return adjList.keySet();
    }

    /**
     * Returns the neighbors of v in the graph. A neighbor is a vertex that is
     * connected to v by an edge. If the graph is directed, this returns the
     * vertices u for which an edge (v, u) exists.
     * 
     * @param v An existing node in the graph
     * @return All neighbors of v in the graph.
     * @throws IllegalArgumentException if the vertex does not occur in the graph
     */
    public Collection<V> getNeighbors(V v){
        if(!adjList.containsKey(v)){
            throw new IllegalArgumentException();
        }
        return adjList.get(v);
    }

    /**
     * Determines whether the given vertex is already contained in the graph. The
     * comparison is based on the <code>equals()</code> method in the class V.
     * 
     * @param v The vertex to be tested.
     * @return True if v exists in the graph, false otherwise.
     */
    public boolean containsVertex(V v){
        return adjList.containsKey(v);
    }

    /**
     * Determines whether an edge exists between two vertices. In a directed graph,
     * this returns true only if the edge starts at v and ends at u.
     * 
     * @param v A node in the graph
     * @param u A node in the graph
     * @return True if an edge exists between the two vertices
     * @throws IllegalArgumentException if either vertex does not occur in the graph
     */
    public boolean edgeExists(V v, V u){
        if(!(adjList.containsKey(v) && adjList.containsKey(u))){
            throw new IllegalArgumentException();
        }

        return adjList.get(v).contains(u);
    }

    /**
     * Returns the degree of the vertex. In a directed graph, this returns the
     * outdegree of the vertex.
     * 
     * @param v A vertex in the graph
     * @return The degree of the vertex
     * @throws IllegalArgumentException if the vertex does not occur in the graph
     */
    public int degree(V v){
        if(!adjList.containsKey(v)){
            throw new IllegalArgumentException();
        }

        return adjList.get(v).size();
    }

    /**
     * Returns a string representation of the graph. The string representation shows
     * all vertices and edges in the graph.
     * 
     * @return A string representation of the graph
     */
    public String toString(){
        String str = "";
        for (Map.Entry<V,Set<V>> adjEntry : adjList.entrySet()) {
            Iterator<V> adj_iter = adjEntry.getValue().iterator();
            str += "\nVertex [" + adjEntry.getKey() + "] is adjacent to [";
            while(adj_iter.hasNext()){
                str += (adj_iter.next() + ", ");
            }
            if(!str.substring(str.length()-1, str.length()).equals("[")){
                str = str.substring(0,str.length()-2);
            }
            str += "]";
        }
        return str;

    }

}
