package Tools;

// Java program to implement Graph
// with the help of Generics

import java.util.*;

class Graph<T> {

    // We use Hashmap to store the edges in the graph
    private Map<T, List<T>> map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s) {
        map.put(s, new LinkedList<T>());
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(T source, T destination,
            boolean bidirectional) {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(destination);
        if (bidirectional == true) {
            map.get(destination).add(source);
        }
    }

    // This function gives the count of vertices
    public void getVertexCount() {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has " + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains " + s
                    + " as a vertex.");
        } else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or
    // not.
    public void hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            System.out.println(
                    "The graph has an edge between " + s
                            + " and " + d + ".");
        } else {
            System.out.println(
                    "The graph has no edge between " + s
                            + " and " + d + ".");
        }
    }

    public void neighbours(T s) {
        if (!map.containsKey(s))
            return;
        System.out.println("The neighbours of " + s + " are");
        for (T w : map.get(s))
            System.out.print(w + ",");
    }

    public int dijkstra(T start, T end) {
        if (!map.containsKey(start) || !map.containsKey(end))
            return -1;
        Map<T, Integer> distances = new HashMap<>();
        Set<T> visited = new HashSet<>();
        PriorityQueue<Pair<T, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getRight));
        pq.add(Pair.of(start, 0));
        distances.put(start, 0);
        while (!pq.isEmpty()) {
            Pair<T, Integer> currentPair = pq.poll();
            T currentNode = currentPair.getLeft();
            int currentDistance = currentPair.getRight();
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);
            if (currentNode.equals(end)) {
                return currentDistance;
            }
            for (T neighbor : map.get(currentNode)) {
                if (!visited.contains(neighbor)) {
                    int newDist = currentDistance + 1; // Assuming each edge has a weight of 1
                    if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        pq.add(Pair.of(neighbor, newDist));
                    }
                }
            }
        }
        return -1; // Return -1 if there is no path from start to end
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}