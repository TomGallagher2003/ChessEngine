package com.example.chess;

import com.example.chess.type.Move;

import java.util.ArrayList;

import static com.example.chess.MoveUtility.copyMap;

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
    public Move getBestMove(boolean playerWhite, ArrayList<ArrayList<Double>> map){
        Move move = new Move(0, 0, 0, 0);
        Move testMove = new Move(0, 0, 0, 0);
        Double eval = playerWhite ? 10000.0 : -10000.0;
        Double testEval = 0.0;
        ArrayList<ArrayList<Boolean>> currentLegalMoves = null;
        for(int row= 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                currentLegalMoves = moveUtil.getLegalMoves(row, col, map);
                if((map.get(row).get(col) < 1 && playerWhite) || (map.get(row).get(col) > 1 && !playerWhite) )
                    for(int moveRow = 0; moveRow < 8; moveRow++){
                        for(int moveCol = 0; moveCol < 8; moveCol++){
                            if(currentLegalMoves.get(moveRow).get(moveCol)){
                                testMove.setAll(row, col, moveRow, moveCol);
                                testEval = pieceTotalMove(testMove, map);
                                if((testEval < eval && playerWhite) || (testEval > eval && !playerWhite)){
                                    move.setAll(row, col, moveRow, moveCol);
                                    eval = testEval;
                                }

                            }
                        }
                    }
            }
        }
        System.out.println(move.getOldRow() + ", " +move.getOldCol() + ", " +move.getNewRow() + ", " +move.getNewCol() + ": eval = " + String.format("%.2f", eval));
        return move;
    }


    public static Double pieceTotalMove(Move move, ArrayList<ArrayList<Double>> map){
        ArrayList<ArrayList<Double>> ifMap = copyMap(map);
        ifMap.get(move.getNewRow()).set(move.getNewCol(), ifMap.get(move.getOldRow()).get(move.getOldCol()));
        ifMap.get(move.getOldRow()).set(move.getOldCol(), 0.0);
        return pieceTotal(ifMap);
    }
    public static Double pieceTotal(ArrayList<ArrayList<Double>> map) {
        Double sum = 0.0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                sum+= map.get(row).get(col);
            }
        }
        return sum;
    }
}
