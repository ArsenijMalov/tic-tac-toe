package com.malov.gameengineservice.controller;

import com.malov.gameengineservice.dto.GameState;
import com.malov.gameengineservice.dto.MoveRequest;
import com.malov.gameengineservice.service.GameEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameEngineController {

    private final GameEngineService gameEngineService;

    public GameEngineController(GameEngineService gameEngineService) {
        this.gameEngineService = gameEngineService;
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable Long gameId, @RequestBody MoveRequest move) {
        return ResponseEntity.ok(gameEngineService.makeMove(gameId, move));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameState> getGameState(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameEngineService.getGameState(gameId));
    }
}

