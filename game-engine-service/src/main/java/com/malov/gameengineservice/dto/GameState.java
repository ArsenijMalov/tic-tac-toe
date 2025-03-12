package com.malov.gameengineservice.dto;

import com.malov.gameengineservice.enumeration.GameStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameState {
    private String[][] board = new String[3][3];
    private GameStatus status = GameStatus.IN_PROGRESS;
    private String result = "Game in progress";
    public final static String PLAYER_WON_STATUS = "The Player %s won!";
    public final static String DRAW_STATUS = "Draw!";
    public final static String GAME_IN_PROGRESS = "Game in progress";

    public boolean isValidMove(int row, int col) {
        return board[row][col] == null;
    }

    public void makeMove(int row, int col, String player) {
        board[row][col] = player;
        checkGameStatus();
    }

    public String getResult() {
        return result;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    private void checkGameStatus() {
        Set<String> nonWinningCombinations = new HashSet<>();
        for (int row = 0; row < 3; row++) {
            Map<String, Integer> playersByRow = new HashMap<>();
            Map<String, Integer> playersByRightDiagonal = new HashMap<>();
            Map<String, Integer> playersByLeftDiagonal = new HashMap<>();
            for (int col = 0; col < 3; col++) {
                if (row == 0) {
                    Map<String, Integer> playersByCol = new HashMap<>();
                    for (int row_ = 0; row_ < 3; row_++) {
                        checkThatGameIsOver(row_, col, playersByCol);
                        addIfNonWinningCombinationIfItIS(playersByCol,
                                nonWinningCombinations, "Column %d".formatted(col));
                        if (GameStatus.GAME_OVER.equals(status)) {
                            return;
                        }
                    }
                }
                checkThatGameIsOver(row, col, playersByRow);
                addIfNonWinningCombinationIfItIS(playersByRow,
                        nonWinningCombinations, "Row %d".formatted(row));
                if (row == 0) {
                    checkThatGameIsOver(col, col, playersByLeftDiagonal);
                    addIfNonWinningCombinationIfItIS(playersByLeftDiagonal,
                            nonWinningCombinations, "Left diagonal");
                    checkThatGameIsOver(board.length - col - 1, col, playersByRightDiagonal);
                    addIfNonWinningCombinationIfItIS(playersByRightDiagonal,
                            nonWinningCombinations, "Right diagonal");

                }
                if (GameStatus.GAME_OVER.equals(status)) {
                    return;
                }
                if (nonWinningCombinations.size() == 8) {
                    status = GameStatus.GAME_OVER;
                    result = DRAW_STATUS;
                    return;
                }
            }
        }
    }

    private void checkThatGameIsOver(int row, int col, Map<String, Integer> counterPlayersInCheckedItem) {
        if (board[row][col] != null) {
            putOrUpdate(counterPlayersInCheckedItem, board[row][col]);
        }
        if (counterPlayersInCheckedItem.size() == 1 && counterPlayersInCheckedItem.values().stream().findFirst().get() == board.length) {
            gameOver(counterPlayersInCheckedItem.keySet().stream().findFirst().get());
        }
    }

    private void addIfNonWinningCombinationIfItIS(Map<String, Integer> map, Set<String> numberOfExactlyNonWinningCombinations,
                                                  String combinationName) {
        if (map.size() > 1) numberOfExactlyNonWinningCombinations.add(combinationName);
    }

    private void gameOver(String winner) {
        result = winner == null ? DRAW_STATUS : PLAYER_WON_STATUS.formatted(winner);
        status = GameStatus.GAME_OVER;
    }

    private void putOrUpdate(Map<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }
}
