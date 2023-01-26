package masteringthreads.ch3_the_secrets_of_concurrency.exercise_3_1;

// import masteringthreads.ch2_basics_of_threads.exercise_2_1.ThreadPool;

public class VeryLongTask {
    public static void main(String... args) {
        var pool = new ThreadPool(1);
        pool.submit(() -> {
            try {
                Thread.sleep(10_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }
}
