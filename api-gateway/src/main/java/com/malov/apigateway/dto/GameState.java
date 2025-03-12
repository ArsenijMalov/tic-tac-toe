package com.malov.apigateway.dto;

import com.malov.apigateway.enumeration.GameStatus;

import java.util.Arrays;
import java.util.Objects;

public class GameState {

    private String[][] board;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private String nextPlayer;
    private String result;
    private String statusName;

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.deepEquals(board, gameState.board) && Objects.equals(status, gameState.status) && Objects.equals(nextPlayer, gameState.nextPlayer) && Objects.equals(result, gameState.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), status, nextPlayer, result);
    }
}
