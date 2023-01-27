package playground;

import java.util.concurrent.atomic.*;

public class RaceConditionExampleAtomics {
    private static final AtomicLong count = new AtomicLong(); // 24 bytes

    // 9.3 seconds
    public static void main(String... args) throws Exception {
        long time = System.nanoTime();
        try {
            Runnable task = () -> {
                for (int i = 0; i < 100_000_000; i++)
                    count.incrementAndGet();
            };
            var threads = new Thread[5];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(task);
                threads[i].start();
            }
            for (var thread : threads) {
                thread.join();
            }
            System.out.println("count = " + count);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}