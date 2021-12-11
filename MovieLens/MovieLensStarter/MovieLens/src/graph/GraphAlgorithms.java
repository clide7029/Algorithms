package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GraphAlgorithms {

	public int[] dijkstras(Graph<Integer> G, int start) {
		Set<Integer> nodes = G.getVertices();
		int size = nodes.size();
		PriorityQueue Q = new PriorityQueue();
		HashMap<Integer, Integer> dist = new HashMap<>();
		int[] visited = new int[size];
		Integer max = Integer.MAX_VALUE;

		for (Integer node : nodes) {
			dist.put(node, max);
			Q.push(node, dist.get(node));
		}

		while (!Q.isEmpty()) {
			Integer u = Q.pop();
			for (Integer v : G.getNeighbors(u)) {
				int altCost = dist.get(u) + 1;
				if (altCost < dist.get(v)) {
					dist.put(v, altCost);
					visited[v] = u;
					Q.changePriority(v, altCost);
				}
			}
		}

		return visited;

	}

	public int[] findHeaviestNeighbor(Graph<Integer> G, int[] liked) {

		for (int i = 0; i < liked.length; i++) {

		}
	}

}
