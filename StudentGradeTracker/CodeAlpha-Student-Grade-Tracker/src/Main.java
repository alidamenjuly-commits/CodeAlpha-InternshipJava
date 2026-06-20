import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        GradeBook gradeBook = new GradeBook();
        InputHandler input = new InputHandler();
        ReportPrinter printer = new ReportPrinter();

        gradeBook.loadFromFile("data.txt");

        int choice;

        do {
            printer.printMenu();
            choice = input.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    String name1 = input.readLine("Enter student name: ");
                    if (gradeBook.addStudent(name1)) {
                        printer.success("Added");
                    } else {
                        printer.error("Already exists");
                    }
                    break;

                case 2:
                    if (gradeBook.isEmpty()) {
                        printer.error("No students available");
                        break;
                    }

                    String name2 = input.readLine("Student name: ");
                    Student s2 = gradeBook.findStudent(name2);

                    if (s2 == null) {
                        printer.error("Not found");
                    } else {
                        double g = input.readGrade("Grade: ");
                        s2.addGrade(g);
                        printer.success("Added");
                    }
                    break;

                case 3:
                    String name3 = input.readLine("Student name: ");
                    Student s3 = gradeBook.findStudent(name3);

                    if (s3 == null) {
                        printer.error("Not found");
                    } else {
                        printer.printStudentReport(s3);
                    }
                    break;

                case 4:
                    if (gradeBook.isEmpty()) {
                        printer.error("No students available");
                    } else {
                        printer.printSummaryReport(gradeBook);
                    }
                    break;

                case 5:
                    String name5 = input.readLine("Student name: ");

                    if (gradeBook.removeStudent(name5)) {
                        printer.success("Removed");
                    } else {
                        printer.error("Not found");
                    }
                    break;

                case 6:
                    if (gradeBook.isEmpty()) {
                        printer.error("No students available");
                        break;
                    }

                    String name6 = input.readLine("Student name: ");
                    Student s6 = gradeBook.findStudent(name6);

                    if (s6 == null) {
                        printer.error("Not found");
                        break;
                    }

                    int i6 = input.readInt("Index to edit: ") - 1;

                    if (i6 < 0 || i6 >= s6.getGrades().size()) {
                        printer.error("Invalid index");
                    } else {
                        double g6 = input.readGrade("New grade: ");
                        s6.updateGrade(i6, g6);
                        printer.success("Updated");
                    }
                    break;

                case 7:
                    if (gradeBook.isEmpty()) {
                        printer.error("No students available");
                        break;
                    }

                    String name7 = input.readLine("Student name: ");
                    Student s7 = gradeBook.findStudent(name7);

                    if (s7 == null) {
                        printer.error("Not found");
                        break;
                    }

                    int i7 = input.readInt("Index to remove: ") - 1;

                    if (i7 < 0 || i7 >= s7.getGrades().size()) {
                        printer.error("Invalid index");
                    } else {
                        s7.removeGrade(i7);
                        printer.success("Grade removed");
                    }
                    break;

                case 8:
                    gradeBook.saveToFile("data.txt");
                    printer.success("Saved");
                    break;
                case 9:
                    if (gradeBook.isEmpty()) {
                        printer.error("No students available");
                    } else {
                        System.out.println("\nStudents:");
                        int i = 1;

                        for (Student s : gradeBook.getAllStudents()) {
                            System.out.println(i + ". " + s.getName());
                            i++;
                        }

                        System.out.println();
                    }
                    break;

                case 10:

                    ArrayList<Student> failing =
                            gradeBook.getFailingStudents();

                    if (failing.isEmpty()) {

                        printer.error("No failing students");

                    } else {

                        System.out.println("\nFailing Students:");

                        for (Student s : failing) {

                            System.out.println(
                                    s.getName()
                                            + " - Average: "
                                            + String.format("%.2f",
                                            s.getAverage()));
                        }

                        System.out.println();
                    }

                    break;

                case 11:
                    gradeBook.saveToFile("data.txt");
                    printer.success("Goodbye");
                    break;

                default:
                    printer.error("Invalid choice");
            }

        } while (choice != 11);

        gradeBook.saveToFile("data.txt");
        input.close();
    }
}