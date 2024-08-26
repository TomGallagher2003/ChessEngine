package com.example.chess.Utility;

import com.example.chess.Position;
import com.example.chess.model.Move;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {

    private final Position collectionManager;
    private final boolean white;

    public MoveComparator(Position collectionManager, boolean white) {
        this.collectionManager = collectionManager;
        this.white = white;
    }

    @Override
    // Try moves with good capture value first
    public int compare(Move move1, Move move2) {
        double mvvLva1 = getValueOfCapturedPiece(move1);
        double mvvLva2 = getValueOfCapturedPiece(move2);

        if (white) {
            return Double.compare(mvvLva1, mvvLva2);
        }

        return Double.compare(mvvLva2, mvvLva1);
    }

    private double getValueOfCapturedPiece(Move move) {
        return collectionManager.getPieceValue(move.getNewRow(), move.getNewCol());
    }

    private double getValueOfMovingPiece(Move move) {
        return collectionManager.getPieceValue(move.getOldRow(), move.getOldCol());
    }
}
