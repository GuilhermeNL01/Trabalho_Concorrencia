package HotelEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    // Definição dos atributos e constantes
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

    // Método construtor
    public Hotel() {
        rooms = new ArrayList<>();
        housekeepers = new ArrayList<>();
        receptionists = new ArrayList<>();
        waitingList = new ArrayList<>();
        keys = new ArrayList<>();
        allGuestsInHotel = new ArrayList<>();
        lock = new ReentrantLock();
        createReceptionists();
        createHousekeepers();
        createRooms();
    }

    // Método para criar e inicializar os housekeepers
    public void createHousekeepers() {
        for (int i = 0; i < NUM_HOUSEKEEPERS; i++) {
            housekeepers.add(new Housekeeper(i + 1, this));
        }
        for (Housekeeper housekeeper : housekeepers) {
            housekeeper.start();
        }
    }

    // Método para criar e inicializar os receptionists
    public void createReceptionists() {
        for (int i = 0; i < NUM_RECEPTIONISTS; i++) {
            receptionists.add(new Receptionist(i + 1, this));
        }
        for (Receptionist receptionist : receptionists) {
            receptionist.start();
        }
    }

    // Método para criar e inicializar os quartos do hotel
    public void createRooms() {
        for (int i = 0; i < NUM_ROOMS; i++) {
            rooms.add(new Room(i + 1, 4)); // Capacidade de 4 hóspedes por quarto
        }
    }

    // Métodos para acessar os housekeepers, receptionists, e a lista de espera
    public List<Housekeeper> getHousekeepers() {
        return housekeepers;
    }

    public List<Receptionist> getReceptionists() {
        return receptionists;
    }

    public List<Guest> getWaitingList() {
        return waitingList;
    }

    // Método para obter um housekeeper disponível para limpeza
    public Housekeeper getHousekeeper() {
        lock.lock();
        try {
            for (Housekeeper housekeeper : housekeepers) {
                if (!housekeeper.isCleaning()) {
                    return housekeeper;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Método para obter um receptionist disponível para atendimento
    public Receptionist getReceptionist() {
        lock.lock();
        try {
            for (Receptionist receptionist : receptionists) {
                if (receptionist.isFree()) {
                    return receptionist;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Método para obter um quarto disponível
    public Room getAvailableRoom() {
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    return room;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Método para obter um quarto sujo
    public Room getDirtyRoom() {
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.getHasKey() && !room.isClean()) {
                    room.setClean(true);
                    room.setHasKey(false);
                    return room;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Método para adicionar um hóspede à lista de espera
    public void addToWaitingList(Guest guest) {
        if (!this.waitingList.contains(guest)) {
            System.out.println("No available rooms. Guest " + guest.getId() + " is in the waiting list.");
            this.waitingList.add(guest);
        }
    }

    // Método para remover um hóspede do hotel
    public void removeGuest(Guest guest) {
        this.allGuestsInHotel.remove(guest);
    }
    // Método para alocar um quarto para um hóspede
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
                waitingList.add(guest); // Adiciona à lista de espera (pode ser otimizado)
            } else {
                availableRoom.occupy(guest);
                allGuestsInHotel.add(guest);
                System.out.println("Guest " + guest.getId() + " allocated to room " + availableRoom.getNumber());
            }
        } finally {
            lock.unlock();
        }
    }

    // Método para que um hóspede deixe o hotel
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

    // Método para limpar um quarto
    public void cleanRoom() throws InterruptedException {
        lock.lock();
        try {
            for (Room room : rooms) {
                if (room.isAvailable() && room.isClean()) {
                    // Simula o tempo de limpeza
                    Thread.sleep(new Random().nextInt(10000));
                    System.out.println("Housekeeper is cleaning room " + room.getNumber());
                    room.setClean(true);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Método para receber um hóspede da lista de espera
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
