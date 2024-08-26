package com.example.chess.Validation;

public class King {

    public static boolean isValidMoveBlackKing(int oldRow, int newRow, int oldCol, int newCol) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            return true;
        }
        return false;
    }

    public static boolean isValidMoveWhiteKing(int oldRow, int newRow, int oldCol, int newCol) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            return true;
        }
        return false;
    }
}
