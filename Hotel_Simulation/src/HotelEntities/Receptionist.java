package HotelEntities;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Receptionist extends Thread {
    private final int id;
    private boolean isFree;
    private Lock lock;
    private final Hotel hotel;

    public Receptionist(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isFree = true;
        this.lock = new ReentrantLock();
    }

    public boolean isFree(){
        return this.isFree;
    }

    public void setFree(boolean isFree){
        this.isFree = isFree;
    }

    public void allocateRoom(Guest guest){
        lock.lock();
        try{
            if(guest.getComplaintAttempts() > 0) {
                Room room = hotel.getAvailableRoom();
                if (room != null) {
                    Key key = room.getKey();
                    guest.setRoom(room);
                    guest.setKey(key);
                    room.setAvailable(false);
                    room.setHasKey(true);
                    System.out.println("Room " + room.getNumber() + " allocated to guest " + guest.getId());
                } else {
                    hotel.addToWaitingList(guest);
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public Key returnKey(int roomNumber) {
        Key key = null;
        lock.lock();
        try {
            for (Key k : hotel.getKeys()) {
                if (k.getKeyId() == roomNumber) {
                    key = k;
                    break;
                }
            }
            return key;
        } finally {
            lock.unlock();
        }
    }
    public void addKey(Key chave){
        hotel.getKeys().add(chave);
    }

    @Override
    public void run() {
        System.out.println("Receptionist " + id + " started the shift.");
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5)); // Simulating receptionist's work time
                setFree(true);
                TimeUnit.SECONDS.sleep(new Random().nextInt(5)); // Simulating break time
                setFree(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
