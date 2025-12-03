import java.util.Arrays;
import java.util.regex.Pattern;

import Tools.Tools;

class Day02 {

    public static void main(String[] args) {
        String input = Tools
                .getInput("Day02/Day02Input.txt")
                .getFirst();
        long[][] bounds = parseInput(input);
        System.out.println(solver(bounds, "^(\\d+)\\1$")); // part one
        System.out.println(solver(bounds, "^(\\d+)\\1+$")); // part two
    }

    public static long solver(long[][] bounds, String regex) {
        Pattern pattern = Pattern.compile(regex);
        long count = 0;
        for (int i = 0; i < bounds.length; i++) {
            for (long j = bounds[i][0]; j <= bounds[i][1]; j++) {
                if (pattern.matcher(j + "").find())
                    count += j;
            }
        }
        return count;
    }

    public static long[][] parseInput(String input) {
        String[] split = input
                .split(",");
        long[][] parsed = new long[split.length][2];

        for (int i = 0; i < split.length; i++) {
            parsed[i] = Arrays
                    .stream(split[i].split("-"))
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        return parsed;
    }

}
