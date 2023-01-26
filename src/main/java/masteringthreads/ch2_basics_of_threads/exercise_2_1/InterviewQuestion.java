package masteringthreads.ch2_basics_of_threads.exercise_2_1;

public class InterviewQuestion {
    private int count;
    public final synchronized void foo() {
        count = 42;
        System.out.println(this);
        if (count != 42) System.out.println("You've got a job interview");
    }

    public final synchronized void increment() {
        count++;
    }

}
