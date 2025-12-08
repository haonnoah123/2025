import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Tools.FileImporter;
import Tools.Graph;

class Day08 {
    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day08/Day08Input.txt");
        Point[] points = getPoints(input);
        System.out.println(partOne(points));
        System.out.println(partTwo(points));
    }

    public static long partTwo(Point[] points) {
        List<PointPair> closestPairs = getKClosestPairs(points, Integer.MAX_VALUE);
        Graph<Point> graph = new Graph<>();

        for (Point p : points) {
            graph.addVertex(p);
        }

        PointPair lastPair = null;

        for (PointPair pair : closestPairs) {
            graph.addEdge(pair.p1, pair.p2, true, 1);
            if (graph.countConnectedComponents() == 1) {
                lastPair = pair;
                break;
            }
        }

        return lastPair.p1.x * (long) lastPair.p2.x;
    }

    public static long partOne(Point[] points) {
        List<PointPair> closestPairs = getKClosestPairs(points, 1000);
        Graph<Point> graph = new Graph<>();

        for (PointPair pair : closestPairs) {
            graph.addEdge(pair.p1, pair.p2, true, 1);
        }

        List<Integer> connectedComponentSizes = graph.getComponentSizes();
        connectedComponentSizes.sort((c1, c2) -> c2.compareTo(c1));
        long result = 1;

        for (int i = 0; i < 3; i++) {
            result *= connectedComponentSizes.get(i);
        }

        return result;
    }

    public static List<PointPair> getKClosestPairs(Point[] points, int k) {
        List<PointPair> pairs = new ArrayList<>();

        // Generate all unique pairs (i < j to avoid duplicates)
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distance(points[j]);
                pairs.add(new PointPair(points[i], points[j], dist));
            }
        }

        // Sort pairs by distance ascending
        Collections.sort(pairs, Comparator.comparingDouble(p -> p.distance));

        // Return the first k (or all if fewer than k)
        return pairs.subList(0, Math.min(k, pairs.size()));
    }

    public static Point[] getPoints(List<String> input) {
        Point[] points = new Point[input.size()];
        for (int i = 0; i < input.size(); i++) {
            points[i] = new Point(Arrays.stream(input.get(i).split(",")).mapToInt(Integer::parseInt).toArray());
        }
        return points;
    }
}
