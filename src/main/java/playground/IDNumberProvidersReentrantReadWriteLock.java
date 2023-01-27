package playground;

import java.util.concurrent.locks.*;
import java.util.function.*;

public class IDNumberProvidersReentrantReadWriteLock extends AbstractIDNumberProviders {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // Write
    public void swap() {
        lock.writeLock().lock();
        try {
            super.swap();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Readonly
    public void navigate(Consumer<String> action) {
        lock.readLock().lock();
        try {
            super.navigate(action);
        } finally {
            lock.readLock().unlock();
        }
    }
}
