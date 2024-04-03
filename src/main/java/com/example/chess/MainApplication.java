package com.example.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.GridPane;

public class MainApplication extends Application {
    public static boolean PLAY_AS_WHITE = true;
    public static boolean PLAY_AS_BLACK = false;
    GridPane root = new GridPane();
    BoardManager board = new BoardManager(root, PLAY_AS_WHITE);

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