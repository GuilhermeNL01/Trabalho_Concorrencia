import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Housekeeper extends Thread{
    private final int id;
    private boolean isCleaning;
    private final Hotel hotel;
    private boolean isAvailable = true;
    private Room beingCleaned;
    private Lock lock;

    public Housekeeper(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isCleaning = false;
        this.lock = new ReentrantLock();
    }
     // Método para verificar se o funcionário está atualmente limpando um quarto
     public boolean isCleaning(){
        return this.isCleaning;
    }

    // Método para definir se o funcionário está atualmente limpando um quarto
    public void setCleaning(boolean isCleaning){
        this.isCleaning = isCleaning;
    }

    // Método para definir se o funcionário está disponível ou não
    public void setAvailable() {
        this.isAvailable = !isAvailable;
    }
     // Método para limpar um quarto
     public void cleanRoom() {
        lock.lock(); // Adquirindo o bloqueio para garantir exclusão mútua
        setAvailable(); // Marcar o funcionário como indisponível enquanto limpa
        this.beingCleaned = hotel.getDirtyRoom(); // Obter um quarto sujo do hotel
        try {
            if (beingCleaned != null) {
                beingCleaned.setBeingCleaned(true); // Marcar o quarto como sendo limpo
                System.out.println("Room cleaning started " + beingCleaned.getNumber());
                Thread.sleep(2000); // Simular o tempo de limpeza
                System.out.println("Room " + beingCleaned.getNumber() + " cleaned.");
                setAvailable(); // Marcar o funcionário como disponível após a limpeza
                beingCleaned.setBeingCleaned(false); // Marcar o quarto como não mais sendo limpo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // Liberando o bloqueio após a conclusão da operação
        }
    }
    
    @Override
    public void run() {
        System.out.println("Housekeeper " + id + " started the shift.");
        while (true) {
            try {
                this.cleanRoom(); // Limpar um quarto durante o turno
                Thread.sleep(new Random().nextInt(5000)); // Simular tempo entre limpezas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

