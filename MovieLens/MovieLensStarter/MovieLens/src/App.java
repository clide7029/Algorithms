import java.util.Scanner;

import graph.Graph;

public class App {
    public static void main(String[] args) throws Exception {
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
                    G = new Graph<>();
                    // G = option1();
                    break;
                case 2:
                    // G = option2();
                    break;
                case 3:
                    // G = option3();
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

        int userChoice = -1;
        int nodeChoice = -1;
        int startChoice = -1;
        int endChoice = -1;
        while (userChoice != 4) {
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
                    // printGraphStats(G);
                    break;
                case 2:
                    System.out.println("Choose what node to print out: ");
                    nodeChoice = scan.nextInt();
                    // printNodeStats(G, nodeChoice);
                    break;
                case 3:
                    System.out.println("Choose a start node");
                    startChoice = scan.nextInt();
                    System.out.println("Choose an end node");
                    endChoice = scan.nextInt();
                    System.out.println("start: " + startChoice + " end: " + endChoice);

                    // int[] path = dijkstrasPath(G, startChoice, endChoice);
                    break;
                case 4:

                    break;
                default:

                    break;
            }

        }

        System.out.println("Hello, World!");
    }

    public void printGraphStats(Graph<Integer> G) {
        int numNodes = G.numVertices();
        int numEdges = G.numEdges();
        double density = numEdges / (numNodes * (numNodes - 1));
        int maxDegree = highestDegree(G);
        int diameter = -1;
        int average = -1;

        int[][] paths = null; // Floyd Warshall

        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths.length; j++) {
                average += paths[i][j];
                if (paths[i][j] > diameter) {
                    diameter = paths[i][j];
                }
            }
        }
        average = average / (paths.length ^ 2);

        System.out.println("Number of nodes: " + numNodes);
        System.out.println("Number of edges: " + numEdges);
        System.out.println("Graph density: " + density);
        System.out.println("Maximum degree: " + maxDegree);
        System.out.println("Diameter: " + diameter);
        System.out.println("Average shortest path: " + average);
    }

    public int highestDegree(Graph<Integer> G) {
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

}
