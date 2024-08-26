package com.example.chess.Validation;

import com.example.chess.Position;

public class Pawn{

    public static boolean isValidMoveBlackPawn(int oldRow, int newRow, int oldCol, int newCol, Position collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == 1 && colDiff == 0 && collectionManager.getPieceValue(newRow, newCol) == 0.0) {
            return true;
        } else if (newRow == 3 && colDiff == 0 && oldRow == 1 && collectionManager.getPieceValue(newRow, newCol) == 0.0 && collectionManager.getPieceValue(newRow - 1, newCol) == 0.0) {
            return true;
        }
        if (rowDiff == 1 && (colDiff == 1 || colDiff == -1) && collectionManager.getPieceValue(newRow, newCol) > 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidMoveWhitePawn(int oldRow, int newRow, int oldCol, int newCol, Position collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == -1 && colDiff == 0 && collectionManager.getPieceValue(newRow, newCol) == 0.0) {
            return true;
        } else if (newRow == 4 && colDiff == 0 && oldRow == 6 && collectionManager.getPieceValue(newRow, newCol) == 0.0 && collectionManager.getPieceValue(newRow + 1, newCol) == 0.0) {
            return true;
        }
        if (rowDiff == -1 && (colDiff == 1 || colDiff == -1) && collectionManager.getPieceValue(newRow, newCol) < 0) {
            return true;
        }
        return false;
    }
}
