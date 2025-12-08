package Tools;

// Java program to implement Graph
// with the help of Generics

import java.util.*;

public class Graph<T> {

    // We use Hashmap to store the edges in the graph
    private Map<T, List<Pair<T, Double>>> map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s) {
        map.put(s, new LinkedList<Pair<T, Double>>());
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(T source, T destination,
            boolean bidirectional, double weight) {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(new Pair<>(destination, weight));
        if (bidirectional == true) {
            map.get(destination).add(new Pair<>(source, weight));
        }
    }

    // This function gives the count of vertices
    public int getVertexCount() {
        return map.keySet().size();
    }

    // This function gives the count of edges
    public int getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        return count;
    }

    // This function gives whether
    // a vertex is present or not.
    public boolean hasVertex(T s) {
        return map.containsKey(s);
    }

    // This function gives whether an edge is present or
    // not.
    public boolean hasEdge(T s, T d) {
        if (!map.containsKey(s))
            return false;
        for (Pair<T, Double> p : map.get(s)) {
            if (p.getLeft().equals(d))
                return true;
        }
        return false;
    }

    public List<T> neighbours(T s) {
        if (!map.containsKey(s))
            return null;
        List<T> neighbors = new ArrayList<>();
        for (Pair<T, Double> p : map.get(s)) {
            neighbors.add(p.getLeft());
        }
        return neighbors;
    }

    public double dijkstra(T start, T end) {
        if (!map.containsKey(start) || !map.containsKey(end))
            return -1.0;
        Map<T, Double> distances = new HashMap<>();
        Set<T> visited = new HashSet<>();
        PriorityQueue<Pair<T, Double>> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::getRight));
        pq.add(Pair.of(start, 0.0));
        distances.put(start, 0.0);
        while (!pq.isEmpty()) {
            Pair<T, Double> currentPair = pq.poll();
            T currentNode = currentPair.getLeft();
            double currentDistance = currentPair.getRight();
            if (visited.contains(currentNode)) {
                continue;
            }
            visited.add(currentNode);
            if (currentNode.equals(end)) {
                return currentDistance;
            }
            for (Pair<T, Double> edge : map.get(currentNode)) {
                T neighbor = edge.getLeft();
                double weight = edge.getRight();
                if (!visited.contains(neighbor)) {
                    double newDist = currentDistance + weight;
                    if (newDist < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        distances.put(neighbor, newDist);
                        pq.add(Pair.of(neighbor, newDist));
                    }
                }
            }
        }
        return -1.0; // Return -1.0 if there is no path from start to end
    }

    // This function returns the sizes of each connected component
    public List<Integer> getComponentSizes() {
        Set<T> visited = new HashSet<>();
        List<Integer> sizes = new ArrayList<>();
        for (T vertex : map.keySet()) {
            if (!visited.contains(vertex)) {
                int size = dfsSize(vertex, visited);
                sizes.add(size);
            }
        }
        return sizes;
    }

    // Helper method for DFS traversal that returns the size of the component
    private int dfsSize(T vertex, Set<T> visited) {
        visited.add(vertex);
        int size = 1;
        for (Pair<T, Double> p : map.get(vertex)) {
            T neighbor = p.getLeft();
            if (!visited.contains(neighbor)) {
                size += dfsSize(neighbor, visited);
            }
        }
        return size;
    }

    // This function counts the number of connected components in the graph
    public int countConnectedComponents() {
        Set<T> visited = new HashSet<>();
        int count = 0;
        for (T vertex : map.keySet()) {
            if (!visited.contains(vertex)) {
                count++;
                dfs(vertex, visited);
            }
        }
        return count;
    }

    // Helper method for DFS traversal
    private void dfs(T vertex, Set<T> visited) {
        visited.add(vertex);
        for (Pair<T, Double> p : map.get(vertex)) {
            T neighbor = p.getLeft();
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (Pair<T, Double> p : map.get(v)) {
                builder.append(p.getLeft().toString() + "(" + p.getRight() + ") ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}