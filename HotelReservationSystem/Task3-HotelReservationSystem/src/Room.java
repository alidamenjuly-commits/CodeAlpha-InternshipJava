public class Room {

    private String roomId;
    private int roomNumber;
    private String category;
    private double price;
    private boolean isBooked;

    public Room() {}

    public Room(String roomId, int roomNumber, String category, double price) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isBooked = false;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancelBooking() {
        isBooked = false;
    }

    public String toString() {
        return roomId + " | " + roomNumber + " | " + category + " | $" + price + " | " +
                (isBooked ? "Booked" : "Available");
    }

    public String toCSV() {
        return roomId + "," + roomNumber + "," + category + "," + price + "," + isBooked;
    }

    public void fromCSV(String data) {

        String[] parts = data.split(",");

        roomId = parts[0];
        roomNumber = Integer.parseInt(parts[1]);
        category = parts[2];
        price = Double.parseDouble(parts[3]);

        if (parts.length > 4) {
            isBooked = Boolean.parseBoolean(parts[4]);
        } else {
            isBooked = false;
        }
    }
}