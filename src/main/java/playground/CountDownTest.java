package playground;

import java.util.concurrent.*;

public class CountDownTest {
    public static void main(String... args) throws Exception {
        try (var pool = Executors.newFixedThreadPool(5)) {
            var latch = new ExtendedCountDownLatch(3);
            for (int i = 0; i < 5; i++) {
                pool.execute(() -> {
                    System.out.println("Waiting ...");
                    latch.awaitUninterruptibly();
                    System.out.println("... finished");
                });
                Thread.sleep(1000);
                latch.countDown();
            }
        }
    }
}

