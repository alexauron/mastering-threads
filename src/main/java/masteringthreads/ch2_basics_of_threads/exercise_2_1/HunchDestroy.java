package masteringthreads.ch2_basics_of_threads.exercise_2_1;

import java.util.*;
import java.util.concurrent.locks.*;

public class HunchDestroy {
    public static void main(String... args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(1);
        pool.submit(() -> {
            LockSupport.park();
        });

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Adding task to queue");
                pool.submit(() -> System.out.println("Task in the queue"));
                timer.cancel();
            }
        }, 3000);

        Thread.sleep(100);

        System.out.println("Waiting for the tasks queue to become 1");
        int length;
        while ((length = pool.getRunQueueLength()) == 0) ;
        System.out.println("length = " + length);

    }
}
