import java.util.List;

import Tools.FileImporter;
import Tools.Tools;

class Day04 {

    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day04/Day04Input.txt");
        char[][] graph = Tools.inputTo2DCharArray(input);
        System.out.println(partOne(graph));
        System.out.println(partTwo(graph));
    }

    public static int partOne(char[][] graph) {
        int count = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j] != '@')
                    continue;
                if (canAccess(graph, i, j))
                    count++;
            }
        }
        return count;
    }

    public static int partTwo(char[][] graph) {
        int count = 0;
        int i = 0;
        while (i < graph.length) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j] != '@')
                    continue;
                if (canAccess(graph, i, j)) {
                    count++;
                    graph[i][j] = '.';
                    i = -1;
                    break;
                }
            }
            i++;
        }
        return count;
    }

    public static boolean canAccess(char[][] graph, int i, int j) {
        int count = 0;
        if (i - 1 >= 0 && graph[i - 1][j] == '@')
            count++;
        if (j - 1 >= 0 && graph[i][j - 1] == '@')
            count++;
        if (i - 1 >= 0 && j - 1 >= 0 && graph[i - 1][j - 1] == '@')
            count++;
        if (i + 1 < graph.length && graph[i + 1][j] == '@')
            count++;
        if (j + 1 < graph[0].length && graph[i][j + 1] == '@')
            count++;
        if (i + 1 < graph.length && j + 1 < graph[0].length && graph[i + 1][j + 1] == '@')
            count++;
        if (i + 1 < graph.length && j - 1 >= 0 && graph[i + 1][j - 1] == '@')
            count++;
        if (i - 1 >= 0 && j + 1 < graph[0].length && graph[i - 1][j + 1] == '@')
            count++;
        return count < 4;
    }

}
