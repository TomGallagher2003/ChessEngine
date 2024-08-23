package com.example.chess;

import com.example.chess.type.Move;
import com.example.chess.type.MoveComparator;
import com.example.chess.type.SecondaryMoveComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveUtility {
    private boolean justCastled = false;
    private boolean whiteKingMoved = false;
    private boolean blackKingMoved = false;

    public boolean isValidMove(int oldRow, int oldCol, int newRow, int newCol, InfoCollectionManager collectionManager) {
        double piece = collectionManager.getPieceValue(oldRow, oldCol);

        if (newCol < 0 || newCol > 7 || newRow < 0 || newRow > 7) {
            return false;
        }
        if (piece == 0.0) {
            return false;
        }
        if (piece < 0 && putsBlackInCheck(oldRow, newRow, oldCol, newCol, collectionManager)) {
            return false;
        }
        if (piece > 0 && putsWhiteInCheck(oldRow, newRow, oldCol, newCol, collectionManager)) {
            return false;
        }
        if (collectionManager.getPieceValue(newRow, newCol) * piece > 0) {
            return false;
        }
        // Direct to individual validation functions by piece.
        if (piece == InfoCollectionManager.BLACK_PAWN) {
            return isValidMoveBlackPawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.WHITE_PAWN) {
            return isValidMoveWhitePawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_KNIGHT || piece == InfoCollectionManager.WHITE_KNIGHT) {
            return isValidMoveKnight(oldRow, newRow, oldCol, newCol);
        } else if (piece == InfoCollectionManager.BLACK_ROOK || piece == InfoCollectionManager.WHITE_ROOK) {
            return isValidMoveRook(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_BISHOP || piece == InfoCollectionManager.WHITE_BISHOP) {
            return isValidMoveBishop(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_QUEEN || piece == InfoCollectionManager.WHITE_QUEEN) {
            return isValidMoveQueen(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_KING) {
            return isValidMoveBlackKing(oldRow, newRow, oldCol, newCol) || isValidMoveBlackKingCastles(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.WHITE_KING) {
            return isValidMoveWhiteKing(oldRow, newRow, oldCol, newCol) || isValidMoveWhiteKingCastles(oldRow, newRow, oldCol, newCol, collectionManager);
        }
        return false;
    }
    public boolean isValidMoveCheckless(int oldRow, int oldCol, int newRow, int newCol, InfoCollectionManager collectionManager) {
        double piece = collectionManager.getPieceValue(oldRow, oldCol);

        if (newCol < 0 || newCol > 7 || newRow < 0 || newRow > 7) {
            return false;
        }
        if (piece == 0.0) {
            return false;
        }
        if (collectionManager.getPieceValue(newRow, newCol) * piece > 0) {
            return false;
        }

        // Direct to individual validation functions by piece.
        if (piece == InfoCollectionManager.BLACK_PAWN) {
            return isValidMoveBlackPawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.WHITE_PAWN) {
            return isValidMoveWhitePawn(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_KNIGHT || piece == InfoCollectionManager.WHITE_KNIGHT) {
            return isValidMoveKnight(oldRow, newRow, oldCol, newCol);
        } else if (piece == InfoCollectionManager.BLACK_ROOK || piece == InfoCollectionManager.WHITE_ROOK) {
            return isValidMoveRook(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_BISHOP || piece == InfoCollectionManager.WHITE_BISHOP) {
            return isValidMoveBishop(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_QUEEN || piece == InfoCollectionManager.WHITE_QUEEN) {
            return isValidMoveQueen(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.BLACK_KING) {
            return isValidMoveBlackKing(oldRow, newRow, oldCol, newCol) || isValidMoveBlackKingCastles(oldRow, newRow, oldCol, newCol, collectionManager);
        } else if (piece == InfoCollectionManager.WHITE_KING) {
            return isValidMoveWhiteKing(oldRow, newRow, oldCol, newCol) || isValidMoveWhiteKingCastles(oldRow, newRow, oldCol, newCol, collectionManager);
        }
        return false;
    }

    public boolean isValidMoveBlackPawn(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == 1 && colDiff == 0 && collectionManager.getPieceValue(newRow, newCol) == 0.0) {
            justCastled = false;
            return true;
        } else if (newRow == 3 && colDiff == 0 && oldRow == 1 && collectionManager.getPieceValue(newRow, newCol) == 0.0 && collectionManager.getPieceValue(newRow - 1, newCol) == 0.0) {
            justCastled = false;
            return true;
        }
        if (rowDiff == 1 && (colDiff == 1 || colDiff == -1) && collectionManager.getPieceValue(newRow, newCol) > 0) {
            justCastled = false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveWhitePawn(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == -1 && colDiff == 0 && collectionManager.getPieceValue(newRow, newCol) == 0.0) {
            justCastled = false;
            return true;
        } else if (newRow == 4 && colDiff == 0 && oldRow == 6 && collectionManager.getPieceValue(newRow, newCol) == 0.0 && collectionManager.getPieceValue(newRow + 1, newCol) == 0.0) {
            justCastled = false;
            return true;
        }
        if (rowDiff == -1 && (colDiff == 1 || colDiff == -1) && collectionManager.getPieceValue(newRow, newCol) < 0) {
            justCastled = false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveKnight(int oldRow, int newRow, int oldCol, int newCol) {
        if ((Math.abs(newRow - oldRow) == 2 && Math.abs(newCol - oldCol) == 1) || (Math.abs(newRow - oldRow) == 1 && Math.abs(newCol - oldCol) == 2)) {
            justCastled = false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveBishop(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (Math.abs(rowDiff) == Math.abs(colDiff)) {
            int rowDirection = rowDiff > 0 ? 1 : -1;
            int colDirection = colDiff > 0 ? 1 : -1;

            for (int i = 1; i < Math.abs(rowDiff); i++) {
                if (collectionManager.getPieceValue(oldRow + i * rowDirection, oldCol + i * colDirection) != 0.0) {
                    return false;
                }
            }
            justCastled = false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveRook(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;

        if (rowDiff == 0) {
            int colDirection = colDiff > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(colDiff); i++) {
                if (collectionManager.getPieceValue(oldRow, oldCol + i * colDirection) != 0.0) {
                    return false;
                }
            }
            justCastled = false;
            return true;
        } else if (colDiff == 0) {
            int rowDirection = rowDiff > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(rowDiff); i++) {
                if (collectionManager.getPieceValue(oldRow + i * rowDirection, oldCol) != 0.0) {
                    return false;
                }
            }
            justCastled = false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveQueen(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        return isValidMoveBishop(oldRow, newRow, oldCol, newCol, collectionManager) || isValidMoveRook(oldRow, newRow, oldCol, newCol, collectionManager);
    }

    public boolean isValidMoveBlackKing(int oldRow, int newRow, int oldCol, int newCol) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            justCastled = false;
            blackKingMoved = true;
            return true;
        }
        return false;
    }

    public boolean isValidMoveWhiteKing(int oldRow, int newRow, int oldCol, int newCol) {
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if (Math.abs(rowDiff) <= 1 && Math.abs(colDiff) <= 1) {
            justCastled = false;
            whiteKingMoved = true;
            return true;
        }
        return false;
    }

    public boolean isValidMoveBlackKingCastles(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        if (blackKingMoved) return false;
        if (oldRow == 0 && oldCol == 4) {
            if (newRow == 0 && newCol == 6 && collectionManager.getPieceValue(0, 7) == InfoCollectionManager.BLACK_ROOK && collectionManager.getPieceValue(0, 5) == 0) {
                blackKingMoved = true;
                justCastled = true;
                return true;
            } else if (newRow == 0 && newCol == 2 && collectionManager.getPieceValue(0, 0) == InfoCollectionManager.BLACK_ROOK && collectionManager.getPieceValue(0, 2) == 0 && collectionManager.getPieceValue(0, 1) == 0) {
                blackKingMoved = true;
                justCastled = true;
                return true;
            }
        }
        return false;
    }

    public boolean isValidMoveWhiteKingCastles(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        if (whiteKingMoved) return false;
        if (oldRow == 7 && oldCol == 4) {
            if (newRow == 7 && newCol == 6 && collectionManager.getPieceValue(7, 7) == InfoCollectionManager.WHITE_ROOK && collectionManager.getPieceValue(7, 5) == 0) {
                whiteKingMoved = true;
                justCastled = true;
                return true;
            } else if (newRow == 7 && newCol == 2 && collectionManager.getPieceValue(7, 0) == InfoCollectionManager.WHITE_ROOK && collectionManager.getPieceValue(7, 2) == 0 && collectionManager.getPieceValue(7, 1) == 0) {
                justCastled = true;
                whiteKingMoved = true;
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<Boolean>> getLegalMoves(int oldRow, int oldCol, InfoCollectionManager collectionManager) {
        ArrayList<ArrayList<Boolean>> resultMap = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            ArrayList<Boolean> temp = new ArrayList<>();
            for (int col = 0; col < 8; col++) {
                temp.add(isValidMove(oldRow, oldCol, row, col, collectionManager));
            }
            resultMap.add(temp);
        }
        return resultMap;
    }

    private boolean putsBlackInCheck(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        InfoCollectionManager copiedManager = new InfoCollectionManager(collectionManager);
        copiedManager.movePiece(oldRow, oldCol, newRow, newCol, collectionManager.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (copiedManager.getPieceValue(row, col) == InfoCollectionManager.BLACK_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, true, copiedManager);
    }

    private boolean putsWhiteInCheck(int oldRow, int newRow, int oldCol, int newCol, InfoCollectionManager collectionManager) {
        InfoCollectionManager copiedManager = new InfoCollectionManager(collectionManager);
        copiedManager.movePiece(oldRow, oldCol, newRow, newCol, collectionManager.getPieceValue(oldRow, oldCol));

        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (copiedManager.getPieceValue(row, col) == InfoCollectionManager.WHITE_KING) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isSquareUnderAttack(kingRow, kingCol, false, copiedManager);
    }

    private boolean isSquareUnderAttack(int row, int col, boolean isWhite, InfoCollectionManager collectionManager) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                double piece = collectionManager.getPieceValue(r, c);
                if (isWhite && piece < 0) {
                    if (isValidMoveCheckless(r, c, row, col, collectionManager)) {
                        return true;
                    }
                } else if (!isWhite && piece > 0) {
                    if (isValidMoveCheckless(r, c, row, col, collectionManager)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public List<Move> generateLegalMoves(boolean white, InfoCollectionManager collectionManager, boolean withSort) {
        List<Move> legalMoves = new ArrayList<>();

        try {
            for (int oldRow = 0; oldRow < 8; oldRow++) {
                for (int oldCol = 0; oldCol < 8; oldCol++) {
                    double piece = collectionManager.getPieceValue(oldRow, oldCol);

                    if ((piece > 0 && white) || (piece < 0 && !white)) {
                        for (int newRow = 0; newRow < 8; newRow++) {
                            for (int newCol = 0; newCol < 8; newCol++) {

                                if (isValidMoveCheckless(oldRow, oldCol, newRow, newCol, collectionManager)) {
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
            legalMoves.sort(new MoveComparator(collectionManager, white)
                    .thenComparing(new SecondaryMoveComparator(collectionManager, white)));

        }
        return legalMoves;
    }



    public boolean isJustCastled() {
        return justCastled;
    }
}
