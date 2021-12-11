package analyzer;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import data.Movie;
import graph.Graph;
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
		dl.loadData("bin/ml-latest-small/movies.csv", "bin/ml-latest-small/ratings.csv");

		System.out.print("Start...");
		long start = System.nanoTime();
		Graph<Integer> G = MovieLensAnalyzer.option3(dl.getMovies());
		long time = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done! " + time + "ms");


		MovieLensAnalyzer.printNodeInfo(G, dl.getMovies(), 500);
	}

	/**
	 * This method prints the the information about a given node in a graph.
	 * 
	 * @param G
	 * @param node
	 */
	public static void printNodeInfo(Graph<Integer> G, Map<Integer,Movie> movies, int node ){
		String str = movies.get(node).toString();
		str += "Adjacent Nodes: ";
		for (int i: G.getNeighbors(node)){
			str += "\n\t"+movies.get(i).getTitle();
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
	public static ArrayList<Integer> titleSearch(Map<Integer,Movie> movies, String title){
		ArrayList<Integer> matches = new ArrayList<>();
		for (Movie m: movies.values()){
			if ((m.getTitle().toUpperCase()).contains(title.toUpperCase())){
				matches.add(m.getMovieId());
			}
		}
		return matches;
	}


}
