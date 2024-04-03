package com.example.chess;

import com.example.chess.type.Move;
import com.example.chess.type.KillerHeuristic;

import java.util.ArrayList;

import static com.example.chess.InfoCollectionManager.copyMap;
import static com.example.chess.InfoCollectionManager.copyMapMove;

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
    private double minimax(ArrayList<ArrayList<Double>> map, int depth, double alpha, double beta, boolean playerWhite) {
        if (depth == 0) {
            // Return evaluation of the current board state
            return pieceTotal(map);
        }

        ArrayList<Move> killerMoves = KillerHeuristic.getKillerMoves(depth);

        if (playerWhite) {
            double bestScore = Double.NEGATIVE_INFINITY;
            // Generate all possible moves for the maximizing player
            ArrayList<Move> legalMoves = moveUtil.generateLegalMoves(true, map);
            // Prioritize killer moves in move ordering
            ArrayList<Move> orderedMoves = prioritizeKillerMoves(legalMoves, killerMoves);
            for (Move move : orderedMoves) {
                ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move);
                double score = minimax(newMap, depth - 1, alpha, beta, false);
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore); // Update alpha
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return bestScore;
        } else {
            double bestScore = Double.POSITIVE_INFINITY;
            // Generate all possible moves for the minimizing player
            ArrayList<Move> legalMoves = moveUtil.generateLegalMoves(false, map);
            // Prioritize killer moves in move ordering
            ArrayList<Move> orderedMoves = prioritizeKillerMoves(legalMoves, killerMoves);
            for (Move move : orderedMoves) {
                ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move);
                double score = minimax(newMap, depth - 1, alpha, beta, true);
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore); // Update beta
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return bestScore;
        }
    }

    // Function to prioritize killer moves in move ordering
    private ArrayList<Move> prioritizeKillerMoves(ArrayList<Move> legalMoves, ArrayList<Move> killerMoves) {
        ArrayList<Move> orderedMoves = new ArrayList<>();
        // Add killer moves first
        for (Move killerMove : killerMoves) {
            if (legalMoves.contains(killerMove)) {
                orderedMoves.add(killerMove);
            }
        }
        // Add remaining legal moves
        for (Move move : legalMoves) {
            if (!orderedMoves.contains(move)) {
                orderedMoves.add(move);
            }
        }
        return orderedMoves;

    }
    public Move getNiceMove(boolean playerWhite, ArrayList<ArrayList<Double>> map, int depth) {
        Move bestMove = null;
        double bestScore = playerWhite ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY; // Initialize alpha
        double beta = Double.POSITIVE_INFINITY; // Initialize beta

        // Generate all possible moves for the current player
        ArrayList<Move> legalMoves = moveUtil.generateLegalMoves(playerWhite, map);

        // Iterate over each possible move and evaluate using Minimax with alpha-beta pruning
        for (Move move : legalMoves) {
            ArrayList<ArrayList<Double>> newMap = copyMapMove(map, move); // Make the move on a copy of the board
            double score = minimax(newMap, depth - 1, alpha, beta, false); // Minimax search
            // Update the best move if necessary
            if ((playerWhite && score > bestScore) || (!playerWhite && score < bestScore)) {
                bestScore = score;
                bestMove = move;
            }
            if (playerWhite) {
                alpha = Math.max(alpha, bestScore); // Update alpha
            } else {
                beta = Math.min(beta, bestScore); // Update beta
            }
            if (beta <= alpha) {
                break; // Beta cut-off
            }
        }

        return bestMove;
    }
}
