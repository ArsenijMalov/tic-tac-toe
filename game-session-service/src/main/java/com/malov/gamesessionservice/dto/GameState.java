package com.malov.gamesessionservice.dto;

import com.malov.gamesessionservice.enumeration.GameStatus;

import java.util.*;

public class GameState {
    private String[][] board = new String[3][3];
    private GameStatus status = GameStatus.IN_PROGRESS;
    private String nextPlayer = "X";
    private Random random = new Random();
    private String result = "";
    TreeSet<Integer> treeadd = new TreeSet<Integer>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));


    public MoveRequest makeMove() {
        int randomInt = random.nextInt(9);
        randomInt = treeadd.floor(randomInt) != null ? treeadd.floor(randomInt) : treeadd.ceiling(randomInt);
        treeadd.remove(randomInt);
        int row = randomInt / 3;
        int col = randomInt % 3;
        MoveRequest move = new MoveRequest(row, col, nextPlayer);

        nextPlayer = "X".equals(nextPlayer) ? "O" : "X";
        return move;
    }


    public boolean isGameOver() {
        return !GameStatus.IN_PROGRESS.equals(status);
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.getStatusName();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
