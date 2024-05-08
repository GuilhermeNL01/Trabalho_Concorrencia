package HotelEntities;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Receptionist extends Thread {
    private final int id;
    private boolean isFree; // Indica se o recepcionista está livre para atribuir quartos aos hóspedes
    private Lock lock; // Objeto de bloqueio para sincronização
    private final Hotel hotel; // Referência para o hotel onde o recepcionista trabalha

    // Construtor para inicializar o recepcionista
    public Receptionist(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isFree = true;
        this.lock = new ReentrantLock();
    }

    // Método para verificar se o recepcionista está livre
    public boolean isFree() {
        return this.isFree;
    }

    // Método para definir o status de disponibilidade do recepcionista
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    // Método para alocar um quarto para um hóspede
    public void allocateRoom(Guest guest) {
        lock.lock(); // Adquire o bloqueio
        try {
            if (guest.getComplaintAttempts() > 0) { // Verifica se o hóspede tentou reclamar anteriormente
                Room room = hotel.getAvailableRoom(); // Obtém um quarto disponível do hotel
                if (room != null) { // Se houver um quarto disponível
                    Key key = room.getKey(); // Obtém a chave do quarto
                    guest.setRoom(room); // Define o quarto atribuído ao hóspede
                    guest.setKey(key); // Define a chave atribuída ao hóspede
                    room.setAvailable(false); // Marca o quarto como ocupado
                    room.setHasKey(true); // Marca o quarto como possuindo uma chave
                    System.out.println("Room " + room.getNumber() + " allocated to guest " + guest.getId());
                } else {
                    hotel.addToWaitingList(guest); // Adiciona o hóspede à lista de espera do hotel
                }
            }
        } finally {
            lock.unlock(); // Libera o bloqueio
        }
    }

    // Método para devolver a chave de um quarto
    public Key returnKey(int roomNumber) {
        Key key = null;
        lock.lock(); // Adquire o bloqueio
        try {
            for (Key k : hotel.getKeys()) {
                if (k.getKeyId() == roomNumber) {
                    key = k;
                    break;
                }
            }
            return key;
        } finally {
            lock.unlock(); // Libera o bloqueio
        }
    }

    // Método para adicionar uma chave à lista de chaves do hotel
    public void addKey(Key key) {
        hotel.getKeys().add(key);
    }

    // Método run para iniciar o trabalho do recepcionista
    @Override
    public void run() {
        System.out.println("Receptionist " + id + " started the shift.");
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5)); // Simula o tempo de trabalho do recepcionista
                setFree(true); // Define o recepcionista como livre após o trabalho
                TimeUnit.SECONDS.sleep(new Random().nextInt(5)); // Simula o tempo de pausa
                setFree(false); // Define o recepcionista como ocupado após a pausa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

