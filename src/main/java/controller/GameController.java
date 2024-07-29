package controller;

import java.util.HashMap;
import java.util.Map;

import game.Game;

public class GameController {

	private static Map<String, Game> games = new HashMap<>();
	
	public static void main(String[] args) {
		Game game = new Game();
		games.put(game.getUuid(), game);
		game.init();
	}
	
}
