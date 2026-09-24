public class Student {
    private final int studentId;
    private String name;
    private String degreeProgram;
    private double gpa;

    public Student(int studentId, String name, String degreeProgram, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.degreeProgram = degreeProgram;
        this.gpa = gpa;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public double getGpa() {
        return gpa;
    }

    public void update(String name, String degreeProgram, double gpa) {
        this.name = name;
        this.degreeProgram = degreeProgram;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Program: %s | GPA: %.2f",
                studentId, name, degreeProgram, gpa);
    }
}
