import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelManager {

    private List<Room> rooms;
    private List<Reservation> reservations;

    public HotelManager() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void removeRoom(String roomId) {

        Room roomToRemove = null;

        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                roomToRemove = room;
                break;
            }
        }

        if (roomToRemove != null) {
            rooms.remove(roomToRemove);
            System.out.println("Room removed");
        } else {
            System.out.println("Room not found");
        }
    }
    public void updateRoom(String roomId, String newCategory, double newPrice) {

        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {

                room.fromCSV(
                        room.getRoomId() + "," +
                                room.getRoomNumber() + "," +
                                newCategory + "," +
                                newPrice + "," +
                                room.isBooked()
                );

                System.out.println("Room updated");
                return;
            }
        }

        System.out.println("Room not found");
    }
    public void searchByPrice(double maxPrice) {

        boolean found = false;

        for (Room room : rooms) {

            if (room.getPricePerNight() <= maxPrice) {
                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms found");
        }
    }
    public void searchAvailableRooms() {

        boolean found = false;

        for (Room room : rooms) {

            if (!room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms");
        }
    }

    public Room findRoom(String roomId) {
        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }
        return null;
    }

    public void viewRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void searchRooms(String category) {
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category)) {
                System.out.println(room);
            }
        }
    }

    public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {

        for (Reservation r : reservations) {

            if (!r.getRoomId().equalsIgnoreCase(roomId)) continue;
            if (r.getStatus().equalsIgnoreCase("CANCELLED")) continue;

            boolean overlap =
                    checkIn.isBefore(r.getCheckOutDate()) &&
                            checkOut.isAfter(r.getCheckInDate());

            if (overlap) return false;
        }

        return true;
    }

    public void bookRoom(String customerName,
                         String roomId,
                         LocalDate checkIn,
                         LocalDate checkOut) {

        Room room = findRoom(roomId);

        if (room == null) {
            System.out.println("Room not found");
            return;
        }

        if (!isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Room not available for these dates");
            return;
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        double totalPrice = nights * room.getPricePerNight();

        String reservationId = "RS" + (reservations.size() + 1001);

        Reservation reservation = new Reservation(
                reservationId,
                customerName,
                roomId,
                checkIn,
                checkOut,
                totalPrice
        );

        reservations.add(reservation);

        room.book();

        System.out.println("Reservation Created");
        System.out.println(reservation);
    }

    public void cancelReservation(String reservationId) {

        for (Reservation r : reservations) {

            if (r.getReservationId().equalsIgnoreCase(reservationId)) {

                r.cancelReservation();

                Room room = findRoom(r.getRoomId());
                if (room != null) {
                    room.cancelBooking();
                }

                System.out.println("Cancelled");
                return;
            }
        }

        System.out.println("Not found");
    }

    public void viewReservations() {
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}