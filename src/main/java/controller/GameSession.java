package controller;

import java.util.UUID;

import action.ActionRequest;
import action.CliOutputHandler;
import action.GameOutputHandler;
import game.Game;
import view.CliInputHandler;
import view.GameInputHandler;

public class GameSession {

	private Game game;
	private GameController controller;
	private GameInputHandler view;
	private GameOutputHandler output;
	private ActionRequest request;
	
	public final String uuid = UUID.randomUUID().toString();

	public GameSession() {
		this.game = new Game();
		this.view = new CliInputHandler();
		this.output = new CliOutputHandler();
		this.request = new ActionRequest();
		this.controller = new GameController(game, view, output, request);
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
