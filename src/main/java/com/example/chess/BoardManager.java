package com.example.chess;

import com.example.chess.type.Move;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardManager {

    Circle selectedPiece = null;
    GridPane root;
    InfoCollectionManager collectionManager = new InfoCollectionManager();
    MoveUtility moveUtil = new MoveUtility();
    ArrayList<Circle> markers = new ArrayList<>();
    boolean playerTurn;
    boolean playerWhite;
    MoveEngine engine = new MoveEngine();


    public BoardManager(GridPane pane, boolean white){
        this.root = pane;
        this.playerTurn = white;
        this.playerWhite = white;


    }
    public void setBoard(){
        root.setGridLinesVisible(true);
        // Add squares representing chessboard
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle square = createSquare();
                root.add(square, col, row);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.LIGHTGRAY); // Light color for white squares
                } else {
                    square.setFill(Color.DARKOLIVEGREEN); // Dark color for black squares
                }
            }
        }

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Double pieceVal = collectionManager.getPieceValue(row, col);
                if(pieceVal != 0.0){
                    Circle piece = createPiece();
                    root.add(piece, col, row);
                    setCircleClickHandlers(piece);
                    addPieceIcon(piece, collectionManager.getImage(pieceVal));
                }
            }
        }

    }
    private Rectangle createSquare() {
        Rectangle square = new Rectangle(80, 80);
        square.setStroke(Color.BLACK); // Add border color
        return square;
    }

    private Circle createPiece() {
        Circle piece = new Circle(37, Color.TRANSPARENT);
        piece.setRadius(37);
        piece.setStrokeWidth(3);
        piece.setStroke(Color.TRANSPARENT);

        return piece;
    }

    private void setCircleClickHandlers(Circle circle) {
        circle.setOnMouseClicked(event -> {
            if(!playerTurn){
                return;
            }
            // Get the clicked circle
            Circle clickedCircle = (Circle) event.getSource();
            // Get the row and column index of the clicked piece
            int clickedRow = GridPane.getRowIndex(clickedCircle);
            int clickedCol = GridPane.getColumnIndex(clickedCircle);

            //deselect on second click
            if(clickedCircle == selectedPiece){
                clearSelect();
                return;
            }

            if (selectedPiece != null) {
                // Get the row and column index of the selected piece
                int oldRow = GridPane.getRowIndex(selectedPiece);
                int oldCol = GridPane.getColumnIndex(selectedPiece);
                //select another piece case
                if(collectionManager.getPieceMap().get(oldRow).get(oldCol) * collectionManager.getPieceMap().get(clickedRow).get(clickedCol) > 0){
                    clearSelect();
                    selectPiece(clickedCircle);
                    return;
                }


            } else {
                // Select the clicked piece
               selectPiece(clickedCircle);
            }
        });

    }
    private void setMarkerClickHandlers(Circle marker) {
        marker.setOnMouseClicked(event -> {
            if(!playerTurn){
                return;
            }
            // Get the clicked circle
            Circle clickedMarker = (Circle) event.getSource();

            // Get the row and column index of the clicked circle
            int clickedRow = GridPane.getRowIndex(clickedMarker);
            int clickedCol = GridPane.getColumnIndex(clickedMarker);
            clearMarkers();
            purgeSquare(clickedRow, clickedCol);

            // If a piece is already selected, move it to the clicked position
            if (selectedPiece != null) {
                // Get the row and column index of the selected piece
                int oldRow = GridPane.getRowIndex(selectedPiece);
                int oldCol = GridPane.getColumnIndex(selectedPiece);
                if(moveUtil.isValidMove(oldRow, oldCol, clickedRow, clickedCol, collectionManager.getPieceMap())){
                    GridPane.setRowIndex(selectedPiece, clickedRow);
                    GridPane.setColumnIndex(selectedPiece, clickedCol);
                    collectionManager.movePiece(oldRow, oldCol, clickedRow, clickedCol);
                    clearSelect();
                    onPlayerMove();
                }


            }
        });
    }
    private void addPieceIcon(Circle circle, String iconPath) {
        // Load the icon image
        Image iconImage = new Image(iconPath);

        // Create an ImagePattern using the icon image
        ImagePattern imagePattern = new ImagePattern(iconImage);

        // Set the fill of the circle to the ImagePattern
        circle.setFill(imagePattern);
    }
    private void addMarker(int row, int col){
        Circle marker = new Circle(40, Color.TRANSPARENT);
        marker.setRadius(40);
        addPieceIcon(marker, "images/marker.png");
        setMarkerClickHandlers(marker);
        markers.add(marker);
        root.add(marker, col, row);
    }
    public void clearMarkers(){
        for(Circle marker: markers){
            root.getChildren().remove(marker);
        }
        markers.clear();
    }
    public void purgeSquare(int row, int col){
        for (javafx.scene.Node node : root.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {

                if (node instanceof Circle) {
                    root.getChildren().remove(node);
                    break; // Found the circle, exit the loop
                }
            }
        }
    }
    public void selectPiece(Circle piece){
        int row = GridPane.getRowIndex(piece);
        int col = GridPane.getColumnIndex(piece);
        selectedPiece = piece;
        selectedPiece.setStroke(Color.PURPLE);
        ArrayList<ArrayList<Boolean>> legalMoves = moveUtil.getLegalMoves(row, col, collectionManager.getPieceMap());
        for(int rowA = 0; rowA < 8; rowA++){
            for(int colA = 0; colA < 8; colA++){
                if(legalMoves.get(rowA).get(colA)){
                    addMarker(rowA, colA);
                }
            }
        }
    }
    public void clearSelect(){
        clearMarkers();
        if(selectedPiece!= null){selectedPiece.setStroke(Color.TRANSPARENT);}
        selectedPiece = null;
    }
    public void onPlayerMove(){
        playerTurn = false;
        makeEngineMove();
        playerTurn = true;
    }
    public void makeEngineMove(){
        Move move = engine.getRandomMove(playerWhite, collectionManager.pieceMap);
        purgeSquare(move.getNewRow(), move.getNewCol());
        collectionManager.movePiece(move.getOldRow(), move.getOldCol(), move.getNewRow(), move.getNewCol());
        Circle piece = findPiece(move.getOldRow(), move.getOldCol());
        GridPane.setRowIndex(piece, move.getNewRow());
        GridPane.setColumnIndex(piece, move.getNewCol());
    }

    public Circle findPiece(int row, int col){
        for (javafx.scene.Node node : root.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                if (node instanceof Circle) {
                    return (Circle) node;
                }
            }
        }
        return null;
    }

}
