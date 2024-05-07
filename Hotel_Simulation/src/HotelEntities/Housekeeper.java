package HotelEntities;

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

    public boolean isCleaning(){
        return this.isCleaning;
    }

    public void setCleaning(boolean isCleaning){
        this.isCleaning = isCleaning;
    }

    public void setAvailable() {
        this.isAvailable = !isAvailable;
    }

    public void cleanRoom() {
        lock.lock();
        setAvailable();
        this.beingCleaned = hotel.getDirtyRoom();
        try {
            if (beingCleaned != null) {
                beingCleaned.setBeingCleaned(true);
                System.out.println("Room cleaning started " + beingCleaned.getNumber());
                Thread.sleep(2000);
                System.out.println("Room " + beingCleaned.getNumber() + " cleaned.");
                setAvailable();
                beingCleaned.setBeingCleaned(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }


    @Override
    public void run() {
        System.out.println("Housekeeper " + id + " started the shift.");
        while (true) {
            try {
                this.cleanRoom();
                Thread.sleep(new Random().nextInt(5000)); // Simulating cleaning time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
