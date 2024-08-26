package com.example.chess.Validation;

import com.example.chess.Position;
import static com.example.chess.Constants.*;
import static com.example.chess.Validation.MainValidation.isValidMove;


public class Checks {

    public static boolean putsBlackInCheck(int oldRow, int newRow, int oldCol, int newCol, Position collectionManager) {
        Position copiedManager = new Position(collectionManager);
        copiedManager.movePiece(oldRow, oldCol, newRow, newCol, collectionManager.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (copiedManager.getPieceValue(row, col) == BLACK_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, true, copiedManager);
    }

    public static boolean putsWhiteInCheck(int oldRow, int newRow, int oldCol, int newCol, Position collectionManager) {
        Position copiedManager = new Position(collectionManager);
        copiedManager.movePiece(oldRow, oldCol, newRow, newCol, collectionManager.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (copiedManager.getPieceValue(row, col) == WHITE_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, false, copiedManager);
    }

    public static boolean isSquareUnderAttack(int row, int col, boolean isWhite, Position collectionManager) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                double piece = collectionManager.getPieceValue(r, c);
                if (isWhite && piece < 0) {
                    if (isValidMove(r, c, row, col, collectionManager)) {
                        return true;
                    }
                } else if (!isWhite && piece > 0) {
                    if (isValidMove(r, c, row, col, collectionManager)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
