package com.example.chess;

import java.util.HashMap;

public class InfoCollectionManager {
    // Bitboards for each piece type
    private long whitePawns, whiteKnights, whiteBishops, whiteRooks, whiteQueens, whiteKing;
    private long blackPawns, blackKnights, blackBishops, blackRooks, blackQueens, blackKing;

    // Image dictionary for piece icons
    private HashMap<Double, String> imageDict = new HashMap<>();

    // Constants representing piece types
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

    public InfoCollectionManager() {
        setImageDict();
        setPieceMap();
    }
    public InfoCollectionManager(InfoCollectionManager other) {
        this.whitePawns = other.whitePawns;
        this.whiteRooks = other.whiteRooks;
        this.whiteKnights = other.whiteKnights;
        this.whiteBishops = other.whiteBishops;
        this.whiteQueens = other.whiteQueens;
        this.whiteKing = other.whiteKing;
        this.blackPawns = other.blackPawns;
        this.blackRooks = other.blackRooks;
        this.blackKnights = other.blackKnights;
        this.blackBishops = other.blackBishops;
        this.blackQueens = other.blackQueens;
        this.blackKing = other.blackKing;
        this.imageDict = new HashMap<>(other.imageDict); // Deep copy the image dictionary if necessary
    }


    // Initialize the image dictionary
    private void setImageDict() {
        imageDict.put(BLACK_ROOK, "images/blackRook.png");
        imageDict.put(BLACK_KNIGHT, "images/blackKnight.png");
        imageDict.put(BLACK_BISHOP, "images/blackBishop.png");
        imageDict.put(BLACK_QUEEN, "images/BlackQueen.png");
        imageDict.put(BLACK_KING, "images/BlackKing.png");
        imageDict.put(BLACK_PAWN, "images/blackPawn.png");

        imageDict.put(WHITE_ROOK, "images/whiteRook.png");
        imageDict.put(WHITE_KNIGHT, "images/whiteKnight.png");
        imageDict.put(WHITE_BISHOP, "images/whiteBishop.png");
        imageDict.put(WHITE_QUEEN, "images/whiteQueen.png");
        imageDict.put(WHITE_KING, "images/whiteKing.png");
        imageDict.put(WHITE_PAWN, "images/whitePawn.png");
    }

    // Initialize the bitboards for the starting position
    private void setPieceMap() {
        whitePawns   = 0x00FF000000000000L;  // 6th rank
        whiteRooks   = 0x8100000000000000L;  // 7th rank corners
        whiteKnights = 0x4200000000000000L;  // 7th rank knights
        whiteBishops = 0x2400000000000000L;  // 7th rank bishops
        whiteQueens  = 0x0800000000000000L;  // 7th rank queen
        whiteKing    = 0x1000000000000000L;  // 7th rank king

        blackPawns   = 0x000000000000FF00L;  // 1st rank
        blackRooks   = 0x0000000000000081L;  // 0th rank corners
        blackKnights = 0x0000000000000042L;  // 0th rank knights
        blackBishops = 0x0000000000000024L;  // 0th rank bishops
        blackQueens  = 0x0000000000000008L;  // 0th rank queen
        blackKing    = 0x0000000000000010L;  // 0th rank king
    }

    // Move a piece from one position to another
    public void movePiece(int oldRow, int oldCol, int newRow, int newCol, double pieceType) {
        int oldPos = oldRow * 8 + oldCol;
        int newPos = newRow * 8 + newCol;

        long moveMask = (1L << oldPos) | (1L << newPos);

        if (pieceType == WHITE_PAWN) {
            whitePawns = (whitePawns & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_PAWN) {
            blackPawns = (blackPawns & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == WHITE_ROOK) {
            whiteRooks = (whiteRooks & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_ROOK) {
            blackRooks = (blackRooks & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == WHITE_KNIGHT) {
            whiteKnights = (whiteKnights & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_KNIGHT) {
            blackKnights = (blackKnights & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == WHITE_BISHOP) {
            whiteBishops = (whiteBishops & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_BISHOP) {
            blackBishops = (blackBishops & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == WHITE_QUEEN) {
            whiteQueens = (whiteQueens & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_QUEEN) {
            blackQueens = (blackQueens & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == WHITE_KING) {
            whiteKing = (whiteKing & ~(1L << oldPos)) | (1L << newPos);
        } else if (pieceType == BLACK_KING) {
            blackKing = (blackKing & ~(1L << oldPos)) | (1L << newPos);
        }
    }

    // Get the value of the piece at a given position
    public double getPieceValue(int row, int col) {
        int pos = row * 8 + col;
        if ((whitePawns & (1L << pos)) != 0) return WHITE_PAWN;
        if ((blackPawns & (1L << pos)) != 0) return BLACK_PAWN;
        if ((whiteRooks & (1L << pos)) != 0) return WHITE_ROOK;
        if ((blackRooks & (1L << pos)) != 0) return BLACK_ROOK;
        if ((whiteKnights & (1L << pos)) != 0) return WHITE_KNIGHT;
        if ((blackKnights & (1L << pos)) != 0) return BLACK_KNIGHT;
        if ((whiteBishops & (1L << pos)) != 0) return WHITE_BISHOP;
        if ((blackBishops & (1L << pos)) != 0) return BLACK_BISHOP;
        if ((whiteQueens & (1L << pos)) != 0) return WHITE_QUEEN;
        if ((blackQueens & (1L << pos)) != 0) return BLACK_QUEEN;
        if ((whiteKing & (1L << pos)) != 0) return WHITE_KING;
        if ((blackKing & (1L << pos)) != 0) return BLACK_KING;
        return 0.0;
    }

    // Get the image path for a piece based on its value
    public String getImage(double piece) {
        return imageDict.get(piece);
    }
}
