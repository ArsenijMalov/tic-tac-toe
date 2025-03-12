package com.malov.gameengineservice.controller;

import com.malov.gameengineservice.dto.GameState;
import com.malov.gameengineservice.dto.MoveRequest;
import com.malov.gameengineservice.enumeration.GameStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/games")
public class GameEngineController {

    private final Map<Long, GameState> games = new ConcurrentHashMap<>();

    @PostMapping("/{gameId}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable Long gameId, @RequestBody MoveRequest move) {
        GameState game = games.computeIfAbsent(gameId, k -> new GameState());

        if (!game.isValidMove(move.row(), move.col())) {
            game.setStatus(GameStatus.GAME_OVER);
            return ResponseEntity.ok(game);
        }

        game.makeMove(move.row(), move.col(), move.player());
        return ResponseEntity.ok(game);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameState> getGameState(@PathVariable Long gameId) {
        return ResponseEntity.ok(games.getOrDefault(gameId, new GameState()));
    }
}

