package HotelEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    private int NUM_HOUSEKEEPERS = 5;
    private int NUM_RECEPTIONISTS = 5;
    private int NUM_ROOMS = 10;

    private List<Room> rooms;
    private List<Housekeeper> housekeepers;
    private List<Receptionist> receptionists;
    private List<Key> keys;
    private final List<Guest> waitingList;
    private final List<Guest> allGuestsInHotel;
    private final Lock lock;

    public Hotel() {
        rooms = new ArrayList<>();
        housekeepers = new ArrayList<>();
        receptionists = new ArrayList<>( );
        waitingList = new ArrayList<>();
        keys = new ArrayList<>();
        allGuestsInHotel = new ArrayList<>();
        lock = new ReentrantLock();
        createReceptionists();
        createHousekeepers();
        createRooms();
    }

    public void createHousekeepers(){
        for (int i = 0; i < NUM_HOUSEKEEPERS; i++) {
            housekeepers.add(new Housekeeper(i + 1, this));
        }
        for(Housekeeper housekeeper: housekeepers){
            housekeeper.start();
        }
    }

    public void createReceptionists(){
        for (int i = 0; i < NUM_RECEPTIONISTS; i++) {
            receptionists.add(new Receptionist(i + 1, this));
        }
        for(Receptionist receptionist: receptionists){
            receptionist.start();
        }
    }

    public void createRooms(){
        for (int i = 0; i < NUM_ROOMS; i++) {
            rooms.add(new Room(i + 1, 4)); // Capacity of 4 guests per room
        }
    }


    public List<Housekeeper> getHousekeepers() {
        return housekeepers;
    }

    public List<Receptionist> getReceptionists() {
        return receptionists;
    }

    public List<Guest> getWaitingList() {
        return waitingList;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public Housekeeper getHousekeeper(){
        lock.lock();
        try{
            for(Housekeeper housekeeper: housekeepers){
                if(!housekeeper.isCleaning()){
                    return housekeeper;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Receptionist getReceptionist(){
        lock.lock();
        try {
            for (Receptionist receptionist : receptionists) {
                if (receptionist.isFree()) {
                    return receptionist;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Room getAvailableRoom(){
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    return room;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Room getDirtyRoom(){
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.getHasKey() && !room.isClean()){
                    room.setClean(true);
                    room.setHasKey(false);
                    return room;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public void addToWaitingList(Guest guest){
        if(!this.waitingList.contains(guest)) {
            System.out.println("No available rooms. Guest " + guest.getId() + " is in the waiting list.");
            this.waitingList.add(guest);
        }
    }

    public void removeGuest(Guest guest){
        this.allGuestsInHotel.remove(guest);
    }

    public void allocateRoom(Guest guest) throws InterruptedException {
        lock.lock();
        try {
            Room availableRoom = null;
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    availableRoom = room;
                    break;
                }
            }
            if (availableRoom == null) {
                System.out.println("No available rooms. Guest " + guest.getId() + " is in the waiting list.");
                waitingList.add(guest); // Add to waiting list (can be optimized)
            } else {
                availableRoom.occupy(guest);
                allGuestsInHotel.add(guest);
                System.out.println("Guest " + guest.getId() + " allocated to room " + availableRoom.getNumber());
            }
        } finally {
            lock.unlock();
        }
    }

    public void leave(Guest guest) {
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.guest == guest && allGuestsInHotel.contains(guest)) {
                    room.vacate();
                    room.setClean(false);
                    allGuestsInHotel.remove(guest);
                    System.out.println("Guest " + guest.getId() + " left room " + room.getNumber());
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void cleanRoom() throws InterruptedException {
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.isAvailable() && room.isClean()) {
                    // Simulate cleaning time
                    Thread.sleep(new Random().nextInt(5000));
                    System.out.println("Housekeeper is cleaning room " + room.getNumber());
                    room.setClean(true);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void welcomeGuest() throws InterruptedException {
        lock.lock();
        try {
            if (!waitingList.isEmpty()) {
                Room availableRoom = null;
                for (Room room : rooms) {
                    if (room.isAvailable() && room.isClean()) {
                        availableRoom = room;
                        break;
                    }
                }
                if (availableRoom != null) {
                    availableRoom.occupy(waitingList.get(0));
                    allGuestsInHotel.add(waitingList.get(0));
                    System.out.println("Guest " + waitingList.get(0).getId() + " from waiting list allocated to room " + availableRoom.getNumber());
                    waitingList.remove(0);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
