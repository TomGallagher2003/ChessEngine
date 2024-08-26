package com.example.chess;

import static com.example.chess.Constants.*;

public class Position {
    private long whitePawns, whiteKnights, whiteBishops, whiteRooks, whiteQueens, whiteKing;
    private long blackPawns, blackKnights, blackBishops, blackRooks, blackQueens, blackKing;


    public Position() {
        setPieceMap();
    }
    public Position(Position other) {
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

        // First, handle the capture by clearing the captured piece from the bitboards
        double capturedPiece = getPieceValue(newRow, newCol);
        if (capturedPiece != 0.0) {
            if (capturedPiece == WHITE_PAWN) {
                whitePawns &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_PAWN) {
                blackPawns &= ~(1L << newPos);
            } else if (capturedPiece == WHITE_ROOK) {
                whiteRooks &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_ROOK) {
                blackRooks &= ~(1L << newPos);
            } else if (capturedPiece == WHITE_KNIGHT) {
                whiteKnights &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_KNIGHT) {
                blackKnights &= ~(1L << newPos);
            } else if (capturedPiece == WHITE_BISHOP) {
                whiteBishops &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_BISHOP) {
                blackBishops &= ~(1L << newPos);
            } else if (capturedPiece == WHITE_QUEEN) {
                whiteQueens &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_QUEEN) {
                blackQueens &= ~(1L << newPos);
            } else if (capturedPiece == WHITE_KING) {
                whiteKing &= ~(1L << newPos);
            } else if (capturedPiece == BLACK_KING) {
                blackKing &= ~(1L << newPos);
            }
        }

        // Then, move the piece to the new position
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

}
