package csr.cli;

import java.util.UUID;

import org.springframework.stereotype.Component;

import csr.game.Game;

@Component(value="GameSession")
public class GameSession {

	private final Game game;
	private final CliController controller;
	
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
