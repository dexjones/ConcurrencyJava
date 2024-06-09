import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Concurrency {
    private static final Lock lock = new ReentrantLock();
    private static final Condition cv = lock.newCondition(); // used to synchronize threads
    private static boolean thread1Done = false;

    public static void countUp() {
        System.out.println("Count up:");

        for (int i = 0; i <= 20; ++i) {
            try {
                Thread.sleep(100); // pause for effect
            } catch (InterruptedException e) { //
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println(i);
            } finally {
                lock.unlock();
            }
        }

        lock.lock(); // lock for thread1Done
        try {
            thread1Done = true;
            cv.signal(); // Notifies second thread to start
        } finally {
            lock.unlock();
        }
    }

    public static void countDown() {
        lock.lock();
        try {
            while (!thread1Done) {
                try {
                    cv.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Count down:");
            for (int i = 20; i >= 0; --i) {
                try {
                    Thread.sleep(100); // pause for effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        } finally {
            lock.unlock();
        }
    }
}
