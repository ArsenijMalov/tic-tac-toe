package com.malov.apigateway.controller;

import com.malov.apigateway.dto.GameState;
import com.malov.apigateway.enumeration.GameStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sessions")
public class GameSessionController {

    private static final String GAME_SESSION_BASE_URL = "http://game-session-service/api/v1";
    private final RestTemplate restTemplate;
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public GameSessionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Long> createSession() {
        ResponseEntity<String> response = restTemplate.postForEntity("%s/sessions".formatted(GAME_SESSION_BASE_URL),
                null, String.class);
        Long sessionId = Long.valueOf(response.getBody());
        emitters.put(sessionId, new SseEmitter());
        return ResponseEntity.ok(sessionId);
    }

    @PostMapping("/{sessionId}/simulate")
    public void simulateGame(@PathVariable Long sessionId) {
        Boolean inProgress = true;
        while (inProgress) {
            GameState game = restTemplate
                    .postForEntity("%s/sessions/%s/simulate".formatted(GAME_SESSION_BASE_URL, sessionId),
                            null, GameState.class).getBody();
            inProgress = GameStatus.IN_PROGRESS.equals(game.getStatus());
            sendGameUpdate(sessionId, game);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
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
                if (!GameStatus.IN_PROGRESS.equals(game.getStatus())) {
                    emitters.remove(sessionId);
                }
            } catch (IOException e) {
                emitters.remove(sessionId);
            }
        }
    }
}
