package com.example.chess.type;

import java.util.ArrayList;
import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {

    private ArrayList<ArrayList<Double>> map;

    public MoveComparator(ArrayList<ArrayList<Double>> map) {
        this.map = map;
    }

    @Override
    public int compare(Move move1, Move move2) {
        double mvvLva1 = getValueOfCapturedPiece(move1) - getValueOfMovingPiece(move1);
        double mvvLva2 = getValueOfCapturedPiece(move2) - getValueOfMovingPiece(move2);
        return Double.compare(mvvLva2, mvvLva1); // Sort in descending order
    }

    private double getValueOfCapturedPiece(Move move) {
        return map.get(move.getNewRow()).get(move.getNewCol());
    }

    private double getValueOfMovingPiece(Move move) {
        return map.get(move.getOldRow()).get(move.getOldCol());
    }
}