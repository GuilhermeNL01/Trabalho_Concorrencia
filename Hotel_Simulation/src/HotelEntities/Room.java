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

    // Adiciona a implementação básica da classe Room com os atributos e métodos necessários.
    public Room(int number, int capacity) {
        this.number = number;
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.available = true;
        this.isClean = true;
        this.beingCleaned = false;
        key = new Key(this);
    }

    // Adiciona métodos para verificar se o quarto está disponível e se está sendo limpo, garantindo consistência nas operações de reserva e limpeza.
    public boolean isAvailable() {
        return available;
    }

    public boolean getBeingCleaned() {
        return this.beingCleaned;
    }


    // métodos para ocupar e desocupar o quarto, gerenciando o estado de disponibilidade e associando hóspedes quando necessário.
    public void occupy(Guest guest) {
        lock.lock();
        try {
            this.guest = guest;
            this.available = false;
        } finally {
            lock.unlock();
        }
        hasKey = false;
    }

    public void vacate() {
        lock.lock();
        try {
            this.guest = null;
            this.available = true;
        } finally {
            lock.unlock();
        }
    }

    // Adiciona métodos para verificar se o quarto está limpo, permitindo que a equipe de limpeza seja notificada quando necessário.
    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        this.isClean = clean;
    }

    // Adiciona métodos para gerenciar a disponibilidade da chave do quarto, garantindo que a chave esteja disponível apenas quando o quarto estiver ocupado.
    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean getHasKey() {
        return this.hasKey;
    }

    public Key getKey() {
        return key;
    }
}
