package com.example.chess.type;

import com.example.chess.BoardManager;
import javafx.concurrent.Task;

public class EngineMoveTask extends Task<Void> {
    private final BoardManager boardManager;
    private final int maxRetries = 2;
    private int attempt = 0;

    public EngineMoveTask(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    protected Void call() throws Exception {
        try {
            boardManager.makeEngineMove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        boardManager.setPlayerTurn(true); // Allow the player to move again
    }

    @Override
    protected void failed() {
        super.failed();
        boardManager.setPlayerTurn(true); // Allow the player to move again in case of failure
    }
}

