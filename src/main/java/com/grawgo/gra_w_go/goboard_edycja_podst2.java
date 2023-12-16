package com.grawgo.gra_w_go;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class goboard_edycja_podst2 extends Application {
    private static final int BOARD_SIZE = 9; // Rozmiar planszy (9x9)
    private static final Color LINE_COLOR = Color.BLACK;
    private static final Color BOARD_COLOR = Color.web("#262626");
    private static final double BOARD_WIDTH = 600.0;
    private static final double BOARD_HEIGHT = 600.0;

    private ObservableList<String> moveHistory = FXCollections.observableArrayList();
    private Group root;
    private boolean isWhiteTurn = false;

    @Override
    public void start(Stage stage) {
        root = new Group();

        Rectangle background = new Rectangle(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        background.setFill(Color.BURLYWOOD);
        root.getChildren().add(background);

        for (int i = 0; i < BOARD_SIZE; i++) {
            double x = (i + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
            Line verticalLine = createLine(x, 0, x, BOARD_HEIGHT);
            root.getChildren().add(verticalLine);
        }

        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            double y = (i + 1.0) * BOARD_HEIGHT / (BOARD_SIZE + 1);
            Line horizontalLine = createLine(0, y, BOARD_WIDTH, y);
            root.getChildren().add(horizontalLine);

            for (int j = 0; j < BOARD_SIZE; j++) {
                double x = (j + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
                Circle circle = createCircle(x, y);
                circle.setFill(Color.BURLYWOOD);
                root.getChildren().add(circle);

                // Obsługa zdarzeń myszy dla okręgu
                circle.setOnMouseEntered(event -> circle.setStroke(Color.RED));
                circle.setOnMouseExited(event -> circle.setStroke(LINE_COLOR));

                // Obsługa zdarzeń kliknięcia myszy dla okręgu
                circle.setOnMouseClicked(event -> {
                    toggleCircleColor(circle);
                    addMoveToHistory(circle);
                    isWhiteTurn = !isWhiteTurn;
                });
            }
        }

        VBox buttonPanel = new VBox();

        Button resetButton = new Button("Reset");
        resetButton.setFont(new Font(15));
        resetButton.setStyle("-fx-background-color: #595959;");
        resetButton.setTextFill(Color.WHITE);
        VBox.setMargin(resetButton, new Insets(10, 20, 10, 20));

        Button giveUpButton = new Button("Poddaj się");
        giveUpButton.setFont(new Font(15));
        giveUpButton.setStyle("-fx-background-color: #595959;");
        giveUpButton.setTextFill(Color.WHITE);
        VBox.setMargin(giveUpButton, new Insets(10, 20, 10, 20));

        resetButton.setOnAction(event -> {
            resetCircleColors(root);
            moveHistory.clear();
            isWhiteTurn = false;
        });

        giveUpButton.setOnAction(event -> showGameOverDialog());

        buttonPanel.getChildren().addAll(resetButton, giveUpButton);

        ListView<String> moveHistoryListView = new ListView<>(moveHistory);
        moveHistoryListView.setPrefHeight(BOARD_HEIGHT);
        moveHistoryListView.setPrefWidth(150);

        VBox container = new VBox();
        container.getChildren().addAll(buttonPanel, moveHistoryListView);
        container.setSpacing(10);

        container.setLayoutX(BOARD_WIDTH + 10);
        container.setLayoutY(50);

        root.getChildren().add(container);

        Scene scene = new Scene(root, BOARD_WIDTH + container.getPrefWidth() + 150, BOARD_HEIGHT);
        stage.setTitle("Go Board");
        scene.setFill(BOARD_COLOR);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void toggleCircleColor(Circle circle) {
        if (isWhiteTurn) {
            circle.setFill(Color.WHITE);
        } else {
            circle.setFill(Color.BLACK);
        }
    }

    private void resetCircleColors(Group root) {
        for (var node : root.getChildren()) {
            if (node instanceof Circle circle) {
                circle.setFill(Color.BURLYWOOD);
            }
        }
    }

    private void addMoveToHistory(Circle circle) {
        int x = (int) circle.getCenterX() / 60;
        int y = (int) circle.getCenterY() / 60;
        String color = (circle.getFill() == Color.BURLYWOOD) ? "Reset" : (isWhiteTurn ? "Biały" : "Czarny");
        String moveInfo = color + ": (" + x + ", " + y + ")";
        moveHistory.add(moveInfo);
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec gry");
        alert.setHeaderText(null);
        alert.setContentText("Partia zakończona. Gracz się poddał.");

        alert.showAndWait();

        System.exit(0);
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(LINE_COLOR);
        line.setStrokeWidth(5);

        return line;
    }

    private Circle createCircle(double centerX, double centerY) {
        Circle circle = new Circle(centerX, centerY, 15);
        circle.setFill(BOARD_COLOR);
        circle.setStroke(LINE_COLOR);
        circle.setStrokeWidth(3);

        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
