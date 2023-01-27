package playground;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class SmallTaskStart {
    public static void main(String... args) {
        List<Executor> executors = List.of(
                task -> new Thread(task).start(),
                Executors.newCachedThreadPool(),
                Executors.newFixedThreadPool(
                        Runtime.getRuntime()
                                .availableProcessors()),
                Executors.newWorkStealingPool(),
                Executors.newVirtualThreadPerTaskExecutor()
        );

        for (Executor executor : executors) {
            test(executor);
        }

        for (Executor executor : executors) {
            if (executor instanceof ExecutorService service) {
                service.shutdown();
            }
        }

        class OurExecutor implements Executor {
            private volatile boolean running = true;
            private final BlockingQueue<Runnable> jobs =
                    new LinkedTransferQueue<>();
            private final Vector<Thread> threads = new Vector<>();

            {
                for (int i = 0; i < Runtime.getRuntime()
                        .availableProcessors(); i++) {
                    Thread thread = new Thread(() -> {
                        while (running) {
                            try {
                                Runnable job = jobs.take();
                                job.run();
                            } catch (InterruptedException e) {
                                // ignore
                            } catch (Throwable t) {
                                System.err.println("Ignoring " + t);
                            }
                        }
                    });
                    threads.add(thread);
                    thread.start();
                }
            }

            @Override
            public void execute(Runnable command) {
                jobs.add(command);
            }

            public void shutdown() {
                running = false;
                threads.forEach(Thread::interrupt);
            }
        }
        ;

        System.out.println("Running our own");

        OurExecutor executor = new OurExecutor();
        test(executor);
        executor.shutdown();
    }

    private static void test(Executor executor) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        var running = new AtomicBoolean(true);
        timer.schedule(() -> {
            running.set(false);
            timer.shutdown();
        }, 3, TimeUnit.SECONDS);

        var completed = new LongAdder();
        long submitted = 0;
        while (running.get()) {
            Runnable task = completed::increment;
            executor.execute(task);
            submitted++;
            if (submitted - completed.longValue() > 10_000) {
                while (submitted - completed.longValue() > 100)
                    LockSupport.parkNanos(1_000);
            }
        }
        System.out.printf(Locale.US, "completed = %,d%n", completed.longValue());
        System.out.printf(Locale.US, "submitted = %,d%n", submitted);
        if (executor instanceof ThreadPoolExecutor tpe) {
            System.out.println("largest pool size = " +
                    tpe.getLargestPoolSize());
        }
        System.out.println();
    }
}