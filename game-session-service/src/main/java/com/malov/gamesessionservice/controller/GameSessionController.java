package com.malov.gamesessionservice.controller;

import com.malov.gamesessionservice.dto.GameState;
import com.malov.gamesessionservice.dto.MoveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/sessions")
public class GameSessionController {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final AtomicLong sessionCounter = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<Long> createSession() {
        Long sessionId = sessionCounter.getAndIncrement();
        emitters.put(sessionId, new SseEmitter());
        return ResponseEntity.ok(sessionId);
    }

    @PostMapping("/{sessionId}/simulate")
    public void simulateGame(@PathVariable Long sessionId) {

        GameState game = new GameState();
        while (!game.isGameOver()) {


            ResponseEntity<GameState> gameResponse = restTemplate.postForEntity("http://localhost:8081/games/" + sessionId + "/move",
                    game.makeMove(), GameState.class);
            GameState newState = gameResponse.getBody();
            game.setBoard(newState.getBoard());
            game.setStatus(newState.getStatus());
            game.setResult(newState.getResult());
            sendGameUpdate(sessionId, game);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SseEmitter> getUpdates(@PathVariable Long sessionId) {
        return ResponseEntity.ok(emitters.get(sessionId));
    }

    private void sendGameUpdate(Long sessionId, GameState game) {
        SseEmitter emitter = emitters.get(sessionId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(game));
                if (game.isGameOver()) {
                    emitters.remove(sessionId);
                }
            } catch (IOException e) {
                emitters.remove(sessionId);
            }
        }
    }
}
