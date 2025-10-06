import java.util.Random;

class SynchronizedTicketBookingSystem {
    private int availableTickets;

    public SynchronizedTicketBookingSystem(int totalTickets) {
        this.availableTickets = totalTickets;
    }

    public synchronized void bookTickets(String userName, int numTickets) {
        if (availableTickets >= numTickets) {
            System.out.println(userName + " is trying to book " + numTickets + " ticket(s)...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            availableTickets -= numTickets;
            System.out.println(" " + userName + " successfully booked " + numTickets +
                               " ticket(s). Remaining: " + availableTickets);
        } else {
            System.out.println(" " + userName + " failed to book " + numTickets +
                               " ticket(s). Only " + availableTickets + " left.");
        }
    }
}

class SynchronizedUser implements Runnable {
    private SynchronizedTicketBookingSystem system;
    private String userName;
    private Random random = new Random();

    public SynchronizedUser(SynchronizedTicketBookingSystem system, String userName) {
        this.system = system;
        this.userName = userName;
    }

    @Override
    public void run() {
        int ticketsToBook = random.nextInt(5) + 1;
        system.bookTickets(userName, ticketsToBook);
    }
}

public class Assignment2 {
    public static void main(String[] args) {
        System.out.println(" Movie Ticket Booking Simulation (With Synchronization)\n");

        SynchronizedTicketBookingSystem system = new SynchronizedTicketBookingSystem(20);

        Thread[] users = new Thread[8];
        for (int i = 0; i < users.length; i++) {
            users[i] = new Thread(new SynchronizedUser(system, "User-" + (i + 1)));
            users[i].start();
        }

        for (Thread t : users) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n Booking completed safely (With Synchronization)");
    }
}
