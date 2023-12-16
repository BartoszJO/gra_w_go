package com.grawgo.gra_w_go;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class goboard_edycja_podst1 extends Application {
    private static final int BOARD_SIZE = 9; // Rozmiar planszy (9x9)
    private static final Color LINE_COLOR = Color.BLACK;
    private static final double BOARD_WIDTH = 600.0;
    private static final double BOARD_HEIGHT = 600.0;

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        // Równomierne rozmieszczenie linii pionowych
        for (int i = 0; i < BOARD_SIZE; i++) {
            double x = (i + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
            Line verticalLine = createLine(x, 0, x, BOARD_HEIGHT);
            root.getChildren().add(verticalLine);
        }

        // Równomierne rozmieszczenie linii poziomych
        for (int i = 0; i < BOARD_SIZE; i++) {
            double y = (i + 1.0) * BOARD_HEIGHT / (BOARD_SIZE + 1);
            Line horizontalLine = createLine(0, y, BOARD_WIDTH, y);
            root.getChildren().add(horizontalLine);

            // Dodanie okręgu nad przecięciem linii poziomej
            for (int j = 0; j < BOARD_SIZE; j++) {
                double x = (j + 1.0) * BOARD_WIDTH / (BOARD_SIZE + 1);
                Circle circle = createCircle(x, y);
                root.getChildren().add(circle);

                // Obsługa zdarzeń myszy dla okręgu
                circle.setOnMouseEntered(event -> circle.setStroke(Color.RED));
                circle.setOnMouseExited(event -> circle.setStroke(Color.BLACK));
            }
        }

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        stage.setTitle("Go Board");
        scene.setFill(Color.BURLYWOOD);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(LINE_COLOR); // Kolor linii
        line.setStrokeWidth(5);

        return line;
    }

    private Circle createCircle(double centerX, double centerY) {
        Circle circle = new Circle(centerX, centerY, 15); // Ustaw rozmiar okręgu według własnych preferencji
        circle.setFill(Color.BURLYWOOD); // Kolor okręgu
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);

        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
