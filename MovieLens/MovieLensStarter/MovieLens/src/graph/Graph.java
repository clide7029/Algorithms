package graph;

import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Tristan Gaeta
 * @version 10-02-2021
 */
public class Graph<V> implements GraphIfc<V> {

	private int numEdges;
	private Map<V, Map<V, Integer>> graph;

	public Graph() {
		this.numEdges = 0;
		this.graph = new HashMap<>();
	}

	/**
	 * Returns the number of vertices in the graph
	 * 
	 * @return The number of vertices in the graph
	 */
	public int numVertices() {
		return this.graph.size();
	}

	public int getWeight(V u, V v){
		if (!this.edgeExists(u, v)){
			throw new IllegalArgumentException("Edge does not exist");
		}
		return this.graph.get(u).get(v);
	}

	/**
	 * Returns the number of edges in the graph
	 * 
	 * @return The number of edges in the graph
	 */
	public int numEdges() {
		return this.numEdges;
	}

	/**
	 * Removes all vertices from the graph
	 */
	public void clear() {
		this.graph.clear();
		this.numEdges = 0;
	}

	/**
	 * Adds a vertex to the graph. This method has no effect if the vertex already
	 * exists in the graph.
	 * 
	 * @param v The vertex to be added
	 */
	public void addVertex(V v) {
		if (this.containsVertex(v)) {
			return;
		}
		this.graph.put(v, new HashMap<V, Integer>());
	}

	/**
	 * Adds an edge between vertices u and v in the graph. Does
	 * not allow for duplicate edges.
	 *
	 * @param u A vertex in the graph
	 * @param v A vertex in the graph
	 * @throws IllegalArgumentException if either vertex does not occur in the
	 *                                  graph.
	 */
	public void addEdge(V u, V v, int weight) {
		if (this.edgeExists(u, v)) { // will throw exception if u or v don't exist
			return;
		}
		this.graph.get(u).put(v, weight);
		this.numEdges++;
	}

	/**
	 * Returns the set of all vertices in the graph.
	 * 
	 * @return A set containing all vertices in the graph
	 */
	public Set<V> getVertices() {
		return this.graph.keySet();
	}

	/**
	 * Returns the neighbors of v in the graph. A neighbor is a vertex that is
	 * connected to
	 * v by an edge. If the graph is directed, this returns the vertices u for which
	 * an
	 * edge (v, u) exists.
	 * 
	 * @param v An existing node in the graph
	 * @return All neighbors of v in the graph.
	 * @throws IllegalArgumentException if the vertex does not occur in the graph
	 */
	public Set<V> getNeighbors(V v) {
		if (!this.containsVertex(v)) {
			throw new IllegalArgumentException("Cannot access non-existent vertex.");
		}
		return this.graph.get(v).keySet();
	}

	/**
	 * Determines whether the given vertex is already contained in the graph. The
	 * comparison
	 * is based on the <code>equals()</code> method in the class V.
	 * 
	 * @param v The vertex to be tested.
	 * @return True if v exists in the graph, false otherwise.
	 */
	public boolean containsVertex(V v) {
		return this.graph.containsKey(v);
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
	public boolean edgeExists(V v, V u) {
		if (!this.containsVertex(u) || !this.containsVertex(v)) {
			throw new IllegalArgumentException("Cannot access non-existent vertices.");
		}
		return this.graph.get(v).containsKey(u);
	}

	/**
	 * Returns the degree of the vertex. In a directed graph, this returns the
	 * outgoing-degree of the vertex.
	 * 
	 * @param v A vertex in the graph
	 * @return The degree of the vertex
	 * @throws IllegalArgumentException if the vertex does not occur in the graph
	 */
	public int degree(V v) {
		if (!this.containsVertex(v)) {
			throw new IllegalArgumentException("Cannot access non-existent vertex.");
		}
		return this.graph.get(v).size();
	}

	/**
	 * Returns a string representation of the graph. The string representation shows
	 * all
	 * vertices and edges in the graph.
	 * 
	 * @return A string representation of the graph
	 */
	public String toString() {
		return this.graph.toString();
	}

	/**
	 * Returns a hashmap containing paths found through BFS
	 * starting at the given vertex.
	 * 
	 * @return a hashmap containing paths found through BFS.
	 * @throws IllegalArgumentException if vertex does not occur in the graph.
	 */
	public Map<V, V> bfs(V origin) {
		if (!this.containsVertex(origin)) {
			throw new IllegalArgumentException("Cannot access non-existent vertex.");
		}
		LinkedList<V> q = new LinkedList<>();
		HashMap<V, V> out = new HashMap<>();

		q.add(origin);
		out.put(origin, null);

		while (!q.isEmpty()) {
			V u = q.poll();
			for (V v : this.getNeighbors(u)) {
				if (!out.containsKey(v)) {
					q.add(v);
					out.put(v, u);
				}
			}
		}
		return out;
	}

	/**
	 * Adds an edges (u,v) and (v,u) to graph
	 *
	 * @param u A vertex in the graph
	 * @param v A vertex in the graph
	 * @throws IllegalArgumentException if either vertex does not occur in the
	 *                                  graph.
	 */
	public void addDoubleEdge(V u, V v, int weight) {
		if (this.edgeExists(u, v) && this.edgeExists(v, u)) { // will throw exception if u or v don't exist
			return;
		}
		this.addEdge(u, v, weight);
		this.addEdge(v, u, weight);
	}

	/**
	 * Returns a hashmap containing paths found through DFS
	 * starting at the given vertex.
	 * 
	 * @return a hashmap containing paths found through DFS.
	 * @throws IllegalArgumentException if vertex does not occur in the graph.
	 */
	public Map<V, V> dfs(V origin) {
		if (!this.containsVertex(origin)) {
			throw new IllegalArgumentException("Cannot access non-existent vertex.");
		}
		return this.dfsHelper(new HashMap<>(), null, origin);
	}

	/**
	 * Helper method for DFS.
	 * 
	 * @return a hashmap containing paths found through DFS.
	 */
	private Map<V, V> dfsHelper(Map<V, V> out, V parent, V current) {
		out.put(current, parent);
		for (V v : this.getNeighbors(current)) {
			if (!out.containsKey(v)) {
				this.dfsHelper(out, current, v);
			}
		}
		return out;
	}

    @Override
    public void addEdge(V u, V v) {
        
    }
}