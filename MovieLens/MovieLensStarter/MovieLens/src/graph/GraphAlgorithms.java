package graph;

import java.util.HashSet;
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
		int i = 0;

		for (int like : liked) {
			for (Integer node : G.getNeighbors(like)) {
				if (seen.contains(node)) {
					res[i++] = node;
				} else {
					seen.add(node);
				}
			}
		}

		return res;
	}

}
