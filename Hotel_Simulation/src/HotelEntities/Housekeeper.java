package HotelEntities;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Housekeeper extends Thread {
    private final int id;
    private boolean isCleaning; // Indica se o housekeeper está atualmente limpando um quarto
    private final Hotel hotel; // Referência para o hotel onde o housekeeper trabalha
    private boolean isAvailable = true; // Indica se o housekeeper está disponível para atribuir tarefas
    private Room beingCleaned; // Referência para o quarto que está sendo limpo atualmente
    private Lock lock; // Objeto de bloqueio para sincronização

    // Construtor para inicializar o housekeeper
    public Housekeeper(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isCleaning = false;
        this.lock = new ReentrantLock();
    }

    // Método para verificar se o housekeeper está atualmente limpando um quarto
    public boolean isCleaning() {
        return this.isCleaning;
    }

    // Método para definir o status de limpeza do housekeeper
    public void setCleaning(boolean isCleaning) {
        this.isCleaning = isCleaning;
    }

    // Método para definir a disponibilidade do housekeeper
    public void setAvailable() {
        this.isAvailable = !isAvailable;
    }

    // Método para realizar a limpeza de um quarto
    public void cleanRoom() {
        lock.lock(); // Adquire o bloqueio
        setAvailable(); // Define o housekeeper como indisponível para outras tarefas
        this.beingCleaned = hotel.getDirtyRoom(); // Obtém um quarto sujo do hotel para limpar
        try {
            if (beingCleaned != null) {
                beingCleaned.setBeingCleaned(true); // Define o quarto como sendo limpo
                System.out.println("Room cleaning started " + beingCleaned.getNumber());
                Thread.sleep(2000); // Simula o tempo de limpeza
                System.out.println("Room " + beingCleaned.getNumber() + " cleaned.");
                setAvailable(); // Define o housekeeper como disponível após a limpeza
                beingCleaned.setBeingCleaned(false); // Define o quarto como não sendo limpo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock(); // Libera o bloqueio
    }

    // Método run para iniciar o trabalho do housekeeper
    @Override
    public void run() {
        System.out.println("Housekeeper " + id + " started the shift.");
        while (true) {
            try {
                this.cleanRoom(); // Limpa um quarto
                Thread.sleep(new Random().nextInt(5000)); // Simula o tempo entre as limpezas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
