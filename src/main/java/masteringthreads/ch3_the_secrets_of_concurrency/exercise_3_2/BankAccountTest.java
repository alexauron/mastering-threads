package masteringthreads.ch3_the_secrets_of_concurrency.exercise_3_2;

import java.util.*;
import java.util.concurrent.*;

public class BankAccountTest {
    public static void main(String... args) {
        // create a BankAccount instance
        var account = new BankAccount(1000);
        // create a Runnable lambda
        Runnable depositWithdraw = () -> {
            while(true) {
                account.deposit(100);
                account.withdraw(100);
            }
        };

        new Thread(depositWithdraw, "depositWithdraw-1").start();
        // doSomethingSneaky();
        new Thread(depositWithdraw, "depositWithdraw-2").start();

        ScheduledExecutorService timer = Executors.newScheduledThreadPool(
                1_000_000, r -> Thread.ofVirtual().name("timer-virtual").unstarted(r)
        );
        for (int i = 0; i < 1000; i++) {
            timer.scheduleAtFixedRate(() -> System.out.println(account.getBalance()),
                    1, 1, TimeUnit.SECONDS);
        }
    }
    private static void doSomethingSneaky() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new CancellationException("interrupted");
        }
    }
}
