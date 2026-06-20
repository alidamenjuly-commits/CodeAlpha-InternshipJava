import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        HotelManager hotel = new HotelManager();

        FileManager.loadRooms(hotel.getRooms());
        FileManager.loadReservations(hotel.getReservations());

        if (hotel.getRooms().isEmpty()) {

            hotel.addRoom(new Room("R101", 101, "Standard", 50));
            hotel.addRoom(new Room("R102", 102, "Deluxe", 100));
            hotel.addRoom(new Room("R103", 103, "Suite", 200));
        }

        while (true) {

            System.out.println("\n===== HOTEL SYSTEM =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Search by Category");
            System.out.println("3. Search by Price");
            System.out.println("4. Search Available Rooms");
            System.out.println("5. Add Room");
            System.out.println("6. Update Room");
            System.out.println("7. Remove Room");
            System.out.println("8. Book Room");
            System.out.println("9. Cancel Reservation");
            System.out.println("10. View Reservations");
            System.out.println("11. Save Data");
            System.out.println("12. Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    hotel.viewRooms();
                    break;

                case 2:
                    System.out.print("Category: ");
                    hotel.searchRooms(scanner.nextLine());
                    break;

                case 3:
                    System.out.print("Max Price: ");
                    hotel.searchByPrice(scanner.nextDouble());
                    scanner.nextLine();
                    break;

                case 4:
                    hotel.searchAvailableRooms();
                    break;

                case 5:
                    System.out.print("Room ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Room Number: ");
                    int number = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Category: ");
                    String cat = scanner.nextLine();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    hotel.addRoom(new Room(id, number, cat, price));
                    break;

                case 6:
                    System.out.print("Room ID: ");
                    String uid = scanner.nextLine();

                    System.out.print("New Category: ");
                    String ncat = scanner.nextLine();

                    System.out.print("New Price: ");
                    double nprice = scanner.nextDouble();
                    scanner.nextLine();

                    hotel.updateRoom(uid, ncat, nprice);
                    break;

                case 7:
                    System.out.print("Room ID: ");
                    hotel.removeRoom(scanner.nextLine());
                    break;

                case 8:
                    System.out.print("Customer Name: ");
                    String customer = scanner.nextLine();

                    System.out.print("Room ID: ");
                    String roomId = scanner.nextLine();

                    System.out.print("Check In (YYYY-MM-DD): ");
                    LocalDate checkIn = LocalDate.parse(scanner.nextLine());

                    System.out.print("Check Out (YYYY-MM-DD): ");
                    LocalDate checkOut = LocalDate.parse(scanner.nextLine());

                    hotel.bookRoom(customer, roomId, checkIn, checkOut);
                    break;

                case 9:
                    System.out.print("Reservation ID: ");
                    hotel.cancelReservation(scanner.nextLine());
                    break;

                case 10:
                    hotel.viewReservations();
                    break;

                case 11:
                    FileManager.saveRooms(hotel.getRooms());
                    FileManager.saveReservations(hotel.getReservations());
                    System.out.println("Saved");
                    break;

                case 12:
                    FileManager.saveRooms(hotel.getRooms());
                    FileManager.saveReservations(hotel.getReservations());
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}