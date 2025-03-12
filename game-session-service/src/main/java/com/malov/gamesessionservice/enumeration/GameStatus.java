package com.malov.gamesessionservice.enumeration;

public enum GameStatus {
    IN_PROGRESS ("In Progress"),
    GAME_OVER ("Game Over");

    private String statusName;

    GameStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
