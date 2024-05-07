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
}
