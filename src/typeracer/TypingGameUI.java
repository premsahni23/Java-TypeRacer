package typeracer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TypingGameUI extends Application {

    private static final int PARAGRAPH_WORDS = 30;

    private TextFlow displayFlow;
    private Label timerLabel, wpmLabel, accuracyLabel;
    private TextField hiddenInput;
    private Timeline timeline;
    private int secondsElapsed = 0;
    private String paragraph;
    private int currentIndex = 0;
    private double finalWPM = 0;
    private double finalAccuracy = 0;

    private boolean isDarkMode = false;
    private VBox layout;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        displayFlow = new TextFlow();
        displayFlow.setPadding(new Insets(20));
        displayFlow.setTextAlignment(TextAlignment.CENTER);
        displayFlow.setLineSpacing(10);
        displayFlow.setPrefHeight(180);

        timerLabel = createStyledLabel("Time: 0s");
        wpmLabel = createStyledLabel("WPM: 0");
        accuracyLabel = createStyledLabel("Accuracy: 0%");

        Button startButton = new Button("Start");
        styleButton(startButton, "#4CAF50");
        startButton.setOnAction(e -> startGame());

        Button leaderboardButton = new Button("Leaderboard");
        styleButton(leaderboardButton, "#2196F3");
        leaderboardButton.setOnAction(e -> LeaderboardView.showLeaderboard(new Stage()));

        Button toggleModeButton = new Button("Toggle Dark Mode");
        styleButton(toggleModeButton, "#555555");
        toggleModeButton.setOnAction(e -> toggleTheme());

        hiddenInput = new TextField();
        hiddenInput.setOpacity(0);
        hiddenInput.setFocusTraversable(true);

        hiddenInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE && currentIndex > 0) {
                currentIndex--;
                resetCharColor(currentIndex);
                e.consume();
            }
        });

        hiddenInput.setOnKeyTyped(e -> {
            if (currentIndex >= paragraph.length()) return;

            char typed = e.getCharacter().charAt(0);
            if (typed == '\b') return;

            updateTextFlow(currentIndex, typed);
            currentIndex++;

            updateTypingStats();

            if (currentIndex >= paragraph.length()) {
                hiddenInput.setDisable(true);
                if (timeline != null) timeline.stop();
                finalWPM = calculateWPM();
                finalAccuracy = calculateAccuracy();
                promptSaveScore();
            }

            e.consume();
        });

        HBox statsBox = new HBox(30, timerLabel, wpmLabel, accuracyLabel);
        statsBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(20, startButton, leaderboardButton, toggleModeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        layout = new VBox(30, displayFlow, hiddenInput, statsBox, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(40));

        scene = new Scene(layout, 950, 500);
        applyTheme();

        stage.setScene(scene);
        stage.setTitle("TypeRacer JavaFX");
        stage.show();
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyTheme();
    }

    private void applyTheme() {
        if (isDarkMode) {
            layout.setStyle("-fx-background-color: #1e1e1e;");
            displayFlow.setStyle("-fx-background-color: #2b2b2b; -fx-border-color: #444; -fx-border-radius: 12; -fx-background-radius: 12;");
            timerLabel.setTextFill(Color.LIGHTGRAY);
            wpmLabel.setTextFill(Color.LIGHTGRAY);
            accuracyLabel.setTextFill(Color.LIGHTGRAY);
        } else {
            layout.setStyle("-fx-background-color: #f7f9fc;");
            displayFlow.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 12; -fx-background-radius: 12;");
            timerLabel.setTextFill(Color.BLACK);
            wpmLabel.setTextFill(Color.BLACK);
            accuracyLabel.setTextFill(Color.BLACK);
        }
    }

    private void startGame() {
        paragraph = WordGenerator.generateWords(PARAGRAPH_WORDS);
        displayFlow.getChildren().clear();
        for (char c : paragraph.toCharArray()) {
            Text t = new Text(String.valueOf(c));
            t.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 24));
            t.setFill(Color.GRAY);
            displayFlow.getChildren().add(t);
        }

        currentIndex = 0;
        secondsElapsed = 0;
        hiddenInput.setDisable(false);
        hiddenInput.requestFocus();

        timerLabel.setText("Time: 0s");
        wpmLabel.setText("WPM: 0");
        accuracyLabel.setText("Accuracy: 0%");

        if (timeline != null) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            timerLabel.setText("Time: " + secondsElapsed + "s");
            updateTypingStats();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTextFlow(int index, char typedChar) {
        Text current = (Text) displayFlow.getChildren().get(index);
        char actualChar = paragraph.charAt(index);

        if (typedChar == actualChar) {
            current.setFill(Color.LIMEGREEN);
            current.setUnderline(false);
        } else {
            current.setFill(Color.CRIMSON);
            current.setUnderline(true);
        }
    }

    private void resetCharColor(int index) {
        Text current = (Text) displayFlow.getChildren().get(index);
        current.setFill(Color.GRAY);
        current.setUnderline(false);
    }

    private void updateTypingStats() {
        double accuracy = calculateAccuracy();
        double wpm = calculateWPM();

        accuracyLabel.setText(String.format("Accuracy: %.2f%%", accuracy));
        wpmLabel.setText(String.format("WPM: %.2f", wpm));
    }

    private double calculateAccuracy() {
        int correct = 0;
        for (int i = 0; i < currentIndex; i++) {
            Text t = (Text) displayFlow.getChildren().get(i);
            if (t.getFill().equals(Color.LIMEGREEN)) correct++;
        }
        return currentIndex == 0 ? 0 : (correct * 100.0 / currentIndex);
    }

    private double calculateWPM() {
        int correct = 0;
        for (int i = 0; i < currentIndex; i++) {
            Text t = (Text) displayFlow.getChildren().get(i);
            if (t.getFill().equals(Color.LIMEGREEN)) correct++;
        }
        int wordCount = correct / 5;
        return secondsElapsed == 0 ? 0 : (wordCount * 60.0 / secondsElapsed);
    }

    private void promptSaveScore() {
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Game Over!");
        dialog.setContentText("Enter your name:");

        dialog.showAndWait().ifPresent(name -> {
            DatabaseManager.insertScore(name, finalWPM, finalAccuracy);
        });
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return label;
    }

    private void styleButton(Button btn, String bgColor) {
        btn.setStyle("-fx-background-color: " + bgColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 20;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #333;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 20;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + bgColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 20;"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
