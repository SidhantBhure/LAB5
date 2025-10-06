import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class OrderCounter {
    private final int capacity;
    private final Queue<String> orders = new LinkedList<>();
    private int orderNumber = 1;
    private final int maxOrders;
    private boolean finished = false;

    public OrderCounter(int capacity, int maxOrders) {
        this.capacity = capacity;
        this.maxOrders = maxOrders;
    }

    public synchronized void placeOrder() {
        while (orders.size() == capacity && !finished) {
            try {
                System.out.println("[Chef] Counter full! Waiting...");
                wait();
            } catch (InterruptedException e) {}
        }

        if (orderNumber > maxOrders) {
            finished = true;
            notifyAll();
            return;
        }

        String order = "Order-" + orderNumber++;
        orders.add(order);
        System.out.println("[Chef] Placed: " + order + " | On counter: " + orders.size());
        notifyAll();
    }

    public synchronized void serveOrder() {
        while (orders.isEmpty() && !finished) {
            try {
                System.out.println("[Waiter] Counter empty! Waiting...");
                wait();
            } catch (InterruptedException e) {}
        }

        if (orders.isEmpty() && finished) {
            return;
        }

        String order = orders.poll();
        System.out.println("[Waiter] Served: " + order + " | Left on counter: " + orders.size());
        notifyAll();
    }

    public boolean isFinished() {
        return finished && orders.isEmpty();
    }
}

class Chef implements Runnable {
    private final OrderCounter counter;
    private final Random random = new Random();

    public Chef(OrderCounter counter) {
        this.counter = counter;
    }

    public void run() {
        while (!counter.isFinished()) {
            counter.placeOrder();
            try { Thread.sleep(random.nextInt(400) + 200); } catch (InterruptedException e) {}
        }
        System.out.println("[Chef] Finished cooking all orders.");
    }
}

class Waiter implements Runnable {
    private final OrderCounter counter;
    private final Random random = new Random();

    public Waiter(OrderCounter counter) {
        this.counter = counter;
    }

    public void run() {
        while (!counter.isFinished()) {
            counter.serveOrder();
            try { Thread.sleep(random.nextInt(500) + 300); } catch (InterruptedException e) {}
        }
        System.out.println("[Waiter] Finished serving all orders.");
    }
}

public class Assignment3 {
    public static void main(String[] args) {
        System.out.println("Restaurant Kitchen Simulation (Plain Text, No Emojis)\n");

        OrderCounter counter = new OrderCounter(5, 20);

        Thread chef = new Thread(new Chef(counter), "Chef");
        Thread waiter = new Thread(new Waiter(counter), "Waiter");

        chef.start();
        waiter.start();
    }
}
