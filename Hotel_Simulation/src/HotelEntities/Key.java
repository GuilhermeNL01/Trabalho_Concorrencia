package HotelEntities;

public class Key {
    private Room room; // Referência para o quarto associado a esta chave
    private int keyId; // Identificador único da chave

    // Construtor
    public Key(Room room){
        this.room = room; // Define o quarto associado a esta chave
        this.keyId = room.getNumber(); // O identificador da chave é o número do quarto
    }

    // Método para obter o identificador da chave
    public int getKeyId() {
        return keyId;
    }

    // Método para obter o quarto associado a esta chave
    public Room getRoom() {
        return room;
    }
}
