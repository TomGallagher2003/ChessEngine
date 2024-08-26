package com.example.chess.Validation;

import com.example.chess.Position;
import static com.example.chess.Constants.*;

import static com.example.chess.Validation.King.isValidMoveBlackKing;
import static com.example.chess.Validation.King.isValidMoveWhiteKing;
import static com.example.chess.Validation.Knight.isValidMoveKnight;
import static com.example.chess.Validation.Pawn.isValidMoveBlackPawn;
import static com.example.chess.Validation.Pawn.isValidMoveWhitePawn;
import static com.example.chess.Validation.SlidingPieces.*;

public class MainValidation {

    public static boolean isValidMove(int oldRow, int oldCol, int newRow, int newCol, Position collectionManager) {
        double piece = collectionManager.getPieceValue(oldRow, oldCol);

        if (newCol < 0 || newCol > 7 || newRow < 0 || newRow > 7) {
            return false;
        }
        if (piece == 0.0) {
            return false;
        }
        if (collectionManager.getPieceValue(newRow, newCol) * piece > 0) {
            return false;
        }
        // Direct to individual validation functions by piece.
        if (piece == BLACK_PAWN) {
            return isValidMoveBlackPawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == WHITE_PAWN) {
            return isValidMoveWhitePawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == BLACK_KNIGHT || piece == WHITE_KNIGHT) {
            return isValidMoveKnight(oldRow, newRow, oldCol, newCol);
        } else if (piece == BLACK_ROOK || piece == WHITE_ROOK) {
            return isValidMoveRook(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == BLACK_BISHOP || piece == WHITE_BISHOP) {
            return isValidMoveBishop(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == BLACK_QUEEN || piece == WHITE_QUEEN) {
            return isValidMoveQueen(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == BLACK_KING) {
            return isValidMoveBlackKing(oldRow, newRow, oldCol, newCol);
        } else if (piece == WHITE_KING) {
            return isValidMoveWhiteKing(oldRow, newRow, oldCol, newCol);
        }
        return false;
    }
}
