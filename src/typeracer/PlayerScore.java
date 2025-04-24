package typeracer;

import java.time.LocalDate;

public class PlayerScore {
    private String playerName;
    private double wpm;
    private double accuracy;
    private LocalDate playDate;  // ✅ New field for date played

    // ✅ Updated Constructor
    public PlayerScore(String playerName, double wpm, double accuracy, LocalDate playDate) {
        this.playerName = playerName;
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.playDate = playDate;
    }

    // ✅ Getters
    public String getPlayerName() {
        return playerName;
    }

    public double getWpm() {
        return wpm;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public LocalDate getPlayDate() {
        return playDate;
    }

    // (Optional) Setters if needed
}
