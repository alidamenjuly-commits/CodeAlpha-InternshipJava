import java.time.LocalDate;

public class Reservation implements CsvHandler {

    private String reservationId;
    private String customerName;
    private String roomId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private double totalPrice;

    private String status;

    public Reservation() {
    }

    public Reservation(String reservationId,
                       String customerName,
                       String roomId,
                       LocalDate checkInDate,
                       LocalDate checkOutDate,
                       double totalPrice) {

        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;

        status = "ACTIVE";
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomId() {
        return roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void cancelReservation() {
        status = "CANCELLED";
    }

    @Override
    public String toCSV() {

        return reservationId + "," +
                customerName + "," +
                roomId + "," +
                checkInDate + "," +
                checkOutDate + "," +
                totalPrice + "," +
                status;
    }

    @Override
    public void fromCSV(String data) {

        String[] parts = data.split(",");

        reservationId = parts[0];
        customerName = parts[1];
        roomId = parts[2];
        checkInDate = LocalDate.parse(parts[3]);
        checkOutDate = LocalDate.parse(parts[4]);
        totalPrice = Double.parseDouble(parts[5]);
        status = parts[6];
    }

    @Override
    public String toString() {

        return reservationId +
                " | " +
                customerName +
                " | Room " +
                roomId +
                " | " +
                checkInDate +
                " -> " +
                checkOutDate +
                " | $" +
                totalPrice +
                " | " +
                status;
    }
}