package com.malov.gameengineservice.service;

import com.malov.gameengineservice.dto.GameState;
import com.malov.gameengineservice.dto.MoveRequest;

public interface GameEngineService {

    GameState makeMove(Long gameId, MoveRequest move);

    GameState getGameState(Long gameId);

}
