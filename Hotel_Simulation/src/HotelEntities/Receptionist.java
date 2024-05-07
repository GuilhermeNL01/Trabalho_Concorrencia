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

    // Aloca um quarto para um hóspede
    public void allocateRoom(Guest guest){
        lock.lock(); // Inicia o bloqueio para garantir acesso exclusivo
        try{
            if(guest.getComplaintAttempts() > 0) { // Verifica se o hóspede tem tentativas de reclamação restantes
                Room room = hotel.getAvailableRoom(); // Obtém um quarto disponível no hotel
                if (room != null) { // Se houver um quarto disponível
                    Key key = room.getKey(); // Obtém a chave do quarto
                    guest.setRoom(room); // Define o quarto para o hóspede
                    guest.setKey(key); // Define a chave para o hóspede
                    room.setAvailable(false); // Define o quarto como não disponível
                    room.setHasKey(true); // Indica que o quarto possui uma chave associada
                    System.out.println("Room " + room.getNumber() + " allocated to guest " + guest.getId()); // Mensagem de alocação do quarto
                } else {
                    hotel.addToWaitingList(guest); // Se não houver quartos disponíveis, adiciona o hóspede à lista de espera do hotel
                }
            }
        } finally {
            lock.unlock(); // Libera o bloqueio após a execução do código crítico
        }
    }

    // Retorna a chave associada a um determinado número de quarto
    public Key returnKey(int roomNumber) {
        Key key = null;
        lock.lock(); // Inicia o bloqueio para garantir acesso exclusivo
        try {
            for (Key k : hotel.getKeys()) { // Itera sobre as chaves do hotel
                if (k.getKeyId() == roomNumber) { // Se a chave corresponder ao número do quarto
                    key = k; // Armazena a chave correspondente
                    break;
                }
            }
            return key; // Retorna a chave correspondente
        } finally {
            lock.unlock(); // Libera o bloqueio após a execução do código crítico
        }
    }

    // Adiciona uma chave à lista de chaves do hotel
    public void addKey(Key key){
        hotel.getKeys().add(key); // Adiciona a chave à lista de chaves do hotel
    }

    @Override
    public void run() {
        System.out.println("Receptionist " + id + " started the shift."); // Mensagem de início de turno do recepcionista
        while (true) { // Loop infinito para simular o turno contínuo do recepcionista
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(10)); // Simula o tempo de trabalho do recepcionista
                setFree(true); // Define o recepcionista como livre após o tempo de trabalho
                TimeUnit.SECONDS.sleep(new Random().nextInt(10)); // Simula o tempo de pausa do recepcionista
                setFree(false); // Define o recepcionista como ocupado após o tempo de pausa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
