package graph;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

import data.Movie;

public class GraphAlgorithms {

	public static int[][] floydWarshall(Graph<Integer> G) {
		int[][] A = new int[G.numVertices()][G.numVertices()];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				if (i == j) {
					A[i][j] = 0;
				} else if (G.edgeExists(i + 1, j + 1)) {
					A[i][j] = G.getWeight(i + 1, j + 1);
				} else {
					A[i][j] = A.length + 1;
				}
			}
		}
		for (int k = 0; k < A.length; k++) {
			int[][] B = new int[G.numVertices()][G.numVertices()];
			for (int i = 0; i < B.length; i++) {
				for (int j = 0; j < B[i].length; j++) {
					B[i][j] = Math.min(A[i][k] + A[k][j], A[i][j]);
				}
			}
			A = B;
		}
		return A;
	}

	public static int[] dijkstrasAlgorithm(Graph<Integer> G, int start) {
		Set<Integer> nodes = G.getVertices();
		int size = nodes.size() + 1;
		PriorityQueue Q = new PriorityQueue();
		int[] dist = new int[size];
		int[] prev = new int[size];
		Integer max = Integer.MAX_VALUE - 100000;

		dist[start] = 0;
		for (Integer node : nodes) {
			dist[node] = max;
			Q.push(dist[node], node);
		}

		while (!Q.isEmpty()) {
			Integer u = Q.pop();
			for (Integer v : G.getNeighbors(u)) {
				int altCost = dist[u] + G.getWeight(u, v);
				if (altCost < dist[v]) {
					dist[v] = altCost;
					prev[v] = u;
					Q.changePriority(altCost, v);
				}
			}
		}

		return prev;
	}

	public static Stack<Integer> dijkstrasPath(Graph<Integer> G, int start, int end) {
		int current = end;
		int[] prev = dijkstrasAlgorithm(G, start);
		Stack<Integer> path = new Stack<>();
		// path[0] = current;
		// int i = 1;

		while (current != start) {
			path.push(current);
			current = prev[current];
		}

		path.push(current);

		return path;
	}

	public static void printDijkstrasPath(Graph<Integer> G, Map<Integer, Movie> movies, int start, int end) {
		Stack<Integer> path = dijkstrasPath(G, start, end);

		String str = "{";
		for (int node : path) {
			str += "\n\t" + movies.get(node).getTitle();
		}
		str += "}";
		System.out.println(str);
	}

	public static int[] printCommonNeighbors(Graph<Integer> G, Map<Integer, Movie> movies, int u, int v) {
		Set<Integer> seen = G.getNeighbors(u);
		int[] res = new int[seen.size()];
		int i = 0;

		for (Integer node : G.getNeighbors(v)) {
			if (seen.contains(node)) {
				res[i++] = node;
			}
		}

		String str = "{";
		for (int node : res) {
			str += "\n\t" + movies.get(node).getTitle();
		}
		str += "}";
		System.out.println(str);

		return res;
	}

	public static int[] commonNeighborsList(Graph<Integer> G, int[] liked) {
		Set<Integer> seen = G.getNeighbors(liked[0]);
		int[] res = new int[G.getVertices().size()];
		int j = 0;

		for (int i = 1; i < liked.length; i++) {
			for (Integer node : G.getNeighbors(liked[i])) {
				if (seen.contains(node)) {
					res[j++] = node;
				} else {
					seen.add(node);
				}
			}
		}

		return res;
	}

	public static void printInterestingPath(Graph<Integer> G, Map<Integer, Movie> movies, int popularNode) {
		Map<Integer, Integer> dfs = G.dfs(popularNode);
		int current = popularNode;
		int[] path = new int[G.numVertices()];
		int i = 0;

		while (dfs.containsKey(current)) {
			path[i++] = current;
			current = dfs.get(current);
		}

		String str = "{";
		for (int node : path) {
			str += "\n\t" + movies.get(node).getTitle();
		}
		str += "}";
		System.out.println(str);
	}

}
