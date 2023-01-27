package playground;

import java.util.*;
import java.util.function.*;

public class IDNumberProvidersSynchronized extends AbstractIDNumberProviders {
    // Write
    public synchronized void swap() {
        super.swap();
    }

    // Readonly
    public synchronized void navigate(Consumer<String> action) {
        super.navigate(action);
    }
}
