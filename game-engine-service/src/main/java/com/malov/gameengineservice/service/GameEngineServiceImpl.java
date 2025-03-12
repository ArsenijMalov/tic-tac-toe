package com.malov.gameengineservice.service;

import com.malov.gameengineservice.dto.GameState;
import com.malov.gameengineservice.dto.MoveRequest;
import com.malov.gameengineservice.enumeration.GameStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameEngineServiceImpl implements GameEngineService {

    private final Map<Long, GameState> games = new ConcurrentHashMap<>();

    @Override
    public GameState makeMove(Long gameId, MoveRequest move) {
        GameState game = games.computeIfAbsent(gameId, k -> new GameState());
        if (!game.isValidMove(move.row(), move.col())) {
            game.setStatus(GameStatus.GAME_OVER);
        } else {
            game.makeMove(move.row(), move.col(), move.player());
        }
        return game;
    }

    @Override
    public GameState getGameState(Long gameId) {
        return games.getOrDefault(gameId, new GameState());
    }
}
