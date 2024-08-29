package com.example.chess.Validation;

import com.example.chess.Position;

public class SlidingPieces {
    public static boolean isValidMoveBishop(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) == Math.abs(colDiff)) {
            int rowDirection = rowDiff > 0 ? 1 : -1;
            int colDirection = colDiff > 0 ? 1 : -1;

            for (int i = 1; i < Math.abs(rowDiff); i++) {
                if (position.getPieceValue(oldRow + i * rowDirection, oldCol + i * colDirection) != 0.0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isValidMoveRook(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == 0) {
            int colDirection = colDiff > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(colDiff); i++) {
                if (position.getPieceValue(oldRow, oldCol + i * colDirection) != 0.0) {
                    return false;
                }
            }
            return true;
        } else if (colDiff == 0) {
            int rowDirection = rowDiff > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(rowDiff); i++) {
                if (position.getPieceValue(oldRow + i * rowDirection, oldCol) != 0.0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isValidMoveQueen(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        return isValidMoveBishop(oldRow, newRow, oldCol, newCol, position) || isValidMoveRook(oldRow, newRow, oldCol, newCol, position);
    }

}
