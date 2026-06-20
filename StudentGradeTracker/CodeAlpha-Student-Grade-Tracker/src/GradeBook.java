import java.io.*;
import java.util.ArrayList;

public class GradeBook {

    private ArrayList<Student> students = new ArrayList<>();

    public boolean addStudent(String name) {
        if (findStudent(name) != null) return false;
        students.add(new Student(name));
        return true;
    }

    public boolean removeStudent(String name) {
        Student s = findStudent(name);
        if (s == null)
            return false;
        students.remove(s);
        return true;
    }

    public Student findStudent(String name) {
        for (Student s : students)
            if (s.getName().equalsIgnoreCase(name))
                return s;
        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
    public ArrayList<Student> getFailingStudents() {

        ArrayList<Student> failing = new ArrayList<>();

        for (Student s : students) {
            if (s.hasGrades() && s.getAverage() < 60) {
                failing.add(s);
            }
        }

        return failing;
    }

    public void saveToFile(String file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Student s : students) {
                pw.print(s.getName());
                for (double g : s.getGrades()) {
                    pw.print("," + g);
                }
                pw.println();
            }
        } catch (IOException e) {
            System.out.println("Save error");
        }
    }

    public void loadFromFile(String file) {
        students.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 0) continue;

                Student s = new Student(parts[0]);

                for (int i = 1; i < parts.length; i++) {
                    try {
                        s.addGrade(Double.parseDouble(parts[i]));
                    } catch (Exception ignored) {}
                }

                students.add(s);
            }

        } catch (IOException e) {
            System.out.println("Load error");
        }
    }

    public double getClassAverage() {
        double sum = 0;
        int count = 0;

        for (Student s : students) {
            if (s.hasGrades()) {
                sum += s.getAverage();
                count++;
            }
        }

        return count == 0 ? 0 : sum / count;
    }

    public Student getTopStudent() {
        Student top = null;

        for (Student s : students) {
            if (!s.hasGrades()) continue;
            if (top == null || s.getAverage() > top.getAverage())
                top = s;
        }

        return top;
    }

    public Student getBottomStudent() {
        Student bottom = null;

        for (Student s : students) {
            if (!s.hasGrades()) continue;
            if (bottom == null || s.getAverage() < bottom.getAverage())
                bottom = s;
        }

        return bottom;
    }

    public int getPassCount() {
        int c = 0;

        for (Student s : students)
            if (s.hasGrades() && s.getAverage() >= 60)
                c++;

        return c;
    }

    public int getFailCount() {
        int c = 0;

        for (Student s : students)
            if (s.hasGrades() && s.getAverage() < 60)
                c++;

        return c;
    }
}