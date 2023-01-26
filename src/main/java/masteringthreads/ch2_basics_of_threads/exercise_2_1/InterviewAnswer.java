package masteringthreads.ch2_basics_of_threads.exercise_2_1;

public class InterviewAnswer extends InterviewQuestion {
    public String toString() {
        new Thread(() -> {
            System.out.println("incrementing");
            increment();
            System.out.println("done");
        }).start();
        synchronized (this) {
            try {
                System.out.println("Letting go of monitor for a bit");
                this.wait(100);
                System.out.println("Getting monitor again");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return "We got this";
    }

    public static void main(String... args) {
        new InterviewAnswer().foo();
    }
}
