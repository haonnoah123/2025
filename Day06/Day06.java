import java.util.Arrays;
import java.util.List;

import Tools.FileImporter;
import Tools.Tools;

class Day06 {
    public static void main(String[] args) {
        List<String> input = FileImporter.getInput("Day06/Day06Input.txt");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    public static int[][] getDigits(List<String> input) {
        int[][] digits = new int[input.size() - 1][];
        for (int i = 0; i < input.size() - 1; i++) {
            digits[i] = Arrays
                    .stream(input.get(i).split("\\s+"))
                    .filter((e) -> !e.isBlank())
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return digits;
    }

    public static long partTwo(List<String> input) {
        String[] operators = input.getLast().split("\\s+");
        char[][] digits = Tools.inputTo2DCharArray(input);
        long sum = 0;
        long prob = 0;
        int k = 0;
        for (int i = 0; i < digits[0].length; i++) {
            String temp = "";
            for (int j = 0; j < digits.length - 1; j++) {
                temp += digits[j][i];
            }
            if (temp.isBlank()) {
                sum += prob;
                prob = 0;
                k++;
            } else {
                if (operators[k].equals("+"))
                    prob += Integer.parseInt(temp.strip());
                else {
                    prob = Math.max(1, prob);
                    prob *= Integer.parseInt(temp.strip());
                }
            }
        }
        return sum + prob;
    }

    public static long partOne(List<String> input) {
        String[] operators = input.getLast().split("\\s+");
        int[][] digits = getDigits(input);
        long sum = 0;
        for (int i = 0; i < operators.length; i++) {
            long temp = (operators[i].equals("+") ? 0 : 1);
            for (int j = 0; j < digits.length; j++) {
                if (operators[i].equals("+"))
                    temp += digits[j][i];
                else
                    temp *= digits[j][i];
            }
            sum += temp;
        }
        return sum;
    }
}
