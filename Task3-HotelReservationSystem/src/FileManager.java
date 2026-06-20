import java.io.*;
import java.util.List;

public class FileManager {

    public static void saveRooms(
            List<Room> rooms) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("rooms.csv"))) {

            for (Room room : rooms) {
                writer.println(room.toCSV());
            }

        } catch (IOException e) {

            System.out.println("Error Saving Rooms");
        }
    }

    public static void loadRooms(List<Room> rooms) {

        File file = new File("rooms.csv");

        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                Room room = new Room();
                room.fromCSV(line);

                rooms.add(room);
            }

        } catch (IOException e) {

            System.out.println("Error Loading Rooms");
        }
    }

    public static void saveReservations(List<Reservation> reservations) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("reservations.csv"))) {

            for (Reservation reservation : reservations) {
                writer.println(reservation.toCSV());
            }

        } catch (IOException e) {

            System.out.println("Error Saving Reservations");
        }
    }

    public static void loadReservations(List<Reservation> reservations) {

        File file = new File("reservations.csv");

        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                Reservation reservation = new Reservation();

                reservation.fromCSV(line);

                reservations.add(reservation);
            }

        } catch (IOException e) {

            System.out.println("Error Loading Reservations");
        }
    }
}
