package controller;

import java.util.UUID;

import action.ActionRequest;
import action.Output;
import game.Game;
import view.CLI;
import view.GameView;

public class GameSession {

	private Game game;
	private GameController controller;
	private GameView view;
	private Output output;
	private ActionRequest request;
	
	public final String uuid = UUID.randomUUID().toString();

	public GameSession() {
		this.game = new Game();
		this.view = new CLI();
		this.output = new Output();
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
