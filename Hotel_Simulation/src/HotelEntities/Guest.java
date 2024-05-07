package HotelEntities;

public class Guest extends Thread {
    private final int id;
    private final Hotel hotel;
    private Room room;
    private boolean isHosted = false;
    private Thread thread;
    private Key key;
    private int complaintAttempts = 2;

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

    public int getComplaintAttempts() {
        return complaintAttempts++;
    }

    public void decrementComplaintAttempts() {
        complaintAttempts--;
        if(complaintAttempts == 0){
            complain();
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void complain() {
        System.out.println(this.getId() + ": The Hotel has no available rooms. Terrible, won't come back!");
        Thread.currentThread().interrupt();
    }

    public void deliverKeyToReceptionist(Receptionist receptionist){
        receptionist.addKey(key);
        this.key = null;
        room.setHasKey(true);
    }

    public void leaveHotel(Receptionist receptionist) {
        deliverKeyToReceptionist(receptionist);
        hotel.removeGuest(this);
        room.setClean(false);
        room.setAvailable(true);
        Housekeeper housekeeper = hotel.getHousekeeper();
        System.out.println(this.getId() + " leaving the Hotel");
        housekeeper.cleanRoom();
    }

    public void goOut(Receptionist receptionist) {
        deliverKeyToReceptionist(receptionist);
        System.out.println(this.getId() + " going out for a walk");
        Housekeeper housekeeper = hotel.getHousekeeper();
        room.setClean(false);
        housekeeper.cleanRoom();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToRoom(Key key) {
        this.key = key;
        System.out.println(this.getId() + " returning to the room");
    }

    @Override
    public void run() {
        Receptionist receptionist = hotel.getReceptionist();
        System.out.println("Guest " + id + " arrived at the hotel.");
        if (receptionist != null && complaintAttempts > 0) {
            while (complaintAttempts > 0) {
                receptionist.allocateRoom(this);
                if (this.key != null) {
                    try {
                        goOut(receptionist);
                        Thread.sleep(4000);
                        if (!room.getBeingCleaned()) {
                            returnToRoom(receptionist.returnKey(room.getNumber()));
                        }
                        Thread.sleep(4000);
                        leaveHotel(receptionist);
                        break;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    complaintAttempts--;
                }
            }
            if (complaintAttempts == 0) {
            }
        }
    }
}
