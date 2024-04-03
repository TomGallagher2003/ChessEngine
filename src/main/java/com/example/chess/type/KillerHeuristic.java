package com.example.chess.type;

import java.util.ArrayList;

public class KillerHeuristic {
    private static final int MAX_DEPTH = 100; // Maximum depth of the search tree
    private static ArrayList<Move>[] killerMoves = new ArrayList[MAX_DEPTH];

    // Initialize killer moves array
    static {
        for (int i = 0; i < MAX_DEPTH; i++) {
            killerMoves[i] = new ArrayList<>();
        }
    }

    // Update killer moves for the current depth
    public static void updateKillerMove(int depth, Move move) {
        if (!killerMoves[depth].contains(move)) {
            killerMoves[depth].add(move);
            if (killerMoves[depth].size() > 2) {
                killerMoves[depth].remove(0); // Keep only the last two killer moves
            }
        }
    }

    // Get killer moves for the current depth
    public static ArrayList<Move> getKillerMoves(int depth) {
        return killerMoves[depth];
    }
}