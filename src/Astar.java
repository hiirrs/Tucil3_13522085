import java.util.*;

// Kelas Astar yang mengimplementasikan algoritma A* untuk mencari solusi alias jalur dari start word menuju end word
public class Astar {
    // Kelas Node merepresentasikan simpul dengan kata, biaya saat itu, dan prioritasnya
    static class Node implements Comparable<Node> {
        String word; // Kata 
        int cost; // Biaya total sampai node
        int priority; // Prioritas node dalam priority queue

        // Konstruktor Node
        Node(String word, int cost, int priority) {
            this.word = word;
            this.cost = cost;
            this.priority = priority;
        }

        // Metode membandingkan prioritas node (digunakan untuk priority queue)
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // Fungsi heuristik yang menghitung estimasi jarak antara dua kata berdasarkan jumlah karakter yang berbeda
    private static int h_function(String now, String end) {
        int count = 0;
        for (int i = 0; i < now.length(); i++) {
            if (now.charAt(i) != end.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    // Fungsi menghasilkan calon daftar kata-kata yang mungkin menjadi solusi yakni yang memiliki satu perbedaan huruf
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

    // Fungsi untuk menemukan jalur optimal dari kata awal ke kata akhir menggunakan algoritma A*.
    public static Pair<List<String>, Integer> optimumSolution(String start, String end, Set<String> wordSet) {
        Map<String, String> wordBefore = new HashMap<>(); 
        PriorityQueue<Pair<String, Integer>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.getValue() + h_function(pair.getKey(), end))); 
        Set<String> visited = new HashSet<>(); 
        int visitedNodes = 0;

        priorityQueue.add(new Pair<>(start, 0));
        while (!priorityQueue.isEmpty()) {
            Pair<String, Integer> wordPair = priorityQueue.poll();
            String wordNow = wordPair.getKey();
            int currentCost = wordPair.getValue();
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
                int newCost = currentCost + h_function(wordNow, nextWord);
                if (!visited.contains(nextWord) && (!wordBefore.containsKey(nextWord) || newCost < currentCost)) {
                    priorityQueue.add(new Pair<>(nextWord, newCost));
                    wordBefore.put(nextWord, wordNow);
                }
            }
        }
        return new Pair<>(null, visitedNodes);
    }
}
