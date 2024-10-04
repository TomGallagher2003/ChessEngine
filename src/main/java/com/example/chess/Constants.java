package com.example.chess;

import com.example.chess.Utility.OpeningParser;
import com.example.chess.model.Move;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    public static final double WHITE_KING_LOST = 250;

    private static OpeningParser openingParser = new OpeningParser();

    public static final List<String> OPENING_FILENAMES = List.of("carlsen", "radjabov", "kasparov", "nepomniachtchi");
    public static final List<String> OPENINGS = openingParser.parseOpenings(OPENING_FILENAMES);
    public static final long KNIGHT_GOOD_SQUARES = 0x00003C3C3C3C0000L;
    public static final long BISHOP_GOOD_SQUARES = 0x00423C3C3C3C4200L;
    public static final long ROOK_GOOD_SQUARES   = 0xffff00000000ffffL;
    public static final long QUEEN_GOOD_SQUARES  = 0x0000000000000000L; //TODO research queen best squares
    public static final long KING_GOOD_SQUARES   = 0xC3000000000000C3L;
    public static final long PAWN_GOOD_SQUARES   = 0x0000003C3C000000L;

    public static final double POSITIONAL_BONUS = 0.35;


    public static final HashMap<Double, String> IMAGE_DICT = getImageDict();

    private static HashMap<Double, String> getImageDict() {
        HashMap<Double, String> tempImageDict = new HashMap<>();
        tempImageDict.put(BLACK_ROOK, "blackRook.png");
        tempImageDict.put(BLACK_KNIGHT, "blackKnight.png");
        tempImageDict.put(BLACK_BISHOP, "blackBishop.png");
        tempImageDict.put(BLACK_QUEEN, "BlackQueen.png");
        tempImageDict.put(BLACK_KING, "BlackKing.png");
        tempImageDict.put(BLACK_PAWN, "blackPawn.png");

        tempImageDict.put(WHITE_ROOK, "whiteRook.png");
        tempImageDict.put(WHITE_KNIGHT, "whiteKnight.png");
        tempImageDict.put(WHITE_BISHOP, "whiteBishop.png");
        tempImageDict.put(WHITE_QUEEN, "whiteQueen.png");
        tempImageDict.put(WHITE_KING, "whiteKing.png");
        tempImageDict.put(WHITE_PAWN, "whitePawn.png");

        tempImageDict.put(MARKER, "marker.png");
        tempImageDict.put(WHITE_KING_LOST, "whiteKingLost.png");
        tempImageDict.put(BLACK_KING_LOST, "blackKingLost.png");
        return tempImageDict;
    }

}
