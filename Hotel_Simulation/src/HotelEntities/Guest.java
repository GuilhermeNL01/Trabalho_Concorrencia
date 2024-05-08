package HotelEntities;

public class Guest extends Thread {
    private final int id; // Identificação única do hóspede
    private final Hotel hotel; // Referência ao hotel
    private Room room; // Quarto do hóspede
    private boolean isHosted = false; // Flag indicando se o hóspede está hospedado
    private Thread thread; // Thread associada ao hóspede
    private Key key; // Chave do quarto do hóspede
    private int complaintAttempts = 2; // Número de tentativas de reclamação

    public Guest(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.room = null;
        this.thread = new Thread(this);
    }

    // Define o quarto para o hóspede
    public void setRoom(Room room){
        this.room = room;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    // Retorna e incrementa o número de tentativas de reclamação restantes
    public int getComplaintAttempts() {
        return complaintAttempts++;
    }

    // Decrementa o número de tentativas de reclamação
    // Se atingir zero, chama o método complain()
    public void decrementComplaintAttempts() {
        complaintAttempts--;
        if(complaintAttempts == 0){
            complain();
        }
    }

    // Retorna a thread associada ao hóspede
    public Thread getThread() {
        return thread;
    }

    // Método de reclamação
    public void complain() {
        System.out.println(this.getId() + ": The Hotel has no available rooms. Terrible, won't come back!");
        Thread.currentThread().interrupt(); // Interrompe a thread
    }

    // Entrega a chave do quarto para o recepcionista
    public void deliverKeyToReceptionist(Receptionist receptionist){
        receptionist.addKey(key);
        this.key = null;
        room.setHasKey(true); // Define que o quarto tem uma chave associada
    }

    // Prepara o hóspede para sair do hotel
    public void leaveHotel(Receptionist receptionist) {
        deliverKeyToReceptionist(receptionist); // Entrega a chave do quarto ao recepcionista
        hotel.removeGuest(this); // Remove o hóspede do hotel
        room.setClean(false); // Marca o quarto como sujo
        room.setAvailable(true); // Marca o quarto como disponível
        Housekeeper housekeeper = hotel.getHousekeeper(); // Obtém a camareira
        System.out.println(this.getId() + " leaving the Hotel");
        housekeeper.cleanRoom(); // Solicita que a camareira limpe o quarto
    }

    // Simula o hóspede saindo para um passeio
    public void goOut(Receptionist receptionist) {
        deliverKeyToReceptionist(receptionist); // Entrega a chave do quarto ao recepcionista
        System.out.println(this.getId() + " going out for a walk");
        Housekeeper housekeeper = hotel.getHousekeeper(); // Obtém a camareira
        room.setClean(false); // Marca o quarto como sujo
        housekeeper.cleanRoom(); // Solicita que a camareira limpe o quarto
        try {
            Thread.sleep(5000); // Simula o hóspede fora do hotel por 5 segundos
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Simula o retorno do hóspede ao quarto
    public void returnToRoom(Key key) {
        this.key = key; // Define a chave do quarto
        System.out.println(this.getId() + " returning to the room");
    }

    // Método executado quando a thread do hóspede é iniciada
    @Override
    public void run() {
        Receptionist receptionist = hotel.getReceptionist(); // Obtém o recepcionista do hotel
        System.out.println("Guest " + id + " arrived at the hotel.");

        // Verifica se há um recepcionista e se ainda há tentativas de reclamação
        if (receptionist != null && complaintAttempts > 0) {
            while (complaintAttempts > 0) {
                receptionist.allocateRoom(this); // Aloca um quarto com o recepcionista
                if (this.key != null) {
                    try {
                        goOut(receptionist); // Simula o hóspede saindo do hotel
                        Thread.sleep(4000); // Simula o tempo fora do hotel
                        if (!room.getBeingCleaned()) {
                            returnToRoom(receptionist.returnKey(room.getNumber())); // Retorna ao quarto
                        }
                        Thread.sleep(4000); // Simula o tempo no quarto
                        leaveHotel(receptionist); // Deixa o hotel
                        break;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    complaintAttempts--; // Decrementa o número de tentativas de reclamação
                }
            }
            if (complaintAttempts == 0) {
                
            }
        }
    }
}

