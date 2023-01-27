package playground;

import java.util.concurrent.*;

public class SemaphoreBlockingQueueTest {
    public static void main(String... args) throws Exception {
        var time = System.currentTimeMillis();
        record WhoAmI(Thread thread, Throwable stackTrace) {
            WhoAmI() {
                this(Thread.currentThread(), new Throwable("Stack Trace"));
            }
        }
        var bouncer = new ArrayBlockingQueue<WhoAmI>(25);
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> System.out.println(bouncer), 500, 1000, TimeUnit.MILLISECONDS);
        try (var pool = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 100; i++) {
                pool.execute(() -> {
                    try {
                        var whoAmI = new WhoAmI();
                        bouncer.put(whoAmI);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new CancellationException("interrupted");
                        } finally {
                            bouncer.remove(whoAmI);
                        }
                    } catch (InterruptedException e) {
                        throw new CancellationException("interrupted");
                    }
                });
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println(time + "ms");
        timer.shutdown();
    }
}