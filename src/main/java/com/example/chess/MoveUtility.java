package com.example.chess;

import com.example.chess.type.Move;
import com.example.chess.type.MoveComparator;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.chess.InfoCollectionManager.copyMap;

public class MoveUtility {
    boolean justCastled = false;
    boolean whiteKingMoved = false;
    boolean blackKingMoved = false;

    public boolean isValidMove(int oldRow, int oldCol, int newRow, int newCol, ArrayList<ArrayList<Double>> map){
        Double piece = map.get(oldRow).get(oldCol);
        if (newCol < 0 ||newCol > 7 || newRow < 0 || newRow > 7){
            return false;
        }
        if(piece < 0 && putsBlackInCheck(oldRow, newRow, oldCol, newCol, map)){
            return false;
        }
        if(piece > 0 && putsWhiteInCheck(oldRow, newRow, oldCol, newCol, map)){
            return false;
        }
        if (map.get(newRow).get(newCol) * piece > 0 && map.get(newRow).get(newCol) != 0) {
            return false;
        }
        //direct to individual validation functions by piece.
        if(piece == -1){return isValidMoveBlackPawn(oldRow, newRow, oldCol, newCol, map);
        } else if(piece == 1){return isValidMoveWhitePawn(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -3){return isValidMoveKnight(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 3){return isValidMoveKnight(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -5.3){return isValidMoveRook(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 5.3){return isValidMoveRook(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -3.3){return isValidMoveBishop(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 3.3){return isValidMoveBishop(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -10){return isValidMoveQueen(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 10){return isValidMoveQueen(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -255){return (isValidMoveBlackKing(oldRow, newRow, oldCol, newCol, map) ||isValidMoveBlackKingCastles(oldRow, newRow, oldCol, newCol, map)) ;}
        else if(piece == 255){return (isValidMoveWhiteKing(oldRow, newRow, oldCol, newCol, map)||isValidMoveWhiteKingCastles(oldRow, newRow, oldCol, newCol, map));
        }
        return false;

    }
    public boolean isValidMoveCheckless(int oldRow, int oldCol, int newRow, int newCol, ArrayList<ArrayList<Double>> map){
        //check the square exists
        if (newCol < 0 ||newCol > 7 || newRow < 0 || newRow > 7){
            return false;
        }
        if (map.get(newRow).get(newCol) * map.get(oldRow).get(oldCol) > 0) {
            // Pieces have the same sign, indicating that they are of the same color
            return false;
        }
        Double piece = map.get(oldRow).get(oldCol);
        // need to add a check for "checks"
        //direct to individual validation functions by piece.
        if(piece == -1){return isValidMoveBlackPawn(oldRow, newRow, oldCol, newCol, map);
        } else if(piece == 1){return isValidMoveWhitePawn(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -3){return isValidMoveKnight(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 3){return isValidMoveKnight(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -5.3){return isValidMoveRook(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 5.3){return isValidMoveRook(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -3.3){return isValidMoveBishop(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 3.3){return isValidMoveBishop(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -10){return isValidMoveQueen(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 10){return isValidMoveQueen(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == -255){return isValidMoveBlackKing(oldRow, newRow, oldCol, newCol, map);}
        else if(piece == 255){return isValidMoveWhiteKing(oldRow, newRow, oldCol, newCol, map);
        }
        return false;

    }
    public boolean isValidMoveBlackPawn(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if(rowDiff == 1 && colDiff == 0 && map.get(newRow).get(newCol) == 0.0){
            justCastled=false;
            return true;
        } else if(newRow == 3 && colDiff == 0 && oldRow == 1 && map.get(newRow).get(newCol) == 0.0 && map.get(newRow -1).get(newCol) == 0.0){
            justCastled=false;
            return true;
        }
        if(rowDiff == 1 && (colDiff == 1 || colDiff == -1) && map.get(newRow).get(newCol) > 0){
            if(map.get(newRow).get(newCol) > 0){
                justCastled=false;
                return true;
            }
        }
        return false;
    }
    public boolean isValidMoveWhitePawn(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if(rowDiff == -1 && colDiff == 0 && map.get(newRow).get(newCol) == 0.0){
            justCastled=false;
            return true;
        } else if(newRow == 4 && colDiff == 0 && oldRow == 6 && map.get(newRow).get(newCol) == 0.0 && map.get(newRow+1).get(newCol ) == 0.0){
            justCastled=false;
            return true;
        }
        if(rowDiff == -1 && (colDiff == 1 || colDiff == -1)&& map.get(newRow).get(newCol) < 0){
            if(map.get(newRow).get(newCol) < 0){
                justCastled=false;
                return true;
            }
        }
        return false;
    }
    public boolean isValidMoveKnight(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        if((oldRow + 2 == newRow || oldRow - 2 == newRow) && (oldCol + 1 == newCol || oldCol - 1 == newCol)){
            justCastled=false;
            return true;
        }else if((oldRow + 1 == newRow || oldRow - 1 == newRow) && (oldCol + 2 == newCol || oldCol - 2 == newCol)){
            justCastled=false;
            return true;
        }
        return false;
    }
    public boolean isValidMoveBishop(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if((colDiff == rowDiff)){
            if(rowDiff<1){
                for(int i = 1; i < -1*rowDiff; i++){
                    if(map.get(oldRow-i).get(oldCol-i) != 0.0){
                        return false;
                    }
                }
            } else{
                for(int i = 1; i < rowDiff; i++){
                    if(map.get(oldRow+i).get(oldCol+i) != 0.0){
                        return false;
                    }
                }
            }
            return true;
        } else if((colDiff == -1*rowDiff)){
            if(rowDiff<1){
                for(int i = 1; i < -1*rowDiff; i++){
                    if(map.get(oldRow-i).get(oldCol+i) != 0.0){
                        return false;
                    }
                }
            } else{
                for(int i = 1; i < rowDiff; i++){
                    if(map.get(oldRow+i).get(oldCol-i) != 0.0){
                        return false;
                    }
                }
            }
            justCastled=false;
            return true;
        }
        return false;
    }
    public boolean isValidMoveRook(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if(rowDiff == 0){
            //right
            if(colDiff > 0){
                for(int i = 1; i < colDiff; i++){
                    if(map.get(oldRow).get(oldCol+i) != 0.0){
                        return false;
                    }
                }
                //left
            } else{
                for(int i = 1; i < Math.abs(colDiff); i++){
                    if(map.get(oldRow).get(oldCol-i) != 0.0){
                        return false;
                    }
                }
            }
            justCastled=false;
            return true;

        } else if(colDiff == 0){
            //up
            if(rowDiff > 0){
                for(int i = 1; i < rowDiff; i++){
                    if(map.get(oldRow+i).get(oldCol) != 0.0){
                        return false;
                    }
                }
                //down
            } else{
                for(int i = 1; i < Math.abs(rowDiff); i++){
                    if(map.get(oldRow-i).get(oldCol) != 0.0){
                        return false;
                    }
                }
            }
            justCastled=false;
            return true;
        }
        return false;
    }

    public boolean isValidMoveQueen(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        if(isValidMoveBishop(oldRow, newRow, oldCol, newCol, map) ||isValidMoveRook(oldRow, newRow, oldCol, newCol, map) ){
            justCastled=false;
            return true;
        }
        return false;
    }
    public boolean isValidMoveBlackKing(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if(colDiff <= 1 && rowDiff <=1 && rowDiff>=-1 && colDiff >=-1){
            justCastled=false;
            blackKingMoved = true;
            return true;
        }
        return false;
    }
    //TODO
    public boolean isValidMoveBlackKingCastles(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        if(blackKingMoved){return false;}
        if(oldRow == 0 && oldCol == 4){
            if(newRow == 0 && newCol == 6 && map.get(0).get(7) == 5.3 && map.get(0).get(5) == 0){
                blackKingMoved = true;
                justCastled=true;
                return true;
            }else if(newRow == 0 && newCol == 2 && map.get(0).get(0) == 5.3 && map.get(0).get(2) == 0 && map.get(0).get(1) == 0){
                blackKingMoved = true;
                justCastled=true;
                return true;
            }
        }
        return false;
    }
    //TODO
    public boolean isValidMoveWhiteKingCastles(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        if(whiteKingMoved){return false;}
        if(oldRow == 7 && oldCol == 4){
            if(newRow == 7 && newCol == 6 && map.get(7).get(7) == 5.3 && map.get(7).get(5) == 0){
                whiteKingMoved = true;
                justCastled=true;
                return true;
            }else if(newRow == 7 && newCol == 2 && map.get(7).get(0) == 5.3 && map.get(7).get(2) == 0 && map.get(7).get(1) == 0){
                justCastled=true;
                whiteKingMoved = true;
                return true;
            }
        }
        return false;
    }
    public boolean isValidMoveWhiteKing(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        int rowDiff = newRow - oldRow;
        int colDiff = newCol - oldCol;
        if(colDiff <= 1 && rowDiff <=1 && rowDiff>=-1 && colDiff >=-1){
            justCastled=false;
            whiteKingMoved = true;
            return true;
        }
        return false;
    }
    public ArrayList<ArrayList<Boolean>> getLegalMoves(int oldRow, int oldCol, ArrayList<ArrayList<Double>> map){
        ArrayList<ArrayList<Boolean>> resultMap = new ArrayList<>();
        for(int row = 0; row < 8; row++){
            ArrayList<Boolean> temp = new ArrayList<>();
            for(int col = 0; col < 8; col++){
                temp.add(isValidMove(oldRow, oldCol, row, col, map));
            }
            resultMap.add(temp);
        }
        return resultMap;
    }

    private boolean putsBlackInCheck(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        ArrayList<ArrayList<Double>> ifMap = copyMap(map);
        ifMap.get(newRow).set(newCol, ifMap.get(oldRow).get(oldCol));
        ifMap.get(oldRow).set(oldCol, 0.0);
        int kingRow = 404;
        int kingCol = 404;
        //get king position
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(map.get(row).get(col) == -255){
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        //check if any white pieces can capture the king
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(ifMap.get(row).get(col) > 0){
                    if(isValidMoveCheckless(row, col, kingRow, kingCol, ifMap)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean putsWhiteInCheck(int oldRow, int newRow, int oldCol, int newCol, ArrayList<ArrayList<Double>> map){
        ArrayList<ArrayList<Double>> ifMap = copyMap(map);
        ifMap.get(newRow).set(newCol, ifMap.get(oldRow).get(oldCol));
        ifMap.get(oldRow).set(oldCol, 0.0);
        int kingRow = 404;
        int kingCol = 404;
        //get king position
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(map.get(row).get(col) == 255){
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        //check if any black pieces can capture the king
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(ifMap.get(row).get(col) < 0){
                    if(isValidMoveCheckless(row, col, kingRow, kingCol, ifMap)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public ArrayList<Move> generateLegalMoves(boolean playerWhite, ArrayList<ArrayList<Double>> map){
        ArrayList<Move> legalMoves = new ArrayList<>();
        for(int oldRow = 0; oldRow < 8; oldRow++){
            for(int oldCol = 0; oldCol < 8; oldCol++){
                if((map.get(oldRow).get(oldCol) < 0 && playerWhite) || (map.get(oldRow).get(oldCol) > 0 && !playerWhite)){
                    for(int row = 0; row < 8; row++){
                        for(int col = 0; col < 8; col++){
                            if(isValidMove(oldRow, oldCol, row, col, map)){
                                legalMoves.add(new Move(oldRow, oldCol, row, col));
                            }

                        }
                    }
                }

            }
        }
        Collections.sort(legalMoves, new MoveComparator(map));
        return legalMoves;
    }
    public boolean isJustCastled(){
        return justCastled;
    }

}
