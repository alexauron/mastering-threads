package playground;// result is unpredictable

public class RaceConditionExample {
    private static long count = 0;

    public static void main(String... args) throws Exception {
        long time = System.nanoTime();
        try {
            Runnable task = () -> {
                for (int i = 0; i < 100_000_000; i++) count++;
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