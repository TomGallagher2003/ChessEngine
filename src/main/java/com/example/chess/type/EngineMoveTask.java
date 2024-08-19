package com.example.chess.type;

import com.example.chess.BoardManager;
import javafx.concurrent.Task;

public class EngineMoveTask extends Task<Void> {
    private final BoardManager boardManager;

    public EngineMoveTask(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    @Override
    protected Void call() throws Exception {
        boardManager.makeEngineMove(); // Compute engine's move
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

