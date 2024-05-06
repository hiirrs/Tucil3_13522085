import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordChecker {
    public static void main(String[] args) {
        String filePath = "words.txt";

        String wordToCheck;

        if (args.length > 0) {
            wordToCheck = args[0];
        } else {
            System.out.print("Enter a word to check: ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                wordToCheck = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
                return;
            }
        }

        if (!wordToCheck.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Word must contain only alphabetic characters.");
            return;
        }

        if (wordExists(filePath, wordToCheck)) {
            System.out.println(wordToCheck + " exists in the words.txt file.");
        } else {
            System.out.println(wordToCheck + " does not exist in the words.txt file.");
        }

        List<String> neighborWords = getNeighborWords(filePath, wordToCheck);
        if (neighborWords.isEmpty()) {
            System.out.println("No neighbor words found for " + wordToCheck);
        } else {
            System.out.println("Neighbor words for " + wordToCheck + ":");
            for (String neighbor : neighborWords) {
                System.out.println(neighbor);
            }
        }
    }

    public static List<String> getNeighborWords(String filePath, String word) {
        List<String> neighborWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == word.length()) {
                    int differences = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (line.charAt(i) != word.charAt(i)) {
                            differences++;
                        }
                        if (differences > 1) {
                            break;
                        }
                    }
                    if (differences == 1) {
                        neighborWords.add(line.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return neighborWords;
    }

    public static boolean wordExists(String filePath, String word) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}