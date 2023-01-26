package playground;

public class InterruptionDemo {
    public static void main(String... args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Running " + Thread.currentThread().isInterrupted());
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("Interrupting thread");
        thread.interrupt();
    }
}
