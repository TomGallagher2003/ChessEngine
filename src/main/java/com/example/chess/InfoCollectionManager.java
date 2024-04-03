package com.example.chess;

import com.example.chess.type.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class InfoCollectionManager {
    // piece constants
    static final double BLACK_ROOK = -5.3;
    static final double BLACK_KNIGHT = -3;
    static final double BLACK_BISHOP = -3.3;
    static final double BLACK_QUEEN = -10;
    static final double BLACK_KING = -255;
    static final double BLACK_PAWN = -1;
    static final double WHITE_ROOK = 5.3;
    static final double WHITE_KNIGHT = 3;
    static final double WHITE_BISHOP = 3.3;
    static final double WHITE_QUEEN = 10;
    static final double WHITE_KING = 255;
    static final double WHITE_PAWN = 1;
    HashMap<Double, String> imageDict = new HashMap<>();
    ArrayList<ArrayList<Double>> pieceMap = new ArrayList<>();
    public InfoCollectionManager(){
        setImageDict();
        setPieceMap();
    }
    private void setImageDict(){
        // set image map
        imageDict.put(-5.3, "images/blackRook.png");
        imageDict.put(-3.0, "images/blackKnight.png");
        imageDict.put(-3.3, "images/blackBishop.png");
        imageDict.put(-10.0, "images/BlackQueen.png");
        imageDict.put(-255.0, "images/BlackKing.png");
        imageDict.put(-1.0, "images/blackPawn.png");

        imageDict.put(5.3, "images/whiteRook.png");
        imageDict.put(3.0, "images/whiteKnight.png");
        imageDict.put(3.3, "images/whiteBishop.png");
        imageDict.put(10.0, "images/whiteQueen.png");
        imageDict.put(255.0, "images/whiteKing.png");
        imageDict.put(1.0, "images/whitePawn.png");
    }
    private void setPieceMap(){
        // set the starting piece map
        ArrayList<Double> row0 = new ArrayList<Double>(Arrays.asList(BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));
        ArrayList<Double> row7 = new ArrayList<Double>(Arrays.asList(WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));
        ArrayList<Double> row1 = new ArrayList<Double>();
        ArrayList<Double> row6 = new ArrayList<Double>();
        for(int i = 0; i<8; i++){row1.add(BLACK_PAWN);}
        for(int i = 0; i<8; i++){row6.add(WHITE_PAWN);}
        pieceMap.add(row0);
        pieceMap.add(row1);
        for(int i =2; i< 6; i++){pieceMap.add(new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)));}
        pieceMap.add(row6);
        pieceMap.add(row7);
    }
    public ArrayList<ArrayList<Double>> getPieceMap(){
        return pieceMap;
    }
    public HashMap<Double, String> getImageDict(){
        return imageDict;
    }
    public ArrayList<ArrayList<Double>> movePiece(int oldRow, int oldCol, int newRow, int newCol){
        pieceMap.get(newRow).set(newCol, pieceMap.get(oldRow).get(oldCol));
        pieceMap.get(oldRow).set(oldCol, 0.0);
        return pieceMap;
    }
    public Double getPieceValue(int row, int col){
        return pieceMap.get(row).get(col);
    }
    public String getImage(Double piece){
        return imageDict.get(piece);
    }
    public static ArrayList<ArrayList<Double>> copyMap( ArrayList<ArrayList<Double>> map){
        ArrayList<ArrayList<Double>> ifMap = new ArrayList<>();
        for(int row = 0; row < 8; row++){
            ArrayList<Double> line = new ArrayList<>();
            for(int col = 0; col < 8; col++){
                line.add(Double.valueOf(map.get(row).get(col)));
            }
            ifMap.add(line);
        }
        return ifMap;
    }
    public static ArrayList<ArrayList<Double>> copyMapMove( ArrayList<ArrayList<Double>> map, Move move){
        ArrayList<ArrayList<Double>> ifMap = new ArrayList<>();
        for(int row = 0; row < 8; row++){
            ArrayList<Double> line = new ArrayList<>();
            for(int col = 0; col < 8; col++){
                line.add(Double.valueOf(map.get(row).get(col)));
            }
            ifMap.add(line);
        }
        ifMap.get(move.getNewRow()).set(move.getNewCol(), ifMap.get(move.getOldRow()).get(move.getOldCol()));
        ifMap.get(move.getOldRow()).set(move.getOldCol(), 0.0);
        return ifMap;
    }
}
