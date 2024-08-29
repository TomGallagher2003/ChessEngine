package com.example.chess;

import static com.example.chess.Constants.*;

public class Position {
    private long whitePawns, whiteKnights, whiteBishops, whiteRooks, whiteQueens, whiteKing;
    private long blackPawns, blackKnights, blackBishops, blackRooks, blackQueens, blackKing;

    private long passantablePawns, unmovedRooks, unmovedKings;


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

        this.passantablePawns = other.passantablePawns;
        this.unmovedRooks = other.unmovedRooks;
        this.unmovedKings = other.unmovedKings;
    }


    // Initialize the bitboards for the starting position
    private void setPieceMap() {
        whitePawns   = 0x00FF000000000000L;
        whiteRooks   = 0x8100000000000000L;
        whiteKnights = 0x4200000000000000L;
        whiteBishops = 0x2400000000000000L;
        whiteQueens  = 0x0800000000000000L;
        whiteKing    = 0x1000000000000000L;

        blackPawns   = 0x000000000000FF00L;
        blackRooks   = 0x0000000000000081L;
        blackKnights = 0x0000000000000042L;
        blackBishops = 0x0000000000000024L;
        blackQueens  = 0x0000000000000008L;
        blackKing    = 0x0000000000000010L;

        passantablePawns = 0x0000000000000000L;
        unmovedKings = 0x1000000000000010L;
        unmovedRooks = 0x8100000000000081L;
    }

    // Move a piece from one position to another
    public void movePiece(int oldRow, int oldCol, int newRow, int newCol, double pieceType) {
        int oldPos = oldRow * 8 + oldCol;
        int newPos = newRow * 8 + newCol;


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

        // move the piece to the new position
        passantablePawns = 0L;
        if (pieceType == WHITE_PAWN) {
            if(isPassantablePawn(newRow + 1, newCol)){
                //en passant
                blackPawns &= ~(1L << (newRow * 8 + newCol + 8));
            }
            whitePawns = (whitePawns & ~(1L << oldPos)) | (1L << newPos);
            if(Math.abs(oldRow - newRow) == 2){
                passantablePawns = 1L << newPos;
            }

        } else if (pieceType == BLACK_PAWN) {
            if(isPassantablePawn(newRow - 1, newCol)){
                //en passant
                whitePawns &= ~(1L << (newRow * 8 + newCol - 8));
            }
            blackPawns = (blackPawns & ~(1L << oldPos)) | (1L << newPos);
            if(Math.abs(oldRow - newRow) == 2){
                passantablePawns = 1L << newPos;
            }
        } else if (pieceType == WHITE_ROOK) {
            whiteRooks = (whiteRooks & ~(1L << oldPos)) | (1L << newPos);
            unmovedRooks &= ~(1L << oldPos);
        } else if (pieceType == BLACK_ROOK) {
            blackRooks = (blackRooks & ~(1L << oldPos)) | (1L << newPos);
            unmovedRooks &= ~(1L << oldPos);
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
            // castling is handled in the board manager
            whiteKing = (whiteKing & ~(1L << oldPos)) | (1L << newPos);
            unmovedKings &= ~(1L << oldPos);
        } else if (pieceType == BLACK_KING) {
            // castling is handled in the board manager
            blackKing = (blackKing & ~(1L << oldPos)) | (1L << newPos);
            unmovedKings &= ~(1L << oldPos);
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

    public boolean isPassantablePawn(int row, int col){
        int pos = row * 8 + col;
        if ((passantablePawns & (1L << pos)) != 0) return true;
        return false;
    }
    public boolean isUnmovedKing(int row, int col){
        int pos = row * 8 + col;
        if ((unmovedKings & (1L << pos)) != 0) return true;
        return false;
    }
    public boolean isUnmovedRook(int row, int col){
        int pos = row * 8 + col;
        if ((unmovedRooks & (1L << pos)) != 0) return true;
        return false;
    }

    public double evaluate() {
        double totalEvaluation = 0.0;

        totalEvaluation += Long.bitCount(whitePawns) * WHITE_PAWN;
        totalEvaluation += Long.bitCount(whiteKnights) * WHITE_KNIGHT;
        totalEvaluation += Long.bitCount(whiteBishops) * WHITE_BISHOP;
        totalEvaluation += Long.bitCount(whiteRooks) * WHITE_ROOK;
        totalEvaluation += Long.bitCount(whiteQueens) * WHITE_QUEEN;
        totalEvaluation += Long.bitCount(whiteKing) * WHITE_KING;

        totalEvaluation += Long.bitCount(blackPawns) * BLACK_PAWN;
        totalEvaluation += Long.bitCount(blackKnights) * BLACK_KNIGHT;
        totalEvaluation += Long.bitCount(blackBishops) * BLACK_BISHOP;
        totalEvaluation += Long.bitCount(blackRooks) * BLACK_ROOK;
        totalEvaluation += Long.bitCount(blackQueens) * BLACK_QUEEN;
        totalEvaluation += Long.bitCount(blackKing) * BLACK_KING;

        totalEvaluation += getPositionalBonus();

        //TODO more bonuses to make the eval better and guide the play-style

        return totalEvaluation;
    }

    private double getPositionalBonus(){
        double bonus = 0.0;

        bonus += Long.bitCount(whiteKnights & KNIGHT_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus += Long.bitCount(whiteBishops & BISHOP_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus += Long.bitCount(whiteRooks & ROOK_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus += Long.bitCount(whiteQueens & QUEEN_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus += Long.bitCount(whiteKing & KING_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus += Long.bitCount(whitePawns & PAWN_GOOD_SQUARES) * POSITIONAL_BONUS;

        bonus -= Long.bitCount(blackKnights & KNIGHT_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus -= Long.bitCount(blackBishops & BISHOP_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus -= Long.bitCount(blackRooks & ROOK_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus -= Long.bitCount(blackQueens & QUEEN_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus -= Long.bitCount(blackKing & KING_GOOD_SQUARES) * POSITIONAL_BONUS;
        bonus -= Long.bitCount(blackPawns & PAWN_GOOD_SQUARES) * POSITIONAL_BONUS;

        return bonus;
    }



}
