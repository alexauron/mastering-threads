package playground;

public class AnotherInterruptDemo {
    public static void main(String... args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                System.out.println("before sleep interrupted? " + Thread.currentThread().isInterrupted());
                Thread.sleep(1000);
                System.out.println("after sleep interrupted? " + Thread.currentThread().isInterrupted());
            } catch (InterruptedException e) {
                System.out.println("inside catch interrupted? " + Thread.currentThread().isInterrupted());
            }

            while(!Thread.currentThread().isInterrupted()) {
            }
            System.out.println("after while interrupted? " + Thread.currentThread().isInterrupted());
            Thread.interrupted();
            System.out.println("after Thread.interrupted()? " + Thread.currentThread().isInterrupted());
        });
        t.start();
        Thread.sleep(100);
        t.interrupt();
        Thread.sleep(100);
        t.interrupt();
        t.join();
    }
}
