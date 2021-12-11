package analyzer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import data.Movie;
import graph.*;
import util.DataLoader;

/**
 * Please include in this comment you and your partner's name and describe any
 * extra credit that you implement
 */
public class MovieLensAnalyzer {

	public static void main(String[] args) {
		// Your program should take two command-line arguments:
		// 1. A ratings file
		// 2. A movies file with information on each movie e.g. the title and genres
		// if(args.length != 2){
		// System.err.println("Usage: java MovieLensAnalyzer
		// [ratings_file][movie_title_file]");
		// System.exit(-1);
		// }
		// FILL IN THE REST OF YOUR PROGRAM

		DataLoader dl = new DataLoader();
		dl.loadData("Algorithms/MovieLens/MovieLensStarter/MovieLens/bin/ml-latest-small/movies.csv",
				"Algorithms/MovieLens/MovieLensStarter/MovieLens/bin/ml-latest-small/ratings.csv");
		Map<Integer, Movie> movies = dl.getMovies();
		System.out.println(movies.toString());

		Scanner scan = new Scanner(System.in);
		String[] fileList = new String[20];
		fileList[0] = "./src/ml-latest-small/ratings/csv";

		for (String file : fileList) {
			System.out.println("file");
		}
		Graph<Integer> G = null;

		while (G == null) {

			System.out.println("");
			System.out.println("There are 3 choices for defining adjacency:");
			System.out.println(
					"[Option 1] u and v are adjacent if 30% of the users who viewed them gave the same rating (unweighted)");
			System.out.println(
					"[Option 2] u and v are adjacent if 30% of the users who viewed them gave the same rating (weight = # users rated)");
			System.out.println(
					"[Option 3] u and v are adjacent if 30% of those who rated u also rated v regardless of rating (weight = # users rated)");
			System.out.println("");
			System.out.print("Choose an option to build the graph (1-3): ");
			int graphChoice = scan.nextInt();
			System.out.print("Creating graph ...");
			switch (graphChoice) {
				case 1:
					G = option1(movies);
					break;
				case 2:
					G = option2(movies);
					break;
				case 3:
					G = option3(movies);
					break;
				default:
					break;
			}

			if (G == null) {
				System.out.println("Could not create graph, please specify (1-3)");
			}
		}
		System.out.println("The graph has been created");

		// TO DO : call Floyd Warshall and store in a 2d array for later ref
		int[][] FW = GraphAlgorithms.floydWarshall(G);

		int userChoice = -1;
		int nodeChoice = -1;
		int startChoice = -1;
		int endChoice = -1;
		int first = -1;
		int second = -1;
		while (userChoice != 6) {
			System.out.println("[Option 1] Print out statistics about the graph");
			System.out.println("[Option 2] Print out node information");
			System.out.println("[Option 3] Display shortest path between two nodes");
			System.out.println("[Option 4] Display common neighbors between two nodes");
			System.out.println("[Option 5] Give me an interesting path");
			System.out.println("[Option 6] Quit");
			System.out.print("Choose an option (1-4): ");
			userChoice = scan.nextInt();
			System.out.println("");

			switch (userChoice) {
				case 1:
					printGraphStats(G, FW);
					break;

				case 2:
					System.out.println("Choose what node to print out: ");
					nodeChoice = scan.nextInt();
					printNodeStats(G, movies, nodeChoice);
					break;

				case 3:
					System.out.println("Choose a start node");
					startChoice = scan.nextInt();
					System.out.println("Choose an end node");
					endChoice = scan.nextInt();
					System.out.println("start: " + startChoice + " end: " + endChoice);

					GraphAlgorithms.printDijkstrasPath(G, movies, startChoice, endChoice);
					break;

				case 4:
					System.out.println("Choose first node");
					first = scan.nextInt();
					System.out.println("Choose second node");
					second = scan.nextInt();
					System.out.println("first: " + first + " second: " + second);

					GraphAlgorithms.printCommonNeighbors(G, movies, first, second);

					break;

				case 5:
					int popularNode = highestDegree(G);
					GraphAlgorithms.printInterestingPath(G, movies, popularNode);

				default:

					break;
			}

		}

		// MovieLensAnalyzer.printNodeInfo(G, dl.getMovies(), 500);
	}

	public static void printGraphStats(Graph<Integer> G, int[][] paths) {
		int numNodes = G.numVertices();
		int numEdges = G.numEdges();
		System.out.println("numNodes = " + numNodes);
		double density = (double) numEdges / (numNodes * (numNodes - 1));
		int maxDegree = highestDegree(G);
		int diameter = -1;
		int average = 0;
		int count = 0;

		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths.length; j++) {
				if (paths[i][j] < numNodes) {
					average += paths[i][j];
					count += 1;
					if (paths[i][j] > diameter) {
						diameter = paths[i][j];
					}
				}
			}
		}
		average = average / count;

		System.out.println("Number of nodes: " + numNodes);
		System.out.println("Number of edges: " + numEdges);
		System.out.println("Graph density: " + density);
		System.out.println("Maximum degree: " + maxDegree);
		System.out.println("Diameter: " + diameter);
		System.out.println("Average shortest path: " + average);
	}

	public static int highestDegree(Graph<Integer> G) {
		Integer max = Integer.MIN_VALUE;
		int degree = -1;
		for (Integer node : G.getVertices()) {
			degree = G.degree(node);
			if (degree > max) {
				max = degree;
			}
		}

		return max;

	}

	/**
	 * This method prints the the information about a given node in a graph.
	 * 
	 * @param G
	 * @param node
	 */
	public static void printNodeStats(Graph<Integer> G, Map<Integer, Movie> movies, int node) {
		String str = movies.get(node).toString();
		str += "Adjacent Nodes: ";
		for (int i : G.getNeighbors(node)) {
			str += "\n\t" + movies.get(i).getTitle();
		}
		System.out.println(str);
	}

	/**
	 * This methods creates an unweighted graph where nodes u and v are connected if
	 * 30% of the people that rated u gave the same rating to v.
	 * 
	 * @param movies
	 * @return
	 */
	public static Graph<Integer> option1(Map<Integer, Movie> movies) {

		Graph<Integer> G = new Graph<>();
		for (Movie m : movies.values()) {
			G.addVertex(m.getMovieId());
		}

		for (int i = 1; i <= movies.size(); i++) {
			for (int j = 1; j <= movies.size(); j++) {
				if (i != j) {
					Movie m1 = movies.get(i);
					Movie m2 = movies.get(j);
					int count = 0;
					for (Entry<Integer, Double> rating : m1.getRatings().entrySet()) {
						if (m2.getRating(rating.getKey()) == rating.getValue()) {
							count++;
						}
					}
					double r1 = (double) count / m1.numRatings();
					if (r1 >= 0.3) {
						G.addEdge(i, j, 1);
					}
					double r2 = (double) count / m2.numRatings();
					if (r2 >= 0.3) {
						G.addEdge(j, i, 1);
					}
				}
			}
		}

		return G;
	}

	/**
	 * This methods creates a weighted graph where nodes u and v are connected if
	 * 30% of the people that rated u gave the same rating to v.
	 * 
	 * @param movies
	 * @return
	 */
	public static Graph<Integer> option2(Map<Integer, Movie> movies) {

		Graph<Integer> G = new Graph<>();
		for (Movie m : movies.values()) {
			G.addVertex(m.getMovieId());
		}

		for (int i = 1; i <= movies.size(); i++) {
			for (int j = 1; j <= movies.size(); j++) {
				if (i != j) {
					Movie m1 = movies.get(i);
					Movie m2 = movies.get(j);
					int count = 0;
					for (Entry<Integer, Double> rating : m1.getRatings().entrySet()) {
						if (m2.getRating(rating.getKey()) == rating.getValue()) {
							count++;
						}
					}
					double r1 = (double) count / m1.numRatings();
					if (r1 >= 0.3) {
						G.addEdge(i, j, count);
					}
					double r2 = (double) count / m2.numRatings();
					if (r2 >= 0.3) {
						G.addEdge(j, i, count);
					}
				}
			}
		}

		return G;
	}

	/**
	 * This methods creates a weighted graph where nodes u and v are connected if
	 * 30% of the people that rated u also rated v, regardless of the ratings.
	 * 
	 * @param movies
	 * @return
	 */
	public static Graph<Integer> option3(Map<Integer, Movie> movies) {

		Graph<Integer> G = new Graph<>();
		for (Movie m : movies.values()) {
			G.addVertex(m.getMovieId());
		}

		for (int i = 1; i <= movies.size(); i++) {
			for (int j = 1; j <= movies.size(); j++) {
				if (i != j) {
					Movie m1 = movies.get(i);
					Movie m2 = movies.get(j);
					int count = 0;
					for (Entry<Integer, Double> rating : m1.getRatings().entrySet()) {
						if (m2.getRating(rating.getKey()) != -1) {
							count++;
						}
					}
					double r1 = (double) count / m1.numRatings();
					if (r1 >= 0.3) {
						G.addEdge(i, j, count);
					}
					double r2 = (double) count / m2.numRatings();
					if (r2 >= 0.3) {
						G.addEdge(j, i, count);
					}
				}
			}
		}

		return G;
	}

	/**
	 * This method returns an array list of movie id's whose tittle
	 * contains the query.
	 * 
	 * @param movies
	 * @param title
	 * @return An array list of movie ids
	 */
	public static ArrayList<String> titleSearch(Map<Integer, Movie> movies, String title) {
		ArrayList<String> matches = new ArrayList<>();
		for (Movie m : movies.values()) {
			if ((m.getTitle().toUpperCase()).contains(title.toUpperCase())) {
				matches.add("(" + m.getMovieId() + ") " + m.getTitle());
			}
		}
		return matches;
	}

}
