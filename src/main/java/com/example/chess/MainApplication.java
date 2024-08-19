package com.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.GridPane;

public class MainApplication extends Application {
    public static boolean WHITE = true;
    public static boolean BLACK = false; // Doesn't work yet
    GridPane root = new GridPane();
    BoardManager board = new BoardManager(root, WHITE, 1);

    @Override
    public void start(Stage primaryStage) {
        board.setBoard();
        Scene scene = new Scene(root, 648, 648);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chessboard");
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}