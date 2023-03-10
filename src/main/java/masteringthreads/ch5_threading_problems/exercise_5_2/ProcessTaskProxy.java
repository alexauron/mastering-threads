package masteringthreads.ch5_threading_problems.exercise_5_2;

import java.util.*;

@SuppressWarnings("rawtypes")
public class ProcessTaskProxy {
    public void printRCEKey() {
        Map map = (Map) ThreadLocalContextHolder.get();
        System.out.println("Thread Name : " + Thread.currentThread().getName() + "\t TL Value : " + map.get(Thread.currentThread().getName()));
        ThreadLocalContextHolder.cleanupThread();
    }
}
