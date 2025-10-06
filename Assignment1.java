import java.util.Random;

class RunnerThread extends Thread {
    private String runnerName;
    private Random random = new Random();

    public RunnerThread(String name) {
        this.runnerName = name;
    }

    @Override
    public void run() {
        for (int distance = 10; distance <= 100; distance += 10) {
            System.out.println("Runner " + runnerName + ": " + distance + "m");
            if (distance == 100) {
                System.out.println("Runner " + runnerName + ": Finished!");
            }
            try {
                Thread.sleep(50 + random.nextInt(151));
            } catch (InterruptedException e) {
                System.out.println(runnerName + " was interrupted!");
            }
        }
    }
}

class RunnerRunnable implements Runnable {
    private String runnerName;
    private Random random = new Random();

    public RunnerRunnable(String name) {
        this.runnerName = name;
    }

    @Override
    public void run() {
        for (int distance = 10; distance <= 100; distance += 10) {
            System.out.println("Runner " + runnerName + ": " + distance + "m");
            if (distance == 100) {
                System.out.println("Runner " + runnerName + ": Finished!");
            }
            try {
                
                Thread.sleep(50 + random.nextInt(151));
            } catch (InterruptedException e) {
                System.out.println(runnerName + " was interrupted!");
            }
        }
    }
}

public class Assignment1 {
    public static void main(String[] args) {
        System.out.println(" The Digital 100-Meter Dash Begins! \n");

        RunnerThread runner1 = new RunnerThread("Bolt");

        Thread runner2 = new Thread(new RunnerRunnable("Blake"));
        Thread runner3 = new Thread(new RunnerRunnable("Gatlin"));

        runner1.start();
        runner2.start();
        runner3.start();

        try {
            runner1.join();
            runner2.join();
            runner3.join();
        } catch (InterruptedException e) {
            System.out.println("Race interrupted!");
        }

        System.out.println("\n Race Over! All runners have finished! ");
    }
}
