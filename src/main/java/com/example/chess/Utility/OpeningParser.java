package com.example.chess.Utility;

import com.example.chess.Position;
import com.example.chess.model.Move;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.chess.Constants.*;
import static com.example.chess.MoveGenerator.findPieceToMove;

public class OpeningParser {

    public List<String> parseOpenings(List<String> filenames){
        List<String> openingStrings = new ArrayList<>();
        for(String filename: filenames){
            parseFile(filename + ".pgn", openingStrings);
        }
        return openingStrings;
    }

    public void parseFile(String filename, List<String> openingStrings){
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource == null) {
            System.err.println("Resource not found: " + filename);
            return;
        }

        String filePath;
        try {
            filePath = URLDecoder.decode(resource.getPath(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                parsePGNLine(line, openingStrings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parsePGNLine(String line, List<String> openingStrings){
        if(line.startsWith("1.")){
            openingStrings.add(line);
        }
    }

    public static String getMoveString(int oldCol, int newRow, int newCol, Double pieceVal, boolean isCapture, Position position){
        String move = "";

        if(pieceVal == WHITE_KING || pieceVal == BLACK_KING){
            if(newCol - oldCol == -2){
                return "O-O-O";
            } else if(newCol - oldCol == 2){
                return "O-O";
            }
        }

        String pieceString = getPieceString(pieceVal);
        if (!pieceString.equals("")) {
            move += pieceString;
        }

        if (isCapture) {
            if (pieceString.equals("")) {
                move += (char) ('a' + oldCol);
            }
            move += "x";
        }

        move += (char) ('a' + newCol);
        move += (8 - newRow);

        return move;
    }

    private static String getPieceString(Double pieceVal){
        if(pieceVal == WHITE_KING || pieceVal == BLACK_KING){
            return "K";
        } else if (pieceVal == WHITE_QUEEN || pieceVal == BLACK_QUEEN){
            return "Q";
        } else if (pieceVal == WHITE_ROOK || pieceVal == BLACK_ROOK){
            return "R";
        } else if (pieceVal == WHITE_BISHOP || pieceVal == BLACK_BISHOP){
            return "B";
        } else if (pieceVal == WHITE_KNIGHT || pieceVal == BLACK_KNIGHT){
            return "N";
        } else if (pieceVal == WHITE_PAWN || pieceVal == BLACK_PAWN){
            return "";
        } else {
            return "";
        }
    }

    private static Double getPieceFromString(String pieceString) {
        switch (pieceString) {
            case "K":
                return BLACK_KING;
            case "Q":
                return BLACK_QUEEN;
            case "R":
                return BLACK_ROOK;
            case "B":
                return BLACK_BISHOP;
            case "N":
                return BLACK_KNIGHT;
            default:
                return BLACK_PAWN;
        }
    }

    public static Move getMoveFromString(String moveString, Position position){
        String[] chars = moveString.split("");
        Double pieceVal;
        int newCol;
        int newRow;
        Integer oldCol = null;
        if(chars.length == 3){
            pieceVal = getPieceFromString(chars[0]);
            if(pieceVal == BLACK_PAWN){
                oldCol = chars[0].charAt(0) - 'a';
            }
            newRow = 8 - Integer.parseInt(chars[2]);
            newCol = chars[1].charAt(0) - 'a';
        } else if(chars.length == 4) {
            pieceVal = getPieceFromString(chars[0]);
            newRow = 8 - Integer.parseInt(chars[3]);
            oldCol = chars[1].charAt(0) - 'a';
            newCol = chars[2].charAt(0) - 'a';
        } else {
            pieceVal = BLACK_PAWN;
            newRow = 8 - Integer.parseInt(chars[1]);
            newCol = chars[0].charAt(0) - 'a';
        }
        return findPieceToMove(newRow, newCol, pieceVal, position, oldCol);
    }

}
