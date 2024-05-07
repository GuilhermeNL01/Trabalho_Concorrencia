package HotelEntities;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Receptionist extends Thread {
    private final int id;
    private boolean isFree; // Indica se o recepcionista está livre para atender um hóspede
    private Lock lock; // Usado para sincronizar o acesso a recursos compartilhados
    private final Hotel hotel; // Referência ao hotel em que o recepcionista trabalha

    public Receptionist(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isFree = true; // Inicialmente, o recepcionista está livre
        this.lock = new ReentrantLock(); // Inicialização do lock para controle de concorrência
    }

    // Verifica se o recepcionista está livre
    public boolean isFree(){
        return this.isFree;
    }

    // Define o status de ocupação do recepcionista
    public void setFree(boolean isFree){
        this.isFree = isFree;
    }


}
