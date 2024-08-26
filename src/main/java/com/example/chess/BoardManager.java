package com.example.chess;

import com.example.chess.Utility.EngineMoveTask;
import com.example.chess.model.Move;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.chess.MoveGenerator.generateLegalMovesPlayer;
import static com.example.chess.Validation.MainValidation.isValidMove;
import static com.example.chess.Constants.*;

public class BoardManager {
    Circle selectedPiece = null;
    GridPane root;
    Position collectionManager = new Position();
    ArrayList<Circle> markers = new ArrayList<>();
    boolean playerTurn;
    boolean playerWhite;
    MoveEngine engine = new MoveEngine();
    int moveCount = 0;
    int depth;

    public BoardManager(GridPane pane, boolean white, int depth) {
        this.root = pane;
        this.playerTurn = white;
        this.playerWhite = white;
        this.depth = depth  ;
    }

    public void setBoard() {
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

        // Add pieces to the board with white pieces at the bottom
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                double pieceVal = collectionManager.getPieceValue(row, col);
                if (pieceVal != 0.0) {
                    Circle piece = createPiece();
                    root.add(piece, col, row);
                    setCircleClickHandlers(piece);
                    addPieceIcon(piece, IMAGE_DICT.get(pieceVal));
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
            if (!playerTurn) {
                return;
            }
            Circle clickedCircle = (Circle) event.getSource();
            int clickedRow = GridPane.getRowIndex(clickedCircle);
            int clickedCol = GridPane.getColumnIndex(clickedCircle);

            double pieceValue = collectionManager.getPieceValue(clickedRow, clickedCol);
            if (pieceValue <= 0) {
                return;
            }

            if (clickedCircle == selectedPiece) {
                clearSelect();
                return;
            }

            if (selectedPiece != null) {
                int oldRow = GridPane.getRowIndex(selectedPiece);
                int oldCol = GridPane.getColumnIndex(selectedPiece);

                if (collectionManager.getPieceValue(oldRow, oldCol) > 0 && collectionManager.getPieceValue(clickedRow, clickedCol) > 0) {
                    clearSelect();
                    selectPiece(clickedCircle);
                }

            } else {
                selectPiece(clickedCircle);
            }
        });
    }

    private void setMarkerClickHandlers(Circle marker) {
        marker.setOnMouseClicked(event -> {
            if (!playerTurn) {
                return;
            }
            Circle clickedMarker = (Circle) event.getSource();
            int clickedRow = GridPane.getRowIndex(clickedMarker);
            int clickedCol = GridPane.getColumnIndex(clickedMarker);
            clearMarkers();
            purgeSquare(clickedRow, clickedCol);

            if (selectedPiece != null) {
                int oldRow = GridPane.getRowIndex(selectedPiece);
                int oldCol = GridPane.getColumnIndex(selectedPiece);
                double pieceVal = collectionManager.getPieceValue(oldRow, oldCol);

                if (pieceVal > 0 && isValidMove(oldRow, oldCol, clickedRow, clickedCol, collectionManager)) {
                    GridPane.setRowIndex(selectedPiece, clickedRow);
                    GridPane.setColumnIndex(selectedPiece, clickedCol);
                    collectionManager.movePiece(oldRow, oldCol, clickedRow, clickedCol, pieceVal);
                    clearSelect();
                    onPlayerMove();
                }
            }
        });
    }

    private void addPieceIcon(Circle circle, String iconPath) {
        Image iconImage = new Image(iconPath);
        ImagePattern imagePattern = new ImagePattern(iconImage);
        circle.setFill(imagePattern);
    }

    private void addMarker(int row, int col) {
        Circle marker = new Circle(40, Color.TRANSPARENT);
        marker.setRadius(40);
        addPieceIcon(marker, IMAGE_DICT.get(MARKER));
        setMarkerClickHandlers(marker);
        markers.add(marker);
        root.add(marker, col, row);
    }

    public void clearMarkers() {
        for (Circle marker : markers) {
            root.getChildren().remove(marker);
        }
        markers.clear();
    }

    public void purgeSquare(int row, int col) {
        for (javafx.scene.Node node : root.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                if (node instanceof Circle) {
                    Platform.runLater(() -> {
                        root.getChildren().remove(node);
                    });
                    break;
                }
            }
        }
    }

    public void selectPiece(Circle piece) {
        int row = GridPane.getRowIndex(piece);
        int col = GridPane.getColumnIndex(piece);
        selectedPiece = piece;
        selectedPiece.setStroke(Color.PURPLE);
        ArrayList<ArrayList<Boolean>> legalMoves = generateLegalMovesPlayer(row, col, collectionManager);
        for (int rowA = 0; rowA < 8; rowA++) {
            for (int colA = 0; colA < 8; colA++) {
                if (legalMoves.get(rowA).get(colA)) {
                    addMarker(rowA, colA);
                }
            }
        }
    }

    public void clearSelect() {
        clearMarkers();
        if (selectedPiece != null) {
            selectedPiece.setStroke(Color.TRANSPARENT);
        }
        selectedPiece = null;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void onPlayerMove() {
        playerTurn = false;
        Platform.runLater(() -> {
            EngineMoveTask task = new EngineMoveTask(this);
            new Thread(task).start();
        });
    }

    public void makeEngineMove() {
        try {
            Move move = engine.getNiceMove(playerWhite, collectionManager, depth);
            System.out.println("Engine generated move: (" + move.getOldRow() + ", " + move.getOldCol() + ") --> (" + move.getNewRow() + ", " + move.getNewCol() + ")" );

            if (move == null) {
                System.out.println("ERROR: No move generated by the engine.");
                return;
            }

            purgeSquare(move.getNewRow(), move.getNewCol());
            double pieceVal = collectionManager.getPieceValue(move.getOldRow(), move.getOldCol());
            collectionManager.movePiece(move.getOldRow(), move.getOldCol(), move.getNewRow(), move.getNewCol(), pieceVal);
            Circle piece = findPiece(move.getOldRow(), move.getOldCol());
            GridPane.setRowIndex(piece, move.getNewRow());
            GridPane.setColumnIndex(piece, move.getNewCol());
            moveCount++;
        } catch (Exception e) {
            System.out.println("Exception in makeEngineMove: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public Circle findPiece(int row, int col) {
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

    public void castle(int row, int col) {
        if (row == 0) {
            if (col == 6) {
                collectionManager.movePiece(0, 7, 0, 5, BLACK_ROOK);
                Circle piece = findPiece(0, 7);
                GridPane.setColumnIndex(piece, 5);
            } else {
                collectionManager.movePiece(0, 0, 0, 3, BLACK_ROOK);
                Circle piece = findPiece(0, 0);
                GridPane.setColumnIndex(piece, 3);
            }
        } else {
            if (col == 6) {
                collectionManager.movePiece(7, 7, 7, 5, WHITE_ROOK);
                Circle piece = findPiece(7, 7);
                GridPane.setColumnIndex(piece, 5);
            } else {
                collectionManager.movePiece(7, 0, 7, 3, WHITE_ROOK);
                Circle piece = findPiece(7, 0);
                GridPane.setColumnIndex(piece, 3);
            }
        }
    }

}
