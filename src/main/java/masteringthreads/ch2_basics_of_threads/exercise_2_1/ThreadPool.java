package masteringthreads.ch2_basics_of_threads.exercise_2_1;

import net.jcip.annotations.*;

import java.util.*;
import java.util.concurrent.locks.*;

public class ThreadPool {
    // Create a LinkedList field containing Runnable. This is our "tasks" queue.
    // Hint: Since LinkedList is not thread-safe, we need to synchronize it.
    private final Lock tasksLock = new ReentrantLock();
    private final Condition tasksNotEmpty = tasksLock.newCondition();

    @GuardedBy("tasksLock")
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    // Create an ArrayList containing all the Worker threads.
    // Hint: ArrayList is also not thread-safe, so we need to synchronize it.
    @GuardedBy("workers")
    private final ArrayList<Worker> workers = new ArrayList<>();

    public ThreadPool(int poolSize) {
        // create several Worker threads and add them to workers list
        // Hint: Worker is an inner class defined at the bottom of this class
        for (int i = 0; i < poolSize; i++) {
            var worker = new Worker("worker-" + (i + 1));
            worker.start();
            synchronized (workers) {
                workers.add(worker);
            }
        }
    }

    private Runnable take() throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        // if the LinkedList is empty, we wait
        tasksLock.lock();
        try {
            while (tasks.isEmpty()) tasksNotEmpty.await();
            // remove the first task from the LinkedList and return it
            return tasks.removeFirst();
        } finally {
            tasksLock.unlock();
        }
    }

    public void submit(Runnable task) {
        // Add the task to the LinkedList and notifyAll
        tasksLock.lock();
        try {
            tasks.add(task);
            tasksNotEmpty.signal();
        } finally {
            tasksLock.unlock();
        }
    }

    public int getRunQueueLength() {
        // return the length of the LinkedList
        // remember to also synchronize!
        tasksLock.lock();
        try {
            return tasks.size();
        } finally {
            tasksLock.unlock();
        }
    }

    @SuppressWarnings("deprecation")
    public void shutdown() {
        // this should call stop() on the worker threads.
        synchronized (workers) {
            workers.forEach(Thread::stop);
        }
    }

    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        public void run() {
            // we run in an infinite loop:
            while (true) {
                try {
                    // remove the next task from the linked list using take()
                    Runnable task = take();
                    // we then call the run() method on the task
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
