package playground;// result is unpredictable

public class RaceConditionExampleSynchronized {
    private static long count = 0; // 8 bytes
    private static synchronized void increment() {count++;}
    private static synchronized long get() { return count;}

    // 36 seconds
    public static void main(String... args) throws Exception {
        long time = System.nanoTime();
        try {
            Runnable task = () -> {
                for (int i = 0; i < 100_000_000; i++)
                    increment();
            };
            var threads = new Thread[5];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(task);
                threads[i].start();
            }
            for (var thread : threads) {
                thread.join();
            }

            System.out.println("count = " + get());
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}