package com.example.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.example.chess.BoardManager;

public class HelloApplication extends Application {
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