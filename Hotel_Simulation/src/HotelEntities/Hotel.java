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
}



