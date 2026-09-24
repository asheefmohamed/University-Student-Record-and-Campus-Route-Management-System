public class StudentBST {
    private static class Node {
        private Student student;
        private Node left;
        private Node right;

        private Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean insert(Student student) {
        if (root == null) {
            root = new Node(student);
            return true;
        }

        return insert(root, student);
    }

    private boolean insert(Node current, Student student) {
        int newId = student.getStudentId();
        int currentId = current.student.getStudentId();

        if (newId < currentId) {
            if (current.left == null) {
                current.left = new Node(student);
                return true;
            }

            return insert(current.left, student);
        }

        if (newId > currentId) {
            if (current.right == null) {
                current.right = new Node(student);
                return true;
            }

            return insert(current.right, student);
        }

        return false;
    }

    public Student search(int studentId) {
        Node current = root;

        while (current != null) {
            int currentId = current.student.getStudentId();

            if (studentId == currentId) {
                return current.student;
            }

            current = studentId < currentId
                    ? current.left
                    : current.right;
        }

        return null;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("The student tree is empty.");
            return;
        }

        displayInOrder(root);
    }

    private void displayInOrder(Node node) {
        if (node == null) {
            return;
        }

        displayInOrder(node.left);
        System.out.println(node.student);
        displayInOrder(node.right);
    }

    public boolean delete(int studentId) {
        if (search(studentId) == null) {
            return false;
        }

        root = delete(root, studentId);
        return true;
    }

    private Node delete(Node node, int studentId) {
        if (node == null) {
            return null;
        }

        int currentId = node.student.getStudentId();

        if (studentId < currentId) {
            node.left = delete(node.left, studentId);
        } else if (studentId > currentId) {
            node.right = delete(node.right, studentId);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            Node successor = findMinimum(node.right);
            node.student = successor.student;
            node.right = delete(
                    node.right,
                    successor.student.getStudentId());
        }

        return node;
    }

    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }
}
