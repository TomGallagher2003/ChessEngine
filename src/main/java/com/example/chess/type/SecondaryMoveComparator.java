package com.example.chess.type;

import com.example.chess.Position;

import java.util.Comparator;

public class SecondaryMoveComparator implements Comparator<Move> {

    private final Position collectionManager;
    private final boolean white;

    public SecondaryMoveComparator(Position collectionManager, boolean white) {
        this.collectionManager = collectionManager;
        this.white = white;
    }

    @Override
    // Try moves with high value pieces first, I think this make sense maybe?
    public int compare(Move move1, Move move2) {
        double mvvLva1 = getValueOfMovingPiece(move1);
        double mvvLva2 = getValueOfMovingPiece(move2);

        if (!white) {
            return Double.compare(mvvLva1, mvvLva2); // Sort in ascending order for black
        }

        return Double.compare(mvvLva2, mvvLva1); // Sort in descending order for white
    }

    private double getValueOfMovingPiece(Move move) {
        return collectionManager.getPieceValue(move.getOldRow(), move.getOldCol());
    }
}
