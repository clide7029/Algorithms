import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        PriorityQueue pq = new PriorityQueue();
        Random rand = new Random();

        for(int e=0; e<45; e++){
            int p = rand.nextInt(100);
            System.out.println("pushing on (" + p + ", " + e + ")");
            pq.push(p, e);
        }

        pq.printMap();
        pq.printHeap();
        pq.pop();
        pq.printMap();
        pq.printHeap();

    }
}
