package masteringthreads.ch4_applied_threading_techniques.exercise_4_1;

import java.util.*;
import java.util.concurrent.*;

// TODO: Replace LinkedList with LinkedBlockingQueue and ArrayList with
// TODO: ConcurrentLinkedQueue
public class ThreadPool {
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final Collection<Worker> workers = new ConcurrentLinkedQueue<>();
    private volatile boolean running = true;

    public ThreadPool(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            var worker = new Worker("worker-" + i);
            worker.start();
            workers.add(worker);
        }
    }

    public void submit(Runnable job) {
        // tasks.add(job); // OK
        boolean success = tasks.offer(job);
        if (!success)
            throw new RejectedExecutionException("task queue is full");
    }

    public int getRunQueueLength() {
        return tasks.size();
    }

    public void shutdown() {
        running = false;
        workers.forEach(Thread::interrupt);
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        public void run() {
            // we run in an infinite loop:
            while (running) {
                // remove the next task from the linked list using take()
                // we then call the run() method on the job
                try {
                    tasks.take().run();
                } catch (InterruptedException consumeAndExit) {
                    break;
                }
            }
        }
    }
}

