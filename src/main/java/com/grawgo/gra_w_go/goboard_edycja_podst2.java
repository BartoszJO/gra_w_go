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
    private Group root; // Dodaj pole root jako zmienną instancji

    @Override
    public void start(Stage stage) {
        root = new Group(); // Inicjalizuj root jako nową instancję Group

        // Dodaj szare tło pod planszą
        Rectangle background = new Rectangle(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        background.setFill(Color.BURLYWOOD);
        root.getChildren().add(background);

        // Równomierne rozmieszczenie linii pionowych
        for (int i = 0; i < BOARD_SIZE; i++) {
            double x = (i + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
            Line verticalLine = createLine(x, 0, x, BOARD_HEIGHT);
            root.getChildren().add(verticalLine);
        }

        // Równomierne rozmieszczenie linii poziomych
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            double y = (i + 1.0) * BOARD_HEIGHT / (BOARD_SIZE + 1);
            Line horizontalLine = createLine(0, y, BOARD_WIDTH, y);
            root.getChildren().add(horizontalLine);

            // Dodanie okręgu nad przecięciem linii poziomej
            for (int j = 0; j < BOARD_SIZE; j++) {
                double x = (j + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
                Circle circle = createCircle(x, y);
                circle.setFill(Color.BURLYWOOD);
                root.getChildren().add(circle);

                // Obsługa zdarzeń myszy dla okręgu
                circle.setOnMouseEntered(event -> circle.setStroke(Color.RED));
                circle.setOnMouseExited(event -> circle.setStroke(Color.BLACK));

                // Obsługa zdarzeń kliknięcia myszy dla okręgu
                circle.setOnMouseClicked(event -> {
                    String color = (event.isShiftDown()) ? "Czarny" : "Biały";
                    if (event.isAltDown()) {
                        color = "Reset";
                    }
                    setCircleFillColor(circle, color);
                    addMoveToHistory(color, circle);
                });
            }
        }

        // Dodanie panelu z przyciskami "Biały", "Czarny", "Reset", "Poddaj się"
        // i historią ruchów po prawej stronie
        VBox buttonPanel = new VBox();
        Button whiteButton = new Button("Biały");
        whiteButton.setFont(new Font(15));
        whiteButton.setStyle("-fx-background-color: #595959;");
        whiteButton.setTextFill(Color.WHITE);
        VBox.setMargin(whiteButton, new Insets(10, 20, 10, 20));

        Button blackButton = new Button("Czarny");
        blackButton.setFont(new Font(15));
        blackButton.setStyle("-fx-background-color: #595959;");
        blackButton.setTextFill(Color.WHITE);
        VBox.setMargin(blackButton, new Insets(10, 20, 10, 20));

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

        whiteButton.setOnAction(event -> setChangeColorMode("Biały"));
        blackButton.setOnAction(event -> setChangeColorMode("Czarny"));
        resetButton.setOnAction(event -> {
            resetCircleColors(root);
            moveHistory.clear(); // Czyść historię ruchów
        });
        giveUpButton.setOnAction(event -> showGameOverDialog());

        buttonPanel.getChildren().addAll(whiteButton, blackButton, resetButton, giveUpButton);

        // Dodanie listy historii ruchów
        ListView<String> moveHistoryListView = new ListView<>(moveHistory);
        moveHistoryListView.setPrefHeight(BOARD_HEIGHT);
        moveHistoryListView.setPrefWidth(150);

        // Dodanie wszystkich elementów do głównego kontenera
        VBox container = new VBox();
        container.getChildren().addAll(buttonPanel, moveHistoryListView);
        container.setSpacing(10);

        // Ustawienie pozycji kontenera
        container.setLayoutX(BOARD_WIDTH + 10);
        container.setLayoutY(50);

        // Dodanie kontenera do sceny
        root.getChildren().add(container);

        Scene scene = new Scene(root, BOARD_WIDTH + container.getPrefWidth() + 150, BOARD_HEIGHT);
        stage.setTitle("Go Board");
        scene.setFill(BOARD_COLOR);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void setChangeColorMode(String color) {
        // Usuń poprzednią obsługę zdarzeń kliknięcia myszy dla wszystkich okręgów
        for (var node : root.getChildren()) {
            if (node instanceof Circle circle) {
                circle.setOnMouseClicked(null);
            }
        }

        // Ustaw nową obsługę zdarzeń kliknięcia myszy dla wszystkich okręgów
        for (var node : root.getChildren()) {
            if (node instanceof Circle circle) {
                circle.setOnMouseClicked(event -> {
                    setCircleFillColor(circle, color);
                    addMoveToHistory(color, circle);
                });
            }
        }
    }

    private void setCircleFillColor(Circle circle, String color) {
        // Ustawianie koloru wypełnienia dla danego okręgu
        switch (color) {
            case "Biały":
                circle.setFill(Color.WHITE);
                break;
            case "Czarny":
                circle.setFill(Color.BLACK);
                break;
            case "Reset":
                circle.setFill(Color.BURLYWOOD);
                break;
        }
        // Zresetuj obsługę zdarzeń kliknięcia myszy, aby nie zmieniać koloru po ponownym najechaniu
        circle.setOnMouseClicked(null);
    }

    private void resetCircleColors(Group root) {
        // Zresetuj kolor wypełnienia dla wszystkich okręgów na kolor tła
        for (var node : root.getChildren()) {
            if (node instanceof Circle circle) {
                setCircleFillColor(circle, "Reset");
            }
        }
    }

    private void addMoveToHistory(String color, Circle circle) {
        // Dodaj informacje o ruchu do historii
        int x = (int) circle.getCenterX() / 60;
        int y = (int) circle.getCenterY() / 60;
        String moveInfo = color + ": (" + x + ", " + y + ")";
        moveHistory.add(moveInfo);
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec gry");
        alert.setHeaderText(null);
        alert.setContentText("Partia zakończona. Gracz się poddał.");

        alert.showAndWait();

        // Po naciśnięciu "Ok", zakończ działanie programu
        System.exit(0);
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(LINE_COLOR); // Kolor linii
        line.setStrokeWidth(5);

        return line;
    }

    private Circle createCircle(double centerX, double centerY) {
        Circle circle = new Circle(centerX, centerY, 15); // Ustaw rozmiar okręgu według własnych preferencji
        circle.setFill(BOARD_COLOR); // Kolor okręgu
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);

        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
