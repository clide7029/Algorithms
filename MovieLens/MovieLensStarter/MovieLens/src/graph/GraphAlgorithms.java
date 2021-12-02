package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GraphAlgorithms {
	
	public ArrayList<Integer> dijkstras(Graph<Integer> G, int start, int end){
		Set<Integer> nodes = G.getVertices();
		PriorityQueue Q = new PriorityQueue();
		HashMap<Integer, Integer> dist = new HashMap<>();
		ArrayList<Integer> visited = new ArrayList<>();
		Integer max = Integer.MAX_VALUE;

		for (Integer node : nodes) {
			dist.put(node, max);
			Q.push(node, dist.get(node));
		}

		while(!Q.isEmpty()){
			Integer u = Q.pop();
			for (Integer v : G.getNeighbors(u)) {
				int altCost = dist.get(u) + 1;
				if(altCost < dist.get(v)){
					dist.put(v, altCost);
					visited.add(v, u);
					Q.changePriority(v, altCost);
				}
			}
		}

		return visited;


	}
}
