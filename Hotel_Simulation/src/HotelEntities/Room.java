package HotelEntities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    private final int number; // Número do quarto
    private final int capacity; // Capacidade máxima de hóspedes do quarto
    private final Lock lock; // Objeto de bloqueio para sincronização
    private boolean available; // Indica se o quarto está disponível para ocupação
    private boolean hasKey; // Indica se o quarto possui uma chave associada
    private boolean beingCleaned; // Indica se o quarto está sendo limpo
    public boolean isClean; // Indica se o quarto está limpo
    public Guest guest = null; // Hóspede atualmente no quarto (se houver)
    public Key key; // Chave associada ao quarto

    // Construtor para inicializar o quarto
    public Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.available = true;
        this.isClean = true;
        this.beingCleaned = false;
        key = new Key(this); // Inicializa a chave associada ao quarto
    }

    // Métodos para acessar informações sobre o quarto

    public int getNumber() {
        return number;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isClean() {
        return isClean;
    }

    public boolean getHasKey() {
        return this.hasKey;
    }

    public Key getKey() {
        return key;
    }

    public boolean getBeingCleaned() {
        return this.beingCleaned;
    }

    // Métodos para definir o estado do quarto

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBeingCleaned(boolean beingCleaned) {
        this.beingCleaned = beingCleaned;
    }

    public void setClean(boolean clean) {
        this.isClean = clean;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    // Método para ocupar o quarto com um hóspede

    public void occupy(Guest guest) {
        lock.lock(); // Adquire o bloqueio
        try {
            this.guest = guest; // Define o hóspede atual do quarto
            this.available = false; // Marca o quarto como indisponível
        } finally {
            lock.unlock(); // Libera o bloqueio
        }
        hasKey = false; // Marca que a chave do quarto não está disponível
    }

    // Método para desocupar o quarto

    public void vacate() {
        lock.lock(); // Adquire o bloqueio
        try {
            this.guest = null; // Remove o hóspede do quarto
            this.available = true; // Marca o quarto como disponível
        } finally {
            lock.unlock(); // Libera o bloqueio
        }
    }
}

