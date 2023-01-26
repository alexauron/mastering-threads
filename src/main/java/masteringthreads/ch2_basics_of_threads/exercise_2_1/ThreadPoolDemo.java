package masteringthreads.ch2_basics_of_threads.exercise_2_1;

import java.time.*;
import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String... args) throws ExecutionException, InterruptedException {
        ThreadPool pool = new ThreadPool(4);
        FutureTask<LocalDateTime> whatIsNow = new FutureTask<>(
                new Callable<LocalDateTime>() {
                    public LocalDateTime call() throws Exception {
                        System.out.println("Sleeping " + Thread.currentThread());
                        Thread.sleep(5000);
                        System.out.println("Awoke " + Thread.currentThread());
                        return LocalDateTime.now();
                    }
                }
        );
        pool.submit(whatIsNow);
        System.out.println("Waiting for answer ...");
        System.out.println(whatIsNow.get());
        pool.shutdown();
    }
}
