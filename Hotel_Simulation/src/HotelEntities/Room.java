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
    public boolean isClean;
    public Guest guest = null;
    public Key key;

    //Adiciona a implementação básica da classe Room com os atributos e métodos necessários.
    public Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.available = true;
        this.isClean = true;
        this.beingCleaned = false;
        key = new Key(this);
    }

    //Adiciona métodos para verificar se o quarto está disponível e se está sendo limpo, garantindo consistência nas operações de reserva e limpeza.
    public boolean isAvailable() {
        return available;
    }

    public boolean getBeingCleaned() {
        return this.beingCleaned;
    }
}