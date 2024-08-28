package com.example.chess;

import com.example.chess.model.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.chess.MoveGenerator.generateLegalMovesEngine;
import static com.example.chess.Utility.OpeningParser.getMoveFromString;
import static com.example.chess.Validation.Checks.putsBlackInCheck;
import static com.example.chess.Constants.*;

public class MoveEngine {


    private static Position simulateMove(Move move, Position position) {
        Position newCollectionManager = new Position(position);
        newCollectionManager.movePiece(move.getOldRow(), move.getOldCol(), move.getNewRow(), move.getNewCol(), position.getPieceValue(move.getOldRow(), move.getOldCol()));
        return newCollectionManager;
    }


    private static double minimax(Position position, int depth, double alpha, double beta, boolean maximizingPlayer) {

        if (depth == 0) {
            double eval = position.evaluate();
            return eval;
        }

        List<Move> legalMoves = generateLegalMovesEngine(maximizingPlayer, position, true);

        if (maximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : legalMoves) {
                Position simulatePosition = simulateMove(move, position);
                double eval = minimax(simulatePosition, depth - 1, alpha, beta, false);
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
                Position newCollectionManager = simulateMove(move, position);
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




    public static Move getEngineMove(Position position, int depth, List<Move> blackMoves) {
        if(blackMoves.isEmpty()){
            return CHECKMATE;
        }
        Move bestMove = null;
        double bestScore = Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;


        for (Move move : blackMoves) {
            Position newCollectionManager = simulateMove(move, position);

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

        if(putsBlackInCheck(bestMove.getOldRow(), bestMove.getNewRow(), bestMove.getOldCol(), bestMove.getNewCol(), position)){
            blackMoves = blackMoves.stream()
                    .filter(move -> !putsBlackInCheck(move.getOldRow(), move.getNewRow(), move.getOldCol(), move.getNewCol(), position))
                    .collect(Collectors.toList());
            return getEngineMove(position, depth, blackMoves);
        }

        return bestMove;
    }

    public static Move getEngineMove(Position position, int depth){
        List<Move> blackMoves = generateLegalMovesEngine(false, position, true);
        return getEngineMove(position, depth, blackMoves);
    }

    public static Move getOpeningMove(String playedMoves, Position position){
        List<String> options = new ArrayList<>();
        for(String opening : OPENINGS){
            if(opening.startsWith(playedMoves)){
                options.add(opening.split(playedMoves)[1].trim().split("\\s+")[0]);
            }
        }
        if(options.size() > 0){
            Random random = new Random();
            return getMoveFromString(options.get(random.nextInt(options.size())), position);
        }
        return null;
    }
}
