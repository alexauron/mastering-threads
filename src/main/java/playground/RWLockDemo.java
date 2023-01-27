package playground;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class RWLockDemo {
    public static void main(String... args) {
        List<IDNumberProviders> providers = List.of(
                new IDNumberProvidersReentrantLockFair(),
                new IDNumberProvidersSynchronized(),
                new IDNumberProvidersReentrantLock(),
                new IDNumberProvidersReentrantReadWriteLock()
        );
        for (IDNumberProviders provider : providers) {
            test(provider);
        }
    }

    private static void test(IDNumberProviders provider) {
        System.out.println(provider.getClass().getSimpleName());
        int WRITERS = 2;
        int READERS = 4;

        var timer = new ScheduledThreadPoolExecutor(1);
        var running = new AtomicBoolean(true);
        timer.schedule(() -> {
            running.set(false);
            timer.shutdown();
        }, 5, TimeUnit.SECONDS);

        LongAdder writers = new LongAdder();
        LongAdder readers = new LongAdder();
        try (var pool = Executors.newCachedThreadPool()) {
            for (int i = 0; i < WRITERS; i++) {
                pool.submit(() -> {
                    long count = 0;
                    while (running.get()) {
                        provider.swap();
                        LockSupport.parkNanos(50_000);
                        count++;
                    }
                    writers.add(count);
                });
            }
            for (int i = 0; i < READERS; i++) {
                pool.submit(() -> {
                    long count = 0;
                    long totalLength = 0;
                    while (running.get()) {
                        AtomicLong length = new AtomicLong();
                        provider.navigate(s -> length.addAndGet(s.length()));
                        totalLength += length.get();
                        count++;
                    }
                    readers.add(count);
                });
            }
        }
        System.out.printf(Locale.US, "readers = %,d%n", readers.longValue());
        System.out.printf(Locale.US, "writers = %,d%n", writers.longValue());
        System.out.println();
    }
}
