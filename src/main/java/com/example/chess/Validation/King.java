package com.example.chess.Validation;

import com.example.chess.Position;

import static com.example.chess.Validation.Checks.isSquareUnderAttack;

public class King {

    public static boolean isValidMoveBlackKing(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            return true;
        }
        if(colDiff == 2 && rowDiff == 0
                && position.getPieceValue(oldRow, oldCol + 1) == 0
                && !isSquareUnderAttack(oldRow, oldCol, false, position)
                && !isSquareUnderAttack(oldRow, oldCol + 1, false, position)
                && !isSquareUnderAttack(oldRow, oldCol + 2, false, position)
                && position.isUnmovedKing(oldRow, oldCol)
                && position.isUnmovedRook(oldRow, 7)){
            return true;
        }
        if(colDiff == -2 && rowDiff == 0
                && position.getPieceValue(oldRow, oldCol - 1) == 0
                && !isSquareUnderAttack(oldRow, oldCol, false, position)
                && !isSquareUnderAttack(oldRow, oldCol - 1, false, position)
                && !isSquareUnderAttack(oldRow, oldCol - 2, false, position)
                && position.isUnmovedKing(oldRow, oldCol)
                && position.isUnmovedRook(oldRow, 0)){
            return true;
        }
        return false;
    }

    public static boolean isValidMoveWhiteKing(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            return true;
        }
        if(colDiff == 2 && rowDiff == 0
                && position.getPieceValue(oldRow, oldCol + 1) == 0
                && !isSquareUnderAttack(oldRow, oldCol, true, position)
                && !isSquareUnderAttack(oldRow, oldCol + 1, true, position)
                && !isSquareUnderAttack(oldRow, oldCol + 2, true, position)
                && position.isUnmovedKing(oldRow, oldCol)
                && position.isUnmovedRook(oldRow, 7)){
            return true;
        }
        if(colDiff == -2 && rowDiff == 0
                && position.getPieceValue(oldRow, oldCol - 1) == 0
                && !isSquareUnderAttack(oldRow, oldCol, true, position)
                && !isSquareUnderAttack(oldRow, oldCol - 1, true, position)
                && !isSquareUnderAttack(oldRow, oldCol - 2, true, position)
                && position.isUnmovedKing(oldRow, oldCol)
                && position.isUnmovedRook(oldRow, 0)){
            return true;
        }
        return false;
    }
}
