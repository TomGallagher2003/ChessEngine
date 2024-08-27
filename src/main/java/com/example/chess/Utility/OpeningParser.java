package com.example.chess.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpeningParser {


    public List<String> parseFile(String filename){
        List<String> openingStrings = new ArrayList<>();
        String filePath = getClass().getClassLoader().getResource(filename).getPath();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                parsePGNLine(line, openingStrings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openingStrings;
    }

    public void parsePGNLine(String line, List<String> openingStrings){
        if(line.startsWith("1.")){
            openingStrings.add(line);
        }
    }
}
