import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Tools.FileImporter;
import Tools.Graph;

class Day11 {

    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day11/Day11Input.txt");
        Graph<String> graph = createGraph(input);
        System.out.println(partOne(graph));
        System.out.println(partTwo(graph));
    }

    public static long partTwo(Graph<String> graph) {
        Set<String> visited = new HashSet<>();
        Map<String, Long> dp = new HashMap<>();

        long FFTtoDACPaths = modifiedDfs("fft", "dac", visited, dp, graph);

        visited.clear();
        dp.clear();
        long SVRtoFFTPaths = modifiedDfs("svr", "fft", visited, dp, graph);

        visited.clear();
        dp.clear();
        long DACtoOUTPaths = modifiedDfs("dac", "out", visited, dp, graph);
        
        return FFTtoDACPaths * SVRtoFFTPaths * DACtoOUTPaths;
    }

    public static long modifiedDfs(String current, String end, Set<String> visited, Map<String, Long> dp,
            Graph<String> graph) {
        if (current.equals(end))
            return 1;

        if (dp.containsKey(current))
            return dp.get(current);

        visited.add(current);

        long count = 0;
        for (String neighbor : graph.neighbours(current)) {
            if (!visited.contains(neighbor)) {
                count += modifiedDfs(neighbor, end, visited, dp, graph);
            }
        }

        visited.remove(current); // Backtrack
        dp.put(current, count);

        return count;
    }

    public static int partOne(Graph<String> graph) {
        return graph.countPaths("you", "out");
    }

    public static Graph<String> createGraph(List<String> input) {
        Graph<String> graph = new Graph<>();

        for (String line : input) {
            String[] parts = line.split(": ");
            String[] parts2 = parts[1].split(" ");
            for (String part : parts2) {
                int weight = parts[0].equals("you") || !part.equals("out") ? 1000 : 1;
                graph.addEdge(parts[0], part, false, weight);
            }
        }

        return graph;
    }

}
