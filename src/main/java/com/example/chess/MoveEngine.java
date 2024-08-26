package com.example.chess;

import com.example.chess.Validation.Checks;
import com.example.chess.model.Move;

import java.util.List;

import static com.example.chess.MoveGenerator.generateLegalMovesEngine;

public class MoveEngine {


    private Position simulateMove(Move move, Position collectionManager) {
        Position newCollectionManager = new Position(collectionManager);
        newCollectionManager.movePiece(move.getOldRow(), move.getOldCol(), move.getNewRow(), move.getNewCol(), collectionManager.getPieceValue(move.getOldRow(), move.getOldCol()));
        return newCollectionManager;
    }

    private double evaluateBoard(Position collectionManager) {
        double totalValue = 0.0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                totalValue += collectionManager.getPieceValue(row, col);
            }
        }
        return totalValue;
    }

    private double minimax(Position collectionManager, int depth, double alpha, double beta, boolean maximizingPlayer) {

        if (depth == 0) {
            double eval = evaluateBoard(collectionManager);
            return eval;
        }

        List<Move> legalMoves = generateLegalMovesEngine(maximizingPlayer, collectionManager, true);

        if (maximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : legalMoves) {
                Position newCollectionManager = simulateMove(move, collectionManager);
                double eval = minimax(newCollectionManager, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : legalMoves) {
                Position newCollectionManager = simulateMove(move, collectionManager);
                double eval = minimax(newCollectionManager, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }




    public Move getNiceMove(boolean playerWhite, Position collectionManager, int depth) {
        Move bestMove = null;
        double bestScore = Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        List<Move> blackMoves = generateLegalMovesEngine(false, collectionManager, true);

        for (Move move : blackMoves) {
            Position newCollectionManager = simulateMove(move, collectionManager);

            double score = minimax(newCollectionManager, depth, alpha, beta, true);

            if (score < bestScore) {
                bestScore = score;
                bestMove = move;
            }
            beta = Math.min(beta, bestScore);

            if (beta <= alpha) {
                break;
            }
        }
        //TODO
        if(Checks.putsBlackInCheck(bestMove.getOldRow(), bestMove.getNewRow(), bestMove.getOldCol(), bestMove.getNewCol(), collectionManager)){
            return bestMove;
        }

        return bestMove;
    }


}
