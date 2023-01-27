package playground;

import java.util.concurrent.atomic.*;

public class RaceConditionExampleFixed {
    private static final LongAdder count = new LongAdder(); // 32 + 152 * 8 = 1248

    // 1 second
    public static void main(String... args) throws Exception {
        long time = System.nanoTime();
        try {
            Runnable task = () -> {
                for (int i = 0; i < 100_000_000; i++)
                    count.increment();
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