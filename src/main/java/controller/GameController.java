package controller;

import action.ActionRequest;
import action.GameOutputHandler;
import game.Game;
import view.GameInputHandler;

public class GameController {

	private Game game;
	private GameInputHandler inputHandler;
	private GameOutputHandler outputHandler;
	private ActionRequest request;
	
	public GameController(Game game, GameInputHandler inputHandler, GameOutputHandler outputHandler, ActionRequest request) {
		
		this.game = game;
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.request = request;
	}

	public void start() {
		
		outputHandler.welcome();
		inputHandler.getNumberOfPlayers();
		play();
	}

	public void resume() {
		play();
	}
	
	public void play() {
		while (!request.isExitRequested())
		{
			request = inputHandler.getInput();
			game.action(request);
			outputHandler.displayOutput();
		}
		
		stop();
	}
	
	public void stop()
	{
		GameManager.resume();
	}
}
