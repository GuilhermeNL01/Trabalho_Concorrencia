import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Housekeeper extends Thread{
    private final int id;
    private boolean isCleaning;
    private final Hotel hotel;
    private boolean isAvailable = true;
    private Room beingCleaned;
    private Lock lock;

    public Housekeeper(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isCleaning = false;
        this.lock = new ReentrantLock();
    }
}
