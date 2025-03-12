package com.malov.gamesessionservice.controller;

import com.malov.gamesessionservice.dto.GameState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/sessions")
public class GameSessionController {
    private static final String GAME_ENGINE_BASE_URL = "http://game-engine-service/api/v1";
    private final Map<Long, GameState> sessions = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private final AtomicLong sessionCounter = new AtomicLong(1);

    public GameSessionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Long> createSession() {
        Long sessionId = sessionCounter.getAndIncrement();
        return ResponseEntity.ok(sessionId);
    }

    @PostMapping("/{sessionId}/simulate")
    public ResponseEntity<GameState> simulateGame(@PathVariable Long sessionId) {
        GameState game = sessions.containsKey(sessionId) ? sessions.get(sessionId) : new GameState();
        ResponseEntity<GameState> gameResponse = restTemplate.postForEntity("%s/games/%s/move".formatted(GAME_ENGINE_BASE_URL, sessionId),
                game.makeMove(), GameState.class);
        updateGameState(game, gameResponse.getBody(), sessionId);
        return gameResponse;
    }

    private void updateGameState(GameState existingGame, GameState newState, Long sessionId) {
        existingGame.setBoard(newState.getBoard());
        existingGame.setStatus(newState.getStatus());
        existingGame.setResult(newState.getResult());
        sessions.put(sessionId, existingGame);
    }

}
