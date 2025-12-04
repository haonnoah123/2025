package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileImporter {
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
}
