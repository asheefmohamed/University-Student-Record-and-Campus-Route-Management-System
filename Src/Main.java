import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);

    private static final StudentLinkedList STUDENTS =
            new StudentLinkedList();

    private static final StudentBST STUDENT_TREE =
            new StudentBST();

    private static final StudentHashTable STUDENT_HASH_TABLE =
            new StudentHashTable(17);

    private static final ServiceQueue SERVICE_QUEUE =
            new ServiceQueue();

    private static final ActionStack ACTIONS =
            new ActionStack();

    private static final CampusGraph CAMPUS_GRAPH =
            new CampusGraph();

    private static int nextRequestId = 1;

    public static void main(String[] args) {
        try (INPUT) {
            seedCampusLocations();

            boolean running = true;

            while (running) {
                printMenu();
                int choice = readInt("Select an option: ");

                switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> deleteStudent();
                case 4 -> STUDENTS.displayAll();
                case 5 -> addServiceRequest();
                case 6 -> processNextRequest();
                case 7 -> ACTIONS.display();
                case 8 -> STUDENT_TREE.displayInOrder();
                case 9 -> searchUsingHashing();
                case 10 -> addCampusLocation();
                case 11 -> removeCampusLocation();
                case 12 -> addCampusConnection();
                case 13 -> removeCampusConnection();
                case 14 -> CAMPUS_GRAPH.displayConnections();
                case 15 -> traverseCampus();
                case 16 -> {
                    running = false;
                    System.out.println("System closed.");
                }
                default -> System.out.println(
                        "Invalid option. Enter a number from 1 to 16."
                );
                }
            }
        }

    }

    private static void printMenu() {
        System.out.println("\n======================================");
        System.out.println(" UNIVERSITY MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1.  Add Student Record");
        System.out.println("2.  Update Student Record");
        System.out.println("3.  Delete Student Record");
        System.out.println("4.  Display All Records using Linked List");
        System.out.println("5.  Add Service Request to Queue");
        System.out.println("6.  Process Next Service Request");
        System.out.println("7.  Display Recent Actions using Stack");
        System.out.println("8.  Display Students using BST");
        System.out.println("9.  Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS/DFS");
        System.out.println("16. Exit");
        System.out.println("======================================");
    }

    private static void addStudent() {
        int id = readPositiveInt("Student ID: ");

        if (STUDENT_HASH_TABLE.get(id) != null) {
            System.out.println("A student with that ID already exists.");
            return;
        }

        String name = readRequiredText("Name: ");
        String program = readRequiredText("Degree program: ");
        double gpa = readGpa();

        Student student = new Student(id, name, program, gpa);

        STUDENTS.add(student);
        STUDENT_TREE.insert(student);
        STUDENT_HASH_TABLE.put(student);

        ACTIONS.push("Added student " + id + " - " + name);
        System.out.println("Student record added successfully.");
    }

    private static void updateStudent() {
        int id = readPositiveInt("Student ID to update: ");
        Student student = STUDENT_HASH_TABLE.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current record: " + student);

        String name = readRequiredText("New name: ");
        String program = readRequiredText("New degree program: ");
        double gpa = readGpa();

        student.update(name, program, gpa);

        ACTIONS.push("Updated student " + id);
        System.out.println("Student record updated successfully.");
    }

    private static void deleteStudent() {
        int id = readPositiveInt("Student ID to delete: ");
        Student removedStudent = STUDENTS.delete(id);

        if (removedStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        STUDENT_TREE.delete(id);
        STUDENT_HASH_TABLE.remove(id);

        ACTIONS.push("Deleted student " + id);
        System.out.println("Student record deleted successfully.");
    }

    private static void addServiceRequest() {
        int studentId = readPositiveInt("Student ID: ");

        if (STUDENT_HASH_TABLE.get(studentId) == null) {
            System.out.println(
                    "Cannot create request: student does not exist."
            );
            return;
        }

        String description =
                readRequiredText("Request description: ");

        ServiceRequest request = new ServiceRequest(
                nextRequestId++,
                studentId,
                description
        );

        SERVICE_QUEUE.enqueue(request);
        ACTIONS.push(
                "Added service request "
                        + request.getRequestId()
                        + " for student "
                        + studentId
        );

        System.out.println("Service request added to the queue.");
    }

    private static void processNextRequest() {
        ServiceRequest request = SERVICE_QUEUE.dequeue();

        if (request == null) {
            System.out.println("No service requests to process.");
            return;
        }

        ACTIONS.push(
                "Processed service request "
                        + request.getRequestId()
        );

        System.out.println("Processed: " + request);
    }

    private static void searchUsingHashing() {
        int id = readPositiveInt("Student ID to search: ");
        Student student = STUDENT_HASH_TABLE.get(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("Student found: " + student);
        }
    }

    private static void addCampusLocation() {
        String location = readRequiredText("Location name: ");

        if (CAMPUS_GRAPH.addLocation(location)) {
            ACTIONS.push("Added campus location " + location);
            System.out.println("Campus location added.");
        } else {
            System.out.println(
                    "Location is invalid or already exists."
            );
        }
    }

    private static void removeCampusLocation() {
        String location =
                readRequiredText("Location to remove: ");

        if (CAMPUS_GRAPH.removeLocation(location)) {
            ACTIONS.push("Removed campus location " + location);
            System.out.println("Campus location removed.");
        } else {
            System.out.println("Campus location not found.");
        }
    }

    private static void addCampusConnection() {
        String first = readRequiredText("First location: ");
        String second = readRequiredText("Second location: ");

        if (CAMPUS_GRAPH.addConnection(first, second)) {
            ACTIONS.push(
                    "Added campus connection "
                            + first
                            + " <-> "
                            + second
            );

            System.out.println("Campus connection added.");
        } else {
            System.out.println(
                    "Could not add connection. Check both locations."
            );
        }
    }

    private static void removeCampusConnection() {
        String first = readRequiredText("First location: ");
        String second = readRequiredText("Second location: ");

        if (CAMPUS_GRAPH.removeConnection(first, second)) {
            ACTIONS.push(
                    "Removed campus connection "
                            + first
                            + " <-> "
                            + second
            );

            System.out.println("Campus connection removed.");
        } else {
            System.out.println("Connection not found.");
        }
    }

    private static void traverseCampus() {
        String start =
                readRequiredText("Starting location: ");

        int type = readInt("Enter 1 for BFS or 2 for DFS: ");

        switch (type) {
            case 1 -> CAMPUS_GRAPH.bfs(start);
            case 2 -> CAMPUS_GRAPH.dfs(start);
            default -> System.out.println("Invalid traversal option.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String value = INPUT.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int number = readInt(message);

            if (number > 0) {
                return number;
            }

            System.out.println(
                    "The number must be greater than zero."
            );
        }
    }

    private static double readGpa() {
        while (true) {
            System.out.print("GPA (0.00-4.00): ");
            String value = INPUT.nextLine().trim();

            try {
                double gpa = Double.parseDouble(value);

                if (gpa >= 0.0 && gpa <= 4.0) {
                    return gpa;
                }
            } catch (NumberFormatException exception) {
                // The common message below handles the error.
            }

            System.out.println(
                    "Enter a valid GPA between 0.00 and 4.00."
            );
        }
    }

    private static String readRequiredText(String message) {
        while (true) {
            System.out.print(message);
            String value = INPUT.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This value cannot be empty.");
        }
    }

    private static void seedCampusLocations() {
        CAMPUS_GRAPH.addLocation("Main Gate");
        CAMPUS_GRAPH.addLocation("Library");
        CAMPUS_GRAPH.addLocation("Cafeteria");
        CAMPUS_GRAPH.addLocation("Engineering Building");
        CAMPUS_GRAPH.addLocation("Administration");

        CAMPUS_GRAPH.addConnection("Main Gate", "Administration");
        CAMPUS_GRAPH.addConnection("Main Gate", "Library");
        CAMPUS_GRAPH.addConnection("Library", "Cafeteria");
        CAMPUS_GRAPH.addConnection(
                "Library",
                "Engineering Building"
        );
        CAMPUS_GRAPH.addConnection(
                "Administration",
                "Engineering Building"
        );
    }

    /** Simple linked-list implementation used by the student record menu. */
    private static final class StudentLinkedList {
        private Node head;

        private void add(Student student) {
            Node node = new Node(student);

            if (head == null) {
                head = node;
                return;
            }

            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }

        private Student delete(int id) {
            Node previous = null;
            Node current = head;
            Student studentToDelete = STUDENT_HASH_TABLE.get(id);

            while (current != null) {
                if (current.student == studentToDelete) {
                    if (previous == null) {
                        head = current.next;
                    } else {
                        previous.next = current.next;
                    }
                    return current.student;
                }
                previous = current;
                current = current.next;
            }
            return null;
        }

        private void displayAll() {
            if (head == null) {
                System.out.println("No student records found.");
                return;
            }

            Node current = head;
            while (current != null) {
                System.out.println(current.student);
                current = current.next;
            }
        }

        private static final class Node {
            private final Student student;
            private Node next;

            private Node(Student student) {
                this.student = student;
            }
        }
    }
}
