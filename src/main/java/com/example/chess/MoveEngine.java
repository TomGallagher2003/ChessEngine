package com.example.chess;

import com.example.chess.type.Move;
import com.example.chess.type.MoveComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.chess.InfoCollectionManager.copyMap;
import static com.example.chess.InfoCollectionManager.copyMapMove;

public class MoveEngine {
    MoveUtility moveUtil = new MoveUtility();

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
    private double minimax(ArrayList<ArrayList<Double>> map, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return pieceTotal(map); // base case
        }

        List<Move> legalMoves = moveUtil.generateLegalMoves(maximizingPlayer, map);

        if (maximizingPlayer) {
            double bestScore = Double.NEGATIVE_INFINITY;
            for (Move move : legalMoves) {
                ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move);
                double score = minimax(newMap, depth - 1, alpha, beta, false);
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return bestScore;
        } else {
            double bestScore = Double.POSITIVE_INFINITY;
            for (Move move : legalMoves) {
                ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move);
                double score = minimax(newMap, depth - 1, alpha, beta, true);
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return bestScore;
        }
    }

    public Move getNiceMove(boolean playerWhite, ArrayList<ArrayList<Double>> map, int depth) {
        Move bestMove = null;
        double bestScore = playerWhite ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        ArrayList<Move> legalMoves = moveUtil.generateLegalMoves(playerWhite, map);

        for (Move move : legalMoves) {
            ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move);
            double score = minimax(newMap, depth - 1, alpha, beta, !playerWhite);
            if ((playerWhite && score > bestScore) || (!playerWhite && score < bestScore)) {
                bestScore = score;
                bestMove = move;
            }
            if (playerWhite) {
                alpha = Math.max(alpha, bestScore);
            } else {
                beta = Math.min(beta, bestScore);
            }
            if (beta <= alpha) {
                break; // Beta cut-off
            }
        }
        return bestMove;
    }

}
