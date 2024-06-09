public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(Concurrency::countUp);
        Thread t2 = new Thread(Concurrency::countDown);

        t1.start();
        t2.start();

        // join threads
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Blast off!");
    }
}