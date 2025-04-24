package typeracer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/typeracerdb";
    private static final String USER = "root";
    private static final String PASSWORD = "merp@2005";

    // ✅ Insert score with date
    public static void insertScore(String name, double wpm, double accuracy) {
        String query = "INSERT INTO leaderboard (player_name, wpm, accuracy, play_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setDouble(2, wpm);
            stmt.setDouble(3, accuracy);
            stmt.setDate(4, Date.valueOf(LocalDate.now())); // ✅ current date

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Retrieve top scores
    public static List<PlayerScore> getTopScores() {
        List<PlayerScore> scores = new ArrayList<>();

        String query = "SELECT player_name, wpm, accuracy, play_date FROM leaderboard ORDER BY wpm DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                scores.add(new PlayerScore(
                    rs.getString("player_name"),
                    rs.getDouble("wpm"),
                    rs.getDouble("accuracy"),
                    rs.getDate("play_date").toLocalDate() // ✅ playDate
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
