package Main;

import HotelEntities.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int NUM_GUESTS = 14;
        List<Guest> guests = new ArrayList<>();
        Hotel hotel = new Hotel();

        // Criando hóspedes
        for (int i = 0; i < NUM_GUESTS; i++) {
            Guest guest = new Guest(i + 1, hotel);
            guests.add(guest);
        }

        // Iniciando as threads dos hóspedes
        for (Guest guest : guests) {
            guest.start();
        }
    }
}
