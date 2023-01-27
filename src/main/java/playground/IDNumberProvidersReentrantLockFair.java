package playground;

import java.util.concurrent.locks.*;
import java.util.function.*;

public class IDNumberProvidersReentrantLockFair extends AbstractIDNumberProviders {
    private final Lock lock = new ReentrantLock(true);

    // Write
    public void swap() {
        lock.lock();
        try {
            super.swap();
        } finally {
            lock.unlock();
        }
    }

    // Readonly
    public void navigate(Consumer<String> action) {
        lock.lock();
        try {
            super.navigate(action);
        } finally {
            lock.unlock();
        }
    }
}
