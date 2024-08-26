package com.example.chess;

import com.example.chess.model.Move;

import java.util.HashMap;

public class Constants {

    // Constants representing piece types
    public static final double BLACK_ROOK = -5.3;
    public static final double BLACK_KNIGHT = -3;
    public static final double BLACK_BISHOP = -3.3;
    public static final double BLACK_QUEEN = -10;
    public static final double BLACK_KING = -255;
    public static final double BLACK_PAWN = -1;
    public static final double WHITE_ROOK = 5.3;
    public static final double WHITE_KNIGHT = 3;
    public static final double WHITE_BISHOP = 3.3;
    public static final double WHITE_QUEEN = 10;
    public static final double WHITE_KING = 255;
    public static final double WHITE_PAWN = 1;

    public static final double MARKER = 0;
    public static final Move CHECKMATE = new Move(-1, -1, -1, -1);

    public static final double BLACK_KING_LOST = -250;

    public static final double WHITE_KING_LOST = -250;


    public static final HashMap<Double, String> IMAGE_DICT = getImageDict();

    private static HashMap<Double, String> getImageDict() {
        HashMap<Double, String> tempImageDict = new HashMap<>();
        tempImageDict.put(BLACK_ROOK, "images/blackRook.png");
        tempImageDict.put(BLACK_KNIGHT, "images/blackKnight.png");
        tempImageDict.put(BLACK_BISHOP, "images/blackBishop.png");
        tempImageDict.put(BLACK_QUEEN, "images/BlackQueen.png");
        tempImageDict.put(BLACK_KING, "images/BlackKing.png");
        tempImageDict.put(BLACK_PAWN, "images/blackPawn.png");

        tempImageDict.put(WHITE_ROOK, "images/whiteRook.png");
        tempImageDict.put(WHITE_KNIGHT, "images/whiteKnight.png");
        tempImageDict.put(WHITE_BISHOP, "images/whiteBishop.png");
        tempImageDict.put(WHITE_QUEEN, "images/whiteQueen.png");
        tempImageDict.put(WHITE_KING, "images/whiteKing.png");
        tempImageDict.put(WHITE_PAWN, "images/whitePawn.png");

        tempImageDict.put(MARKER, "images/marker.png");
        tempImageDict.put(WHITE_KING_LOST, "images/whiteKingLost.png");
        tempImageDict.put(BLACK_KING_LOST, "images/blackKingLost.png");
        return tempImageDict;
    }

}
