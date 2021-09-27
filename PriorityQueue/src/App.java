import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        PriorityQueue pq = new PriorityQueue();
        Random rand = new Random();

        for(int i=0; i<7; i++){
            int r = rand.nextInt(100);
            System.out.println("pushing on (" + r + ", " + i + ")");
            pq.push(r, i);
        }

        pq.printMap();
        pq.printHeap();

    }
}
