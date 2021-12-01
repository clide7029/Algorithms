import java.util.Random;

public class App {
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        Random rand = new Random();

        for (int e = 0; e < 3; e++) {
            int p = rand.nextInt(100);
            System.out.println("pushing on (" + p + ", " + e + ")");
            pq.push(p, e);
        }
        for (int i = 0; i < 16; i++) {
            System.out.println("\n------" + pq.size() + "------\n");
            pq.pop();
        }

        System.out.println(pq.isEmpty());

        pq.printMap();
        pq.printHeap();
        // pq.changePriority(0, pq.size()-1);
        // pq.printMap();
        // pq.printHeap();

    }
}
