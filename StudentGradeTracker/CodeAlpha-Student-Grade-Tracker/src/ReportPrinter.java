public class ReportPrinter {

    public void printBanner() {
        System.out.println("=== STUDENT GRADE TRACKER ===\n");
    }

    public void printMenu() {
        System.out.println("--- MAIN MENU ---");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grade");
        System.out.println("3. View Student Report");
        System.out.println("4. Summary Report");
        System.out.println("5. Remove Student");
        System.out.println("6. Edit Grade");
        System.out.println("7. Remove Grade");
        System.out.println("8. Save");
        System.out.println("9. List all Students");
        System.out.println("10. View Failing Students");
        System.out.println("11. Save & Exit\n");
    }

    public void printStudentReport(Student s) {
        System.out.println("--- Report for " + s.getName() + " ---");
        System.out.println("Trend  : " + s.getTrend());

        if (!s.hasGrades()) {
            System.out.println("No grades recorded.\n");
            return;
        }

        for (int i = 0; i < s.getGrades().size(); i++) {
            System.out.println((i + 1) + ". " + String.format("%.2f", s.getGrades().get(i)));
        }

        System.out.println("Average: " + String.format("%.2f", s.getAverage()));
        System.out.println("Highest: " + String.format("%.2f", s.getHighest()));
        System.out.println("Lowest : " + String.format("%.2f", s.getLowest()));
        System.out.println("Grade  : " + s.getLetterGrade());
        System.out.println("Status : " + s.getStatus() + "\n");
    }

    public void printSummaryReport(GradeBook g) {

        System.out.println("--- CLASS SUMMARY ---");
        System.out.println("Class Average: " + String.format("%.2f", g.getClassAverage()));

        Student top = g.getTopStudent();
        Student bottom = g.getBottomStudent();

        if (top != null)
            System.out.println("Top Student: " + top.getName());

        if (bottom != null)
            System.out.println("Lowest Student: " + bottom.getName());

        System.out.println("Passing: " + g.getPassCount());
        System.out.println("Failing: " + g.getFailCount() + "\n");
    }

    public void success(String msg) {
        System.out.println("[OK] " + msg + "\n");
    }

    public void error(String msg) {
        System.out.println("[ERROR] " + msg + "\n");
    }
}