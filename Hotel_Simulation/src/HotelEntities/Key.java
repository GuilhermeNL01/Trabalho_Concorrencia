package HotelEntities;

public class Key {
    private Room room;
    private int keyId;

    public Key(Room room){
        this.room = room;
        this.keyId = room.getNumber();
    }

    public int getKeyId() {
        return keyId;
    }

    public Room getRoom() {
        return room;
    }
}
