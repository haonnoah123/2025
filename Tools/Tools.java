package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tools {

    /**
     * Converts each line in the input to an element in the
     * return list
     * 
     * @param file String of file path
     * @return List<String>
     */
    public static List<String> getInput(String file) {
        List<String> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                input.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return input;
    }

    /**
     * converts list of strings to 2d char array
     * each char in each line is its own char in the array
     * 
     * @param input List<String>
     * @return 2D char array
     */
    public static char[][] inputTo2DCharArray(List<String> input) {
        char[][] crossword = new char[input.size()][input.get(0).length()];

        int i = 0;
        for (String line : input) {
            int j = 0;
            for (char c : line.toCharArray()) {
                crossword[i][j] = c;
                j++;
            }
            i++;
        }

        return crossword;
    }

    /**
     * pring char 2D array
     * 
     * @param input 2D array
     */
    public static void print2DArray(char[][] input) {
        for (char[] line : input) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    /**
     * print int 2D array
     * 
     * @param input 2D array
     */
    public static void print2DArray(int[][] input) {
        for (int[] line : input) {
            for (int c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

}
