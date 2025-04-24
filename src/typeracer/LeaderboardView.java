package typeracer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LeaderboardView {

    public static void showLeaderboard(Stage stage) {
        TableView<PlayerScore> table = new TableView<>();

        // 🏷️ Create columns
        TableColumn<PlayerScore, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<PlayerScore, Double> wpmCol = new TableColumn<>("WPM");
        wpmCol.setCellValueFactory(new PropertyValueFactory<>("wpm"));

        TableColumn<PlayerScore, Double> accCol = new TableColumn<>("Accuracy (%)");
        accCol.setCellValueFactory(new PropertyValueFactory<>("accuracy"));

        TableColumn<PlayerScore, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("playDate"));

        // 📐 Set column widths (optional)
        nameCol.setPrefWidth(150);
        wpmCol.setPrefWidth(100);
        accCol.setPrefWidth(120);
        dateCol.setPrefWidth(130);

        table.getColumns().addAll(nameCol, wpmCol, accCol, dateCol);

        // 🌟 Fetch data
        ObservableList<PlayerScore> data = FXCollections.observableArrayList(DatabaseManager.getTopScores());
        table.setItems(data);

        // ✨ Label
        Label title = new Label("🏆 Top 10 Leaderboard");
        title.setFont(new Font("Arial", 24));
        title.setPadding(new Insets(10));
        title.setAlignment(Pos.CENTER);

        // 🧱 Layout
        VBox vbox = new VBox(10, title, table);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // 🎨 Scene setup
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Leaderboard");
        stage.setScene(scene);
        stage.show();
    }
}
