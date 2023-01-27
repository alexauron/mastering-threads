package masteringthreads.ch5_threading_problems.exercise_5_2;

import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ProcessTaskManager implements Runnable {
    final ProcessTaskProxy ptp = new ProcessTaskProxy();

    public void run() {
        Map map = new HashMap();
        map.put(Thread.currentThread().getName(), "V-" + Thread.currentThread().getName());
        ThreadLocalContextHolder.put(map);
        ptp.printRCEKey();
    }
}