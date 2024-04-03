package com.example.chess;

import com.example.chess.type.Move;

import java.util.ArrayList;

public class MoveEngine {
    MoveUtility moveUtil = new MoveUtility();

    public Move getRandomMove(boolean white, ArrayList<ArrayList<Double>> map){
        for(int row= 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(map.get(row).get(col) < 1 && white || map.get(row).get(col) > 1 && !white )
                for(int moveRow = 0; moveRow < 8; moveRow++){
                    for(int moveCol = 0; moveCol < 8; moveCol++){
                        if(moveUtil.getLegalMoves(row, col, map).get(moveRow).get(moveCol)){
                            return new Move(row, col, moveRow, moveCol);
                        }
                    }
                }
            }
        }
        return null;
    }
}
