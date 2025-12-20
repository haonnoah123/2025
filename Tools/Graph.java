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

    // This function removes the edge
    // between source to destination
    public void removeEdge(T source, T destination, boolean bidirectional) {
        if (map.containsKey(source)) {
            map.get(source).removeIf(p -> p.getLeft().equals(destination));
        }
        if (bidirectional == true && map.containsKey(destination)) {
            map.get(destination).removeIf(p -> p.getLeft().equals(source));
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

    // Counts the number of unique simple paths from start to end
    public int countPaths(T start, T end) {
        if (!map.containsKey(start) || !map.containsKey(end))
            return 0;
        Set<T> visited = new HashSet<>();
        return dfsCount(start, end, visited);
    }

    // Helper DFS to count paths
    private int dfsCount(T current, T end, Set<T> visited) {
        if (current.equals(end))
            return 1;
        visited.add(current);
        int count = 0;
        for (Pair<T, Double> p : map.get(current)) {
            T neighbor = p.getLeft();
            if (!visited.contains(neighbor)) {
                count += dfsCount(neighbor, end, visited);
            }
        }
        visited.remove(current); // Backtrack
        return count;
    }

    // Efficient countPaths for DAGs using DP
    public int countPathsEfficient(T start, T end) {
        if (!map.containsKey(start) || !map.containsKey(end))
            return 0;
        if (start.equals(end))
            return 1;
        if (!isDAG()) {
            // Fallback to DFS for graphs with cycles (may be slow)
            return countPaths(start, end);
        }
        // DP: number of ways to reach end from each node
        Map<T, Integer> dp = new HashMap<>();
        Set<T> visited = new HashSet<>();
        return dfsDP(start, end, dp, visited);
    }

    // Helper for DP on DAG
    private int dfsDP(T current, T end, Map<T, Integer> dp, Set<T> visited) {
        if (current.equals(end))
            return 1;
        if (dp.containsKey(current))
            return dp.get(current);
        visited.add(current);
        int count = 0;
        for (Pair<T, Double> p : map.get(current)) {
            T neighbor = p.getLeft();
            if (!visited.contains(neighbor)) {
                count += dfsDP(neighbor, end, dp, visited);
            }
        }
        visited.remove(current);
        dp.put(current, count);
        return count;
    }

    // Check if the graph is a DAG using topological sort
    public boolean isDAG() {
        Map<T, Integer> indegree = new HashMap<>();
        for (T v : map.keySet()) {
            indegree.put(v, 0);
        }
        for (T v : map.keySet()) {
            for (Pair<T, Double> p : map.get(v)) {
                T neighbor = p.getLeft();
                indegree.put(neighbor, indegree.getOrDefault(neighbor, 0) + 1);
            }
        }
        Queue<T> queue = new LinkedList<>();
        for (Map.Entry<T, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            T u = queue.poll();
            count++;
            for (Pair<T, Double> p : map.get(u)) {
                T neighbor = p.getLeft();
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return count == map.size();
    }

    // Edmonds-Karp algorithm for maximum flow
    public double maxFlow(T source, T sink) {
        if (!map.containsKey(source) || !map.containsKey(sink))
            return 0.0;

        // Residual graph: Map<from, Map<to, capacity>>
        Map<T, Map<T, Double>> residual = new HashMap<>();
        for (T u : map.keySet()) {
            residual.put(u, new HashMap<>());
            for (Pair<T, Double> p : map.get(u)) {
                T v = p.getLeft();
                double cap = p.getRight();
                residual.get(u).put(v, cap);
                // Ensure reverse edge exists with 0 capacity
                residual.computeIfAbsent(v, k -> new HashMap<>()).put(u, 0.0);
            }
        }

        double maxFlow = 0.0;
        Map<T, T> parent = new HashMap<>();

        while (bfs(residual, source, sink, parent)) {
            // Find minimum residual capacity along the path
            double pathFlow = Double.MAX_VALUE;
            for (T v = sink; !v.equals(source); v = parent.get(v)) {
                T u = parent.get(v);
                pathFlow = Math.min(pathFlow, residual.get(u).getOrDefault(v, 0.0));
            }

            // Update residual capacities
            for (T v = sink; !v.equals(source); v = parent.get(v)) {
                T u = parent.get(v);
                residual.get(u).put(v, residual.get(u).getOrDefault(v, 0.0) - pathFlow);
                residual.get(v).put(u, residual.get(v).getOrDefault(u, 0.0) + pathFlow);
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // BFS to find if there is a path from source to sink in residual graph
    private boolean bfs(Map<T, Map<T, Double>> residual, T source, T sink, Map<T, T> parent) {
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        parent.put(source, null);

        while (!queue.isEmpty()) {
            T u = queue.poll();
            for (Map.Entry<T, Double> entry : residual.get(u).entrySet()) {
                T v = entry.getKey();
                double cap = entry.getValue();
                if (!visited.contains(v) && cap > 0) {
                    queue.add(v);
                    visited.add(v);
                    parent.put(v, u);
                    if (v.equals(sink))
                        return true;
                }
            }
        }
        return false;
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