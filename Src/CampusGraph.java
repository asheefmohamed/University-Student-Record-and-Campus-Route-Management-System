import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.Stack;

public class CampusGraph {
    private final Map<String, Set<String>> adjacencyList = new LinkedHashMap<>();

    public boolean addLocation(String location) {
        String normalized = normalize(location);

        if (normalized.isEmpty()
                || adjacencyList.containsKey(normalized)) {
            return false;
        }

        adjacencyList.put(normalized, new LinkedHashSet<>());
        return true;
    }

    public boolean removeLocation(String location) {
        String normalized = normalize(location);

        if (!adjacencyList.containsKey(normalized)) {
            return false;
        }

        adjacencyList.remove(normalized);

        for (Set<String> neighbours : adjacencyList.values()) {
            neighbours.remove(normalized);
        }

        return true;
    }

    public boolean addConnection(String first, String second) {
        first = normalize(first);
        second = normalize(second);

        if (first.equals(second)
                || !adjacencyList.containsKey(first)
                || !adjacencyList.containsKey(second)) {
            return false;
        }

        boolean firstAdded = adjacencyList.get(first).add(second);
        boolean secondAdded = adjacencyList.get(second).add(first);

        return firstAdded || secondAdded;
    }

    public boolean removeConnection(String first, String second) {
        first = normalize(first);
        second = normalize(second);

        if (!adjacencyList.containsKey(first)
                || !adjacencyList.containsKey(second)) {
            return false;
        }

        boolean firstRemoved = adjacencyList.get(first).remove(second);

        boolean secondRemoved = adjacencyList.get(second).remove(first);

        return firstRemoved || secondRemoved;
    }

    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations are available.");
            return;
        }

        for (Map.Entry<String, Set<String>> entry : adjacencyList.entrySet()) {
            System.out.println(
                    entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void bfs(String start) {
        start = normalize(start);

        if (!adjacencyList.containsKey(start)) {
            System.out.println("Starting location does not exist.");
            return;
        }

        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        System.out.print("BFS: ");

        while (!queue.isEmpty()) {
            String location = queue.poll();
            System.out.print(location + " ");

            for (String neighbour : adjacencyList.get(location)) {
                if (visited.add(neighbour)) {
                    queue.offer(neighbour);
                }
            }
        }

        System.out.println();
    }

    public void dfs(String start) {
        start = normalize(start);

        if (!adjacencyList.containsKey(start)) {
            System.out.println("Starting location does not exist.");
            return;
        }

        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(start);

        System.out.print("DFS: ");

        while (!stack.isEmpty()) {
            String location = stack.pop();

            if (!visited.add(location)) {
                continue;
            }

            System.out.print(location + " ");

            ArrayList<String> neighbours = new ArrayList<>(adjacencyList.get(location));

            for (int i = neighbours.size() - 1; i >= 0; i--) {
                String neighbour = neighbours.get(i);

                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }

        System.out.println();
    }

    private String normalize(String location) {
        return location == null ? "" : location.trim();
    }
}
