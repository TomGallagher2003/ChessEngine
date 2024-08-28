package com.example.chess.Validation;

import com.example.chess.Position;
import com.example.chess.model.Move;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.chess.Constants.*;
import static com.example.chess.MoveGenerator.generateLegalMovesEngine;
import static com.example.chess.Validation.MainValidation.isValidMove;


public class Checks {

    public static boolean putsBlackInCheck(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        Position copiedPosition = new Position(position);
        copiedPosition.movePiece(oldRow, oldCol, newRow, newCol, position.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (copiedPosition.getPieceValue(row, col) == BLACK_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, false, copiedPosition);
    }

    public static boolean putsWhiteInCheck(int oldRow, int newRow, int oldCol, int newCol, Position position) {
        Position positionCopy = new Position(position);
        positionCopy.movePiece(oldRow, oldCol, newRow, newCol, position.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (positionCopy.getPieceValue(row, col) == WHITE_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, true, positionCopy);
    }

    public static boolean isSquareUnderAttack(int row, int col, boolean isWhite, Position position) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                double piece = position.getPieceValue(r, c);
                if (isWhite && piece < 0) {
                    if (isValidMove(r, c, row, col, position)) {
                        return true;
                    }
                } else if (!isWhite && piece > 0) {
                    if (isValidMove(r, c, row, col, position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean whiteInCheckmate(Position position){
        List<Move> moves = generateLegalMovesEngine(true, position, false);
        moves = moves.stream()
                .filter(move -> !putsWhiteInCheck(move.getOldRow(), move.getNewRow(), move.getOldCol(), move.getNewCol(), position))
                .collect(Collectors.toList());
        return moves.size() == 0;
    }
}
