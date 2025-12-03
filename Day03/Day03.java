import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Tools.Tools;

class Day03 {
    public static void main(String[] args) {
        List<String> input = Tools.getInput("Day03/Day03Input.txt");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    public static int partOne(List<String> input) {
        int totalOutputJoltage = 0;
        for (String str : input) {
            int[] joltages = Arrays
                    .stream(str.split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int maxTens = 0;
            int maxOnes = 0;
            for (int i = 0; i < joltages.length - 1; i++) {
                int currJoltage = joltages[i];
                if (maxTens < currJoltage) {
                    maxTens = currJoltage;
                    maxOnes = 0;
                } else {
                    maxOnes = Math.max(maxOnes, currJoltage);
                }
            }
            maxOnes = Math.max(maxOnes, joltages[joltages.length - 1]);
            totalOutputJoltage += (maxTens * 10 + maxOnes);
        }
        return totalOutputJoltage;
    }

    public static long partTwo(List<String> input) {
        long totalOutputJoltage = 0;
        for (String str : input) {
            int[] joltages = Arrays
                    .stream(str.split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int lastJPicked = 0;
            int[] maxes = new int[12];
            for (int i = 0; i < maxes.length; i++) {
                for (int j = lastJPicked; j < joltages.length - 11 + i; j++) {
                    if (maxes[i] < joltages[j]) {
                        lastJPicked = j;
                        maxes[i] = joltages[j];
                    }
                }
                lastJPicked++;
            }

            totalOutputJoltage += Long.parseLong(
                    Arrays
                            .stream(maxes)
                            .boxed()
                            .map(String::valueOf)
                            .collect(Collectors.joining()));
        }
        return totalOutputJoltage;
    }
}
