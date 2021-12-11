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
        System.out.println("");
        System.out.println("There are 3 choices for defining adjacency:");
        System.out.println("[Option 1] u and v are adjacent if two or more users liked the movie, its weight is the number of users");
        System.out.println("");
        System.out.print("Choose an option to build the graph (1-3): ");
        int graphChoice = scan.nextInt();
        System.out.print("Creating graph ...");
        Graph<Integer> G = new Graph<>();
        //call graph create method
        System.out.println("The graph has been created");

        int userChoice = -1;
        int nodeChoice = -1;
        int startChoice = -1;
        int endChoice = -1;
        while(userChoice != 4){
            System.out.println("[Option 1] Print out statistics about the graph");
            System.out.println("[Option 2] Print out node information");
            System.out.println("[Option 3] Display shortest path between two nodes");
            System.out.println("[Option 4] Quit");
            System.out.print("Choose an option (1-4): ");
            userChoice = scan.nextInt();
            System.out.println("");

            switch(userChoice){
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
                    System.out.println("start: "+ startChoice + "end: " + endChoice);

                    // shortestPath(G, startChoice, endChoice);
                    break;
                case 4:

                    break;
                default:

                    break;
            }


        }


        System.out.println("Hello, World!");
    }
}
