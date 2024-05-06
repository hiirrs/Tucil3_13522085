import java.util.*;

public class GBFS {
    private static int h_fuction(String current, String end) {
        int count = 0;
        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) != end.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    private static List<String> nextWords(String word, Set<String> wordSet) {
        List<String> nextWordsList = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char charNow = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != charNow) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (wordSet.contains(newWord)) {
                        nextWordsList.add(newWord);
                    }
                }
            }
            chars[i] = charNow;
        }
        return nextWordsList;
    }

    public static Pair<List<String>, Integer> solution(String start, String end, Set<String> wordSet) {
        Map<String, String> wordBefore = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(word -> h_fuction(word, end)));
        Set<String> visited = new HashSet<>();
        int visitedNodes = 0;
        priorityQueue.add(start);
    
        while (!priorityQueue.isEmpty()) {
            String wordNow = priorityQueue.poll();
            visitedNodes++; 
    
            if (wordNow.equals(end)) {
                List<String> path = new ArrayList<>();
                String word = end;
                while (word != null) {
                    path.add(word);
                    word = wordBefore.get(word);
                }
                Collections.reverse(path);
                return new Pair<>(path, visitedNodes);
            }
    
            visited.add(wordNow);
            for (String nextWord : nextWords(wordNow, wordSet)) {
                if (!visited.contains(nextWord)) {
                    if (!wordBefore.containsKey(nextWord)) {
                        priorityQueue.add(nextWord);
                        wordBefore.put(nextWord, wordNow);
                    }
                }
            }
        }
    
        return new Pair<>(null, visitedNodes);
    }
}