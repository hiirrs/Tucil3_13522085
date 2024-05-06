import java.util.*;

public class UCS {
    static class Node implements Comparable<Node> {
        String word;
        int cost;

        Node(String word, int cost) {this.word = word; this.cost = cost;}

        @Override
        public int compareTo(Node other) {return Integer.compare(this.cost, other.cost);}
    }

    private static List<String> nextWords(String word, Set<String> wordSet) {
        List<String> nextWordsList = new ArrayList<>();
        char[] wordChars = word.toCharArray();
        for (int i = 0; i < wordChars.length; i++) {
            char charNow = wordChars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != charNow) {
                    wordChars[i] = c;
                    String newWord = new String(wordChars);
                    if (wordSet.contains(newWord)) {
                        nextWordsList.add(newWord);
                    }
                }
            }
            wordChars[i] = charNow;
        }
        return nextWordsList;
    }

    public static Pair<List<String>, Integer> optimumSolution(String start, String end, Set<String> wordSet) {
        Map<String, String> wordBefore = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        priorityQueue.add(new Node(start, 0));
        int visitedNodes = 0;

        while (!priorityQueue.isEmpty()) {
            Node lowestCost = priorityQueue.poll();
            String wordNow = lowestCost.word;
            int currentCost = lowestCost.cost;
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
            for (String newWord : nextWords(wordNow, wordSet)) {
                if (!visited.contains(newWord)) {
                    int newCost = currentCost + 1;
                    if (!wordBefore.containsKey(newWord) || newCost < lowestCost.cost) {
                        priorityQueue.add(new Node(newWord, newCost));
                        wordBefore.put(newWord, wordNow);
                    }
                }
            }
        }

        return new Pair<>(null, visitedNodes);
    }
}