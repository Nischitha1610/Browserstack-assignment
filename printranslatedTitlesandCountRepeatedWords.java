
import java.util.*;

public class WordAnalyzer {
    public static void countRepeatedWords(List<String> titles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : titles) {
            String[] words = title.split("\\s+");
            for (String word : words) {
                word = word.toLowerCase().replaceAll("[^a-zA-Z]", "");  // Normalize
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Find words repeated more than twice
        wordCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
