package playground;

import java.util.concurrent.*;

public class SemaphoreTest {
    public static void main(String... args) throws Exception {
        var time = System.currentTimeMillis();
        var bouncer = new Semaphore(25);
        try (var pool = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 100; i++) {
                pool.execute(() -> {
                    bouncer.acquireUninterruptibly();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new CancellationException("interrupted");
                    } finally {
                        bouncer.release();
                    }
                });
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println(time + "ms");
    }
}