import java.util.ArrayList;

public class Student {

    private String name;
    private ArrayList<Double> grades = new ArrayList<>();

    public Student(String name) {
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public void removeGrade(int index) {
        if (index >= 0 && index < grades.size()) {
            grades.remove(index);
        }
    }

    public void updateGrade(int index, double grade) {
        if (index >= 0 && index < grades.size()) {
            grades.set(index, grade);
        }
    }

    public boolean hasGrades() {
        return !grades.isEmpty();
    }

    public double getAverage() {
        if (grades.isEmpty())
            return 0;
        double sum = 0;
        for (double g : grades)
            sum += g;
        return sum / grades.size();
    }

    public double getHighest() {
        if (grades.isEmpty()) return 0;
        double max = grades.get(0);
        for (double g : grades)
            if(g > max)
            max = g;
        return max;
    }

    public double getLowest() {
        if (grades.isEmpty()) return 0;
        double min = grades.get(0);
        for (double g : grades)
            if(g < min)
            min = g;
        return min;
    }

    public String getTrend() {

        if (grades.size() < 2)
            return "Not enough data";

        double first = grades.get(0);
        double last = grades.get(grades.size() - 1);

        if (last > first)
            return "Improving";

        if (last < first)
            return "Declining";

        return "Stable";
    }

    public String getLetterGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }

    public String getStatus() {
        return getAverage() >= 60 ? "PASS" : "FAIL";
    }
}