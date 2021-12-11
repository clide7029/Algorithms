package graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphAlgorithms {

	public int[] dijkstrasAlgorithm(Graph<Integer> G, int start) {
		Set<Integer> nodes = G.getVertices();
		int size = nodes.size();
		PriorityQueue Q = new PriorityQueue();
		int[] dist = new int[size];
		int[] prev = new int[size];
		Integer max = Integer.MAX_VALUE;

		dist[start] = 0;
		for (Integer node : nodes) {
			dist[node] = max;
			Q.push(node, dist[node]);
		}

		while (!Q.isEmpty()) {
			Integer u = Q.pop();
			for (Integer v : G.getNeighbors(u)) {
				int altCost = dist[u] + G.getWeight(u, v);
				if (altCost < dist[v]) {
					dist[v] = altCost;
					prev[v] = u;
					Q.changePriority(v, altCost);
				}
			}
		}

		return prev;
	}

	public int[] dijkstrasPath(Graph<Integer> G, int start, int end) {
		int current = end;
		int[] prev = dijkstrasAlgorithm(G, start);
		int[] path = new int[prev.length];
		path[0] = current;
		int i = 1;

		while (current != start) {
			path[i++] = prev[current];
			current = prev[current];
		}

		return path;
	}

	public int[] commonNeighbors(Graph<Integer> G, int u, int v) {
		Set<Integer> seen = G.getNeighbors(u);
		int[] res = new int[seen.size()];
		int i = 0;

		for (Integer node : G.getNeighbors(v)) {
			if (seen.contains(node)) {
				res[i++] = node;
			}
		}

		return res;
	}

	public int[] commonNeighborsList(Graph<Integer> G, int[] liked) {
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

	public int[] interestingPath(Graph<Integer> G, int popularNode) {
		Map<Integer, Integer> dfs = G.dfs(popularNode);
		int current = popularNode;
		int[] path = new int[G.numVertices()];
		int i = 0;

		while (dfs.containsKey(current)) {
			path[i++] = current;
			current = dfs.get(current);
		}
		return path;
	}

}
