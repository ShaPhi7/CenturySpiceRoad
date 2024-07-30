package controller;

import java.util.UUID;

import game.Game;

public class GameSession {

	private Game game;
	private CliController controller;
	
	public final String uuid = UUID.randomUUID().toString();

	public GameSession() {
		this.game = new Game();
		this.controller = new CliController(game);
	}

	public void start() {
		new Thread(() -> controller.start()).start();
	}

	public void resume() {
		new Thread(() -> controller.resume()).start();
	}
	
	public String getUuid()
	{
		return uuid;
	}
}
