import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordLadderGame {
    public static Set<String> readWordsFromFile(String fileName) throws IOException {
        Set<String> words = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String word;
        while ((word = reader.readLine()) != null) {
            words.add(word.trim().toLowerCase());
        }
        reader.close();
        return words;
    }

    public static boolean isValidWord(String word) {
        return word.matches("[a-zA-Z]+");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Set<String> wordSet = readWordsFromFile("words.txt");
            System.out.print("WELCOME TO THE WORD LADDER GAME!\n");

            String start, end;
            int choice;

            do {
                System.out.print("Enter start word: ");
                start = scanner.nextLine().trim().toLowerCase();
                if (!isValidWord(start)) {
                    System.out.println("Warning: Start word should contain only alphabets.");
                    continue;
                }
                if (!wordSet.contains(start)) {
                    System.out.println("Warning: Start word does not exist in the word list.");
                    continue;
                }
                break;
            } while (true);

            do {
                System.out.print("Enter end word: ");
                end = scanner.nextLine().trim().toLowerCase();
                if (!isValidWord(end)) {
                    System.out.println("Warning: End word should contain only alphabets.");
                    continue;
                }
                if (!wordSet.contains(end)) {
                    System.out.println("Warning: End word does not exist in the word list.");
                    continue;
                }
                break;
            } while (true);

            do {
                System.out.println("Choose Algorithm:");
                System.out.println("1. UCS");
                System.out.println("2. GBFS");
                System.out.println("3. A*");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    continue;
                }
                break;
            } while (true);

            long startTime, endTime;
            Pair<List<String>, Integer> result;

            switch (choice) {
                case 1:
                    System.out.println("UCS:");
                    startTime = System.currentTimeMillis();
                    result = UCS.optimumSolution(start, end, wordSet);
                    endTime = System.currentTimeMillis();
                    System.out.println("Visited nodes count: " + result.getValue());
                    printPath(result.getKey());
                    printExecutionTimeAndPathLength(startTime, endTime, result.getKey());
                    break;
                case 2:
                    System.out.println("Greedy Best First Search:");
                    startTime = System.currentTimeMillis();
                    result = GBFS.solution(start, end, wordSet);
                    endTime = System.currentTimeMillis();
                    System.out.println("Visited nodes count: " + result.getValue());
                    printPath(result.getKey());
                    printExecutionTimeAndPathLength(startTime, endTime, result.getKey());
                    break;
                case 3:
                    System.out.println("A*:");
                    startTime = System.currentTimeMillis();
                    result = Astar.optimumSolution(start, end, wordSet);
                    endTime = System.currentTimeMillis();
                    System.out.println("Visited nodes count: " + result.getValue());
                    printPath(result.getKey());
                    printExecutionTimeAndPathLength(startTime, endTime, result.getKey());
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } catch (IOException e) {
            System.out.println("Error reading words from file: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void printPath(List<String> path) {
        if (path != null) {
            for (String word : path) {
                System.out.print(word + " ");
            }
            System.out.println();
        } else {
            System.out.println("No path found");
        }
        System.out.println();
    }

    private static void printExecutionTimeAndPathLength(long startTime, long endTime, List<String> path) {
        long execution = endTime - startTime;
        if (path != null) {
            System.out.println("Path length: " + path.size());
        } else {
            System.out.println("No path found");
        }
        System.out.println("Execution time: " + execution + " ms\n");
    }
}
