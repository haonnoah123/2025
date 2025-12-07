import java.util.List;

import Tools.FileImporter;
import Tools.Tools;

class Day07 {
    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day07/Day07Input.txt");
        char[][] diagram = Tools.inputTo2DCharArray(input);
        System.out.println(partOne(diagram));
        diagram = Tools.inputTo2DCharArray(input);
        System.out.println(partTwo(diagram));
    }

    public static int partOne(char[][] diagram) {
        int start = findStart(diagram);
        return recurseDownward(diagram, 0, start);
    }

    public static long partTwo(char[][] diagram) {
        int start = findStart(diagram);
        long[][] dp = new long[diagram.length][diagram[0].length];
        return dpRecurse(diagram, 0, start, dp) + 1;
    }

    public static long dpRecurse(char[][] diagram, int currRow, int currCol, long[][] dp) {
        if (currRow + 1 == diagram.length || currCol >= diagram[currRow].length || currCol < 0)
            return 0;

        if (diagram[currRow + 1][currCol] == '^') {
            long left = 0;
            long right = 0;

            if (currCol - 1 >= 0) {
                left = (dp[currRow][currCol - 1] == 0 ? dpRecurse(diagram, currRow, currCol - 1, dp)
                        : dp[currRow][currCol - 1]);
                dp[currRow][currCol - 1] = left;
            }

            if (currCol + 1 < diagram.length) {
                right = (dp[currRow][currCol + 1] == 0 ? dpRecurse(diagram, currRow, currCol + 1, dp)
                        : dp[currRow][currCol + 1]);
                dp[currRow][currCol + 1] = right;
            }

            dp[currRow][currCol] = left + right + 1;
        }

        return (dp[currRow][currCol] == 0 ? dpRecurse(diagram, currRow + 1, currCol, dp) : dp[currRow][currCol]);
    }

    public static int findStart(char[][] diagram) {
        for (int i = 0; i < diagram[0].length; i++) {
            if (diagram[0][i] == 'S')
                return i;
        }
        return -1;
    }

    public static int recurseDownward(char[][] diagram, int currRow, int currCol) {
        if (currRow + 1 == diagram.length || currCol >= diagram[currRow].length || currCol < 0
                || diagram[currRow][currCol] == '|')
            return 0;

        diagram[currRow][currCol] = '|';

        if (diagram[currRow + 1][currCol] == '^')
            return recurseDownward(diagram, currRow, currCol + 1) + recurseDownward(diagram, currRow, currCol - 1) + 1;

        return recurseDownward(diagram, currRow + 1, currCol);
    }
}
