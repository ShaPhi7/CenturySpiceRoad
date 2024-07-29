package controller;

import action.ActionRequest;
import action.Output;
import game.Game;
import view.GameView;

public class GameController {

	private Game game;
	private GameView view;
	private Output output;
	private ActionRequest request;
	
	public GameController(Game game, GameView view, Output output, ActionRequest request) {
		
		this.game = game;
		this.view = view;
		this.output = output;
		this.request = request;
	}

	public void start() {
		
		welcome();
		play();
	}

	public void resume() {
		play();
	}
	
	public void play() {
		while (!request.isExitRequested())
		{
			request = view.getInput();
			game.action(request);
			view.displayOutput(output);
		}
		
		stop();
	}
	
	public void stop()
	{
		GameManager.resume();
	}
	
	private void welcome() {
		output.addUpdate("Welcome to your game of Century: Spice Road!");
		output.addUpdate("It is a 2-5 player game.");
		output.addUpdate("How many players would you like to play with?");
		view.displayOutput(output);
	}
}
