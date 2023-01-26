package playground;

public class Runner {
    private boolean running = true;

    public void doJob() {
        while (isRunning()) {
            // do something
        }
    }

    private synchronized boolean isRunning() {
        return running;
    }

    public synchronized void shutdown() {
        running = false;
    }
}