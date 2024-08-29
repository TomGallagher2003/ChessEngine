package com.example.chess;

import com.example.chess.model.Move;
import com.example.chess.Utility.MoveComparator;
import com.example.chess.Utility.SecondaryMoveComparator;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.Validation.Checks.putsWhiteInCheck;
import static com.example.chess.Validation.MainValidation.isValidMove;

public class MoveGenerator {

    public static List<Move> generateLegalMovesEngine(boolean white, Position position, boolean withSort) {
        List<Move> legalMoves = new ArrayList<>();

        try {
            for (int oldRow = 0; oldRow < 8; oldRow++) {
                for (int oldCol = 0; oldCol < 8; oldCol++) {
                    double piece = position.getPieceValue(oldRow, oldCol);

                    if ((piece > 0 && white) || (piece < 0 && !white)) {
                        for (int newRow = 0; newRow < 8; newRow++) {
                            for (int newCol = 0; newCol < 8; newCol++) {

                                if (isValidMove(oldRow, oldCol, newRow, newCol, position)) {
                                    legalMoves.add(new Move(oldRow, oldCol, newRow, newCol));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(withSort){
            legalMoves.sort(new MoveComparator(position, white)
                    .thenComparing(new SecondaryMoveComparator(position)));

        }
        return legalMoves;
    }

    public static ArrayList<ArrayList<Boolean>> generateLegalMovesPlayer(int oldRow, int oldCol, Position position) {
        ArrayList<ArrayList<Boolean>> resultMap = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            ArrayList<Boolean> temp = new ArrayList<>();
            for (int col = 0; col < 8; col++) {
                temp.add(isValidMove(oldRow, oldCol, row, col, position) && !putsWhiteInCheck(oldRow, row, oldCol, col, position));
            }
            resultMap.add(temp);
        }

        return resultMap;
    }

    // used to find the current location of the piece being referenced in the opening string
    public static Move findPieceToMove(int newRow, int newCol, Double piece, Position position, Integer oldCol){
        for(Move move: generateLegalMovesEngine(false, position, false)){
            if(move.getNewCol() == newCol && move.getNewRow() == newRow
                    && position.getPieceValue(move.getOldRow(), move.getOldCol()) == piece){
                    if(oldCol == null || move.getOldCol() == oldCol){
                        return move;
                    }
            }
        }
        return null;
    }
}
