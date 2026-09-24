public class StudentLinkedList {
    private static class Node {
        private final Student student;
        private Node next;

        private Node(Student student) {
            this.student = student;
        }
    }

    private Node head;

    public boolean add(Student student) {
        if (find(student.getStudentId()) != null) {
            return false;
        }

        Node newNode = new Node(student);

        if (head == null) {
            head = newNode;
            return true;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
        return true;
    }

    public Student find(int studentId) {
        Node current = head;

        while (current != null) {
            if (current.student.getStudentId() == studentId) {
                return current.student;
            }

            current = current.next;
        }

        return null;
    }

    public boolean update(
            int studentId,
            String name,
            String degreeProgram,
            double gpa) {
        Student student = find(studentId);

        if (student == null) {
            return false;
        }

        student.update(name, degreeProgram, gpa);
        return true;
    }

    public Student delete(int studentId) {
        if (head == null) {
            return null;
        }

        if (head.student.getStudentId() == studentId) {
            Student removedStudent = head.student;
            head = head.next;
            return removedStudent;
        }

        Node current = head;

        while (current.next != null) {
            if (current.next.student.getStudentId() == studentId) {
                Student removedStudent = current.next.student;
                current.next = current.next.next;
                return removedStudent;
            }

            current = current.next;
        }

        return null;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No student records are available.");
            return;
        }

        Node current = head;

        while (current != null) {
            System.out.println(current.student);
            current = current.next;
        }
    }
}
