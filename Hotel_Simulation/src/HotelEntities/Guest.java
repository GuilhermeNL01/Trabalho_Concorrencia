package HotelEntities;

import java.util.Random;

public class Guest extends Thread {
    private final int id;
    private final Hotel hotel;
    private Room room;
    private boolean isHosted = false;
    private Thread thread;
    private Key key;
    private int complaintAttempts = 2;
    private int FamilyMembers; // Variável de membro da família

    // Construtor
    public Guest(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.room = null;
        this.thread = new Thread(this);
        this.FamilyMembers = new Random().nextInt(4); // Define o número aleatório de membros da família
    }

    // Define o quarto para o hóspede
    public void setRoom(Room room){
        this.room = room;
    }

    // Define a chave para o hóspede
    public void setKey(Key key) {
        this.key = key;
    }

    // Obtém o número de tentativas de reclamação
    public int getComplaintAttempts() {
        return complaintAttempts++;
    }
     // Decrementa o número de tentativas de reclamação
     public void decrementComplaintAttempts() {
        complaintAttempts--;
        if(complaintAttempts == 0){
            complain();
        }
    }

    // Obtém a thread do hóspede
    public Thread getThread() {
        return thread;
    }

    // Método para reclamar sobre a falta de quartos
    public void complain() {
        System.out.println(this.getId() + ": The Hotel has no available rooms. Terrible, won't come back!");
        Thread.currentThread().interrupt();
    }
}