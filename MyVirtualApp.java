import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;


public class MyVirtualApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var fixedThreadPool = Executors.newFixedThreadPool(100);
        System.out.println("Regular threads 1000:");
        executeIn(1_000, fixedThreadPool);
        var virtualThreadPool = Executors.newVirtualThreadPerTaskExecutor();
        System.out.println("Virtual threads 1000:");
        executeIn(1_000, virtualThreadPool);
        System.out.println("Virtual threads 100_000:");
        executeIn(100_000, virtualThreadPool);

        fixedThreadPool.shutdown();
        virtualThreadPool.shutdown();
    }

    private static void executeIn(int numberOfThreads, ExecutorService executor) throws InterruptedException, ExecutionException {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            tasks.add(new Task(i));
        }

        long time = System.currentTimeMillis();

        List<Future<Integer>> futures = executor.invokeAll(tasks);

        long sum = 0;
        for (Future<Integer> future : futures) {
            sum += future.get();
        }

        time = System.currentTimeMillis() - time;

        System.out.println("sum = " + sum + "; time = " + time + " ms");
    }
    static class Task implements Callable<Integer> {

        private final int number;

        public Task(int number) {
            this.number = number;
        }

        @Override
        public Integer call() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.printf("Thread %s - Task %d canceled.%n", Thread.currentThread().getName(), number);
                return 0;
            }
            return 1;
        }
    }
}
