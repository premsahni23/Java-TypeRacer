package typeracer;

import java.util.Random;

public class WordGenerator {
    private static final String[] WORDS = {
        "java", "typing", "speed", "keyboard", "challenge", "fun", "developer", "random", "code", "monkey",
        "type", "game", "track", "test", "learn", "focus", "accurate", "practice", "flow", "logic"
    };

    public static String generateWords(int count) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            sb.append(WORDS[rand.nextInt(WORDS.length)]).append(" ");
        }
        return sb.toString().trim();
    }
}
