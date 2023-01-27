package playground;

import java.util.function.*;

public interface IDNumberProviders {
    void swap();
    void navigate(Consumer<String> action);
}
