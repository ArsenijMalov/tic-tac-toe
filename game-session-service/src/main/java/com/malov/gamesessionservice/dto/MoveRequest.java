package com.malov.gamesessionservice.dto;

public record MoveRequest(
        int row,
        int col,
        String player
) {
}
