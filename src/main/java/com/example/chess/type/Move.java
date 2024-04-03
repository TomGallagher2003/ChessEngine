package com.example.chess.type;

public class Move {

    int oldRow;
    int newRow;
    int oldCol;
    int newCol;

    public Move(int oldRow, int oldCol, int newRow, int newCol){
        this.oldRow = oldRow;
        this.newRow = newRow;
        this.oldCol = oldCol;
        this.newCol = newCol;
    }
    public int getOldRow(){
        return oldRow;
    }
    public int getNewCol(){
        return newCol;
    }
    public int getNewRow(){
        return newRow;
    }
    public int getOldCol(){
        return oldCol;
    }
}
