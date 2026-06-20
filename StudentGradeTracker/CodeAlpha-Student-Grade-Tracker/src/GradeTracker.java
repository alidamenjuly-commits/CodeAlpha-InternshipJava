public class GradeTracker {

    private GradeBook gradeBook;
    private InputHandler input;
    private ReportPrinter printer;

    public GradeTracker() {
        this.gradeBook = new GradeBook();
        this.input = new InputHandler();
        this.printer = new ReportPrinter();
    }

    public void printStudentList(GradeBook gradeBook) {
        System.out.println("Students:");
        int i = 1;

        for (Student s : gradeBook.getAllStudents()) {
            System.out.println(i + ". " + s.getName());
            i++;
        }

        System.out.println();
    }

    public void addStudent() {
        String name = input.readLine("Enter student name: ");

        if (gradeBook.addStudent(name)) {
            printer.success("Added");
        } else {
            printer.error("Already exists");
        }
    }

    public void addGrade() {
        if (gradeBook.isEmpty()) {
            printer.error("No students available");
            return;
        }

        printStudentList(gradeBook);

        String name = input.readLine("Student name: ");
        Student s = gradeBook.findStudent(name);

        if (s == null) {
            printer.error("Not found");
            return;
        }

        double g = input.readGrade("Grade: ");
        s.addGrade(g);

        printer.success("Added");
    }

    public void viewStudentReport() {
        String name = input.readLine("Student name: ");
        Student s = gradeBook.findStudent(name);

        if (s == null) {
            printer.error("Not found");
        } else {
            printer.printStudentReport(s);
        }
    }

    public void viewSummaryReport() {
        if (gradeBook.isEmpty()) {
            printer.error("No students available");
        } else {
            printer.printSummaryReport(gradeBook);
        }
    }

    public void removeStudent() {
        String name = input.readLine("Student name: ");

        if (gradeBook.removeStudent(name)) {
            printer.success("Removed");
        } else {
            printer.error("Not found");
        }
    }
}