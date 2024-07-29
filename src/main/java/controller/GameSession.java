package controller;

import java.util.UUID;

import game.Game;
import view.CLI;
import view.GameView;

public class GameSession {

	private Game game;
	private GameController controller;
	private GameView view;
	
	public final String uuid = UUID.randomUUID().toString();

	public GameSession() {
		this.game = new Game();
		this.view = new CLI(); //TODO - gameManager should control what this is
		this.controller = new GameController(game, view);
	}

	public void start() {
		new Thread(() -> controller.start()).start();
	}
	
	public String getUuid()
	{
		return uuid;
	}

	public void resume() {
		new Thread(() -> controller.resume()).start();
	}
}
