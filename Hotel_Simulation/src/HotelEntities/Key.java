package HotelEntities;

public class Key {
    private Room room; // Referência para o quarto associado à chave
    private int keyId; // ID único da chave

    // Construtor para inicializar a chave com um quarto específico
    public Key(Room room) {
        this.room = room;
        this.keyId = room.getNumber(); // O ID da chave é definido como o número do quarto
    }

    // Método para obter o ID da chave
    public int getKeyId() {
        return keyId;
    }

    // Método para obter o quarto associado à chave
    public Room getRoom() {
        return room;
    }
}

