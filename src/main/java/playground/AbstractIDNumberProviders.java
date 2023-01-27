package playground;

import java.util.*;
import java.util.function.*;

public abstract class AbstractIDNumberProviders implements IDNumberProviders {
    private final Deque<String> idNumbers = new ArrayDeque<>();

    public AbstractIDNumberProviders() {
        for (int i = 0; i < 2_000; i++) {
            idNumbers.add("MyId:" + i);
        }
    }

    // Write
    public void swap() {
        for (int i = 0; i < 100; i++) {
            idNumbers.addLast(idNumbers.removeFirst());
        }
    }

    // Readonly
    public void navigate(Consumer<String> action) {
        for (String idNumber : idNumbers) {
            action.accept(idNumber);
        }
    }
}
