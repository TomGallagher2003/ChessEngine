package com.example.chess.Utility;

import com.example.chess.Position;
import com.example.chess.model.Move;

import java.util.Comparator;

public class SecondaryMoveComparator implements Comparator<Move> {

    private final Position collectionManager;

    public SecondaryMoveComparator(Position collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    // Try moves with good capture value first
    public int compare(Move move1, Move move2) {
        double mvvLva1 = Math.abs(getValueOfCapturedPiece(move1)) - Math.abs(getValueOfMovingPiece(move2));
        double mvvLva2 = Math.abs(getValueOfCapturedPiece(move2)) - Math.abs(getValueOfMovingPiece(move1));

        return Double.compare(mvvLva2, mvvLva1);
    }

    private double getValueOfCapturedPiece(Move move) {
        return collectionManager.getPieceValue(move.getNewRow(), move.getNewCol());
    }

    private double getValueOfMovingPiece(Move move) {
        return collectionManager.getPieceValue(move.getOldRow(), move.getOldCol());
    }
}
