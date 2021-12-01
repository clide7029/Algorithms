import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;



/**
 * An interface describing a Graph object. The graph can be either directed or
 * undirected.
 * 
 * @author alchambers
 */
public class Graph<V> implements GraphIfc<V>{

    protected Map<V, Set<V>> adjList;

    public <V> Graph(V vertex) {

        adjList = new Map<V, Set<V>();
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
        int size = 0;
        for (Map.Entry<V,Set<V>> adjEntry : adjList.entrySet()) {
            size += adjEntry.getValue().size();
        }
        return size;
    }

    /**
     * Removes all vertices from the graph
     */
    public void clear(){
        adjList.clear();
    }

    /**
     * Adds a vertex to the graph. This method has no effect if the vertex already
     * exists in the graph.
     * 
     * @param v The vertex to be added
     */
    public void addVertex(V v){
        Set<V> set = new HashSet<V>();
        adjList.put(v, set);
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
        if(!(adjList.containsKey(u) && adjList.containsKey(v))){
            throw new IllegalArgumentException();
        }
        adjList.get(v).add(u);
        adjList.get(u).add(v);
    }

    /**
     * Returns the set of all vertices in the graph.
     * 
     * @return A set containing all vertices in the graph
     */
    public Set<V> getVertices(){
        Set<V> set = new HashSet<V>();
        for (Map.Entry<V,Set<V>> adjEntry : adjList.entrySet()) {
            set.add(adjEntry.getKey());
        }
        return set;
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
        Iterator<V> v_iter = adjList.get(v).iterator();
        Iterator<V> u_iter = adjList.get(u).iterator();
        Set<V> v_adj = adjList.get(v);
        Set<V> u_adj = adjList.get(u);
        
        while(v_iter.hasNext() || u_iter.hasNext()){
            if(u_adj.contains(v_iter.next())){
                return true;
            }
            if (v_adj.contains(u_iter.next())) {
                return true;
            }
        }

        return false;

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
            str += "\nVertice [" + adjEntry.getKey() + "] is adjacent to [";
            while(adj_iter.hasNext()){
                str += (adj_iter.next() + ", ");
            }
            str += "]";
        }
        System.out.println(str);
        return str;

    }

}
