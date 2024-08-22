package com.example.chess;

import com.example.chess.type.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveEngine {
    private final MoveUtility moveUtil = new MoveUtility();

    public Move getBestMove(boolean playerWhite, InfoCollectionManager collectionManager) {
        Move bestMove = null;
        double bestEval = playerWhite ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        List<Move> legalMoves = moveUtil.generateLegalMoves(playerWhite, collectionManager);

        // Filter the moves to ensure that the final selected move is for black pieces
        List<Move> blackMoves = legalMoves.stream()
                .filter(move -> collectionManager.getPieceValue(move.getOldRow(), move.getOldCol()) < 0)
                .collect(Collectors.toList());

        for (Move move : blackMoves) {
            InfoCollectionManager newCollectionManager = simulateMove(move, collectionManager);
            double eval = evaluateBoard(newCollectionManager);

            if ((playerWhite && eval > bestEval) || (!playerWhite && eval < bestEval)) {
                bestMove = move;
                bestEval = eval;
            }
        }

        return bestMove;
    }

    private InfoCollectionManager simulateMove(Move move, InfoCollectionManager collectionManager) {
        InfoCollectionManager newCollectionManager = new InfoCollectionManager(collectionManager);
        // Perform the move in the new collection manager
        newCollectionManager.movePiece(move.getOldRow(), move.getOldCol(), move.getNewRow(), move.getNewCol(), collectionManager.getPieceValue(move.getOldRow(), move.getOldCol()));
        return newCollectionManager;
    }

    private double evaluateBoard(InfoCollectionManager collectionManager) {
        double totalValue = 0.0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                totalValue += collectionManager.getPieceValue(row, col);
            }
        }
        return totalValue;
    }

    private double minimax(InfoCollectionManager collectionManager, int depth, double alpha, double beta, boolean maximizingPlayer) {

        if (depth == 0) {
            double eval = evaluateBoard(collectionManager);
            return eval;
        }

        List<Move> legalMoves = moveUtil.generateLegalMoves(maximizingPlayer, collectionManager);

        if (maximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : legalMoves) {
                InfoCollectionManager newCollectionManager = simulateMove(move, collectionManager);
                double eval = minimax(newCollectionManager, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : legalMoves) {
                InfoCollectionManager newCollectionManager = simulateMove(move, collectionManager);
                double eval = minimax(newCollectionManager, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return minEval;
        }
    }




    public Move getNiceMove(boolean playerWhite, InfoCollectionManager collectionManager, int depth) {
        Move bestMove = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        // Generate legal moves for the black player (as this is the function call for the engine to move black pieces)
        List<Move> blackMoves = moveUtil.generateLegalMoves(false, collectionManager);

        for (Move move : blackMoves) {
            InfoCollectionManager newCollectionManager = simulateMove(move, collectionManager);

            // Minimax expects the opponent's turn, so we switch to the other player
            double score = minimax(newCollectionManager, depth, alpha, beta, true);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            alpha = Math.max(alpha, bestScore);

            if (beta <= alpha) {
                break; // Alpha-Beta cut-off
            }
        }

        return bestMove;
    }

}
