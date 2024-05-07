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
}









