package HotelEntities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    private final int number;
    private final int capacity;
    private final Lock lock;
    private boolean available;
    private boolean hasKey;
    private boolean beingCleaned;
    public  boolean isClean;
    public Guest guest = null;
    public  Key key;


    public Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.available = true;
        this.isClean = true;
        this.beingCleaned = false;
        key = new Key(this);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBeingCleaned(boolean beingCleaned) {
        this.beingCleaned = beingCleaned;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean){
        this.isClean = clean;
    }

    public boolean getBeingCleaned(){
        return this.beingCleaned;
    }

    public int getNumber() {
        return number;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean getHasKey(){
        return this.hasKey;
    }

    public Key getKey() {
        return key;
    }

    public void occupy(Guest guest) {
        lock.lock();
        try {
            this.guest = guest;
            this.available = false;
        } finally {
            lock.unlock();
        }
        hasKey = false;
    }
    public void vacate() {
        lock.lock();
        try {
            this.guest = null;
            this.available = true;
        } finally {
            lock.unlock();
        }
    }
}
