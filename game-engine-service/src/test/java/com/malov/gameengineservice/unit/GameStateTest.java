package com.malov.gameengineservice.unit;

import com.malov.gameengineservice.dto.GameState;
import com.malov.gameengineservice.enumeration.GameStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameStateTest {


    @Test
    public void makeMove_checkGameStatusWhenElementsInOneColumnTheSame() {

        GameState game = new GameState();

        game.makeMove(0, 1, "X");
        game.makeMove(1, 1, "X");
        game.makeMove(2, 1, "X");

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(GameState.PLAYER_WON_STATUS.formatted("X"), game.getResult());

    }

    @Test
    public void makeMove_checkGameStatusWhenElementsInOneRowTheSame() {

        GameState game = new GameState();

        game.makeMove(1, 0, "X");
        game.makeMove(1, 1, "X");
        game.makeMove(1, 2, "X");

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(GameState.PLAYER_WON_STATUS.formatted("X"), game.getResult());

    }

    @Test
    public void makeMove_checkGameStatusWhenElementsInDiagonalTheSame1() {

        GameState game = new GameState();

        game.makeMove(0, 0, "X");
        game.makeMove(1, 1, "X");
        game.makeMove(2, 2, "X");

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(GameState.PLAYER_WON_STATUS.formatted("X"), game.getResult());

    }

    @Test
    public void makeMove_checkGameStatusWhenElementsInDiagonalTheSame2() {

        GameState game = new GameState();

        game.makeMove(2, 0, "X");
        game.makeMove(1, 1, "X");
        game.makeMove(0, 2, "X");

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(GameState.PLAYER_WON_STATUS.formatted("X"), game.getResult());

    }

    @Test
    public void makeMove_checkGameStatusInProgress1() {

        GameState game = new GameState();

        game.makeMove(2, 0, "X");
        game.makeMove(1, 1, "O");
        game.makeMove(0, 2, "X");

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(GameState.GAME_IN_PROGRESS, game.getResult());

    }

    @Test
    public void makeMove_checkGameStatusDraw() {

        GameState game = new GameState();

        game.makeMove(2, 0, "X");
        game.makeMove(2, 1, "O");
        game.makeMove(2, 2, "X");
        game.makeMove(1, 1, "O");
        game.makeMove(0, 1, "X");
        game.makeMove(0, 2, "O");
        game.makeMove(1, 2, "X");
        game.makeMove(0, 0, "O");

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(GameState.DRAW_STATUS, game.getResult());

    }

}
