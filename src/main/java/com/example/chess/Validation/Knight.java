package com.example.chess.Validation;

public class Knight {

    public static boolean isValidMoveKnight(int oldRow, int newRow, int oldCol, int newCol) {
        if ((Math.abs(newRow - oldRow) == 2 && Math.abs(newCol - oldCol) == 1) || (Math.abs(newRow - oldRow) == 1 && Math.abs(newCol - oldCol) == 2)) {
            return true;
        }
        return false;
    }

}
