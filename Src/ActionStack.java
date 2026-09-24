public class ActionStack {
    private static class Node {
        private final String action;
        private Node next;

        private Node(String action) {
            this.action = action;
        }
    }

    private Node top;

    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
    }

    public String pop() {
        if (top == null) {
            return null;
        }

        String action = top.action;
        top = top.next;
        return action;
    }

    public String peek() {
        return top == null ? null : top.action;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("No recent actions.");
            return;
        }

        Node current = top;
        int number = 1;

        while (current != null) {
            System.out.println(number++ + ". " + current.action);
            current = current.next;
        }
    }
}
