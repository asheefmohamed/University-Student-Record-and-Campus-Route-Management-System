public class StudentHashTable {
    private static class Entry {
        private Student student;
        private Entry next;

        private Entry(Student student) {
            this.student = student;
        }
    }

    private final Entry[] buckets;

    public StudentHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Hash-table capacity must be greater than zero.");
        }

        buckets = new Entry[capacity];
    }

    private int hash(int studentId) {
        return Math.floorMod(studentId, buckets.length);
    }

    public boolean put(Student student) {
        int index = hash(student.getStudentId());
        Entry current = buckets[index];

        while (current != null) {
            if (current.student.getStudentId() == student.getStudentId()) {
                current.student = student;
                return false;
            }

            current = current.next;
        }

        Entry newEntry = new Entry(student);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        return true;
    }

    public Student get(int studentId) {
        int index = hash(studentId);
        Entry current = buckets[index];

        while (current != null) {
            if (current.student.getStudentId() == studentId) {
                return current.student;
            }

            current = current.next;
        }

        return null;
    }

    public Student remove(int studentId) {
        int index = hash(studentId);
        Entry current = buckets[index];
        Entry previous = null;

        while (current != null) {
            if (current.student.getStudentId() == studentId) {
                if (previous == null) {
                    buckets[index] = current.next;
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
}
