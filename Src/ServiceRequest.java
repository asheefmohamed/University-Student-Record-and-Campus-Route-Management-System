public class ServiceRequest {
    private final int requestId;
    private final int studentId;
    private final String description;

    public ServiceRequest(
            int requestId,
            int studentId,
            String description) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.description = description;
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId
                + " | Student ID: " + studentId
                + " | Description: " + description;
    }
}

class ServiceQueue {
    private static class Node {
        private final ServiceRequest request;
        private Node next;

        private Node(ServiceRequest request) {
            this.request = request;
        }
    }

    private Node front;
    private Node rear;

    public void enqueue(ServiceRequest request) {
        Node newNode = new Node(request);

        if (rear == null) {
            front = newNode;
            rear = newNode;
            return;
        }

        rear.next = newNode;
        rear = newNode;
    }

    public ServiceRequest dequeue() {
        if (front == null) {
            return null;
        }

        ServiceRequest request = front.request;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return request;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("The service queue is empty.");
            return;
        }

        Node current = front;

        while (current != null) {
            System.out.println(current.request);
            current = current.next;
        }
    }
}
