package com.malov.gameengineservice.dto;

public record MoveRequest(
        int row,
        int col,
        String player
) {
}
