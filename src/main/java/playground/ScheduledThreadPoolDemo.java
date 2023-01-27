package playground;

import java.time.*;
import java.util.concurrent.*;

public class ScheduledThreadPoolDemo {
    private static String name = null;

    public static void main(String... args) {
        ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1) {
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t == null && r instanceof Future<?> future && future.isDone()) {
                    try {
                        Object result = future.get();
                    } catch (CancellationException ce) {
                        t = ce;
                    } catch (ExecutionException ee) {
                        t = ee.getCause();
                    } catch (InterruptedException ie) {          // ignore/reset
                        Thread.currentThread().interrupt();
                    }
                }
                if (t != null) t.printStackTrace();
            }
        };


        timer.scheduleAtFixedRate(() -> System.out.println(name.toUpperCase()),
                5500, 2000, TimeUnit.MILLISECONDS);
        timer.scheduleAtFixedRate(() -> System.out.println(LocalDateTime.now()),
                1, 1, TimeUnit.SECONDS);
    }
}
