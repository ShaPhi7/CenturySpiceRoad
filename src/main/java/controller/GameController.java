package controller;

import action.ActionRequest;
import action.Output;
import game.Game;
import view.GameView;

public class GameController {

	private Game game;
	private GameView view;
	
	Output output = new Output();
	ActionRequest request = new ActionRequest();
	
	public GameController(Game game, GameView view) {
		
		this.game = game;
		this.view = view;
	}

	public void start() {
		
		welcome();
		play();
	}

	private void welcome() {
		output.addUpdate("Welcome to your game of Century: Spice Road!");
		output.addUpdate("It is a 2-5 player game.");
		output.addUpdate("How many players would you like to play with?");
		view.displayOutput(output);
	}

	public void resume() {
		play();
	}
	
	public void play() {
		while (!request.exitRequested)
		{
			request = view.getInput();
			game.action(request);
			view.displayOutput(output);
		}
	}
	
	public void stop() {
		//TODO - exit, or game ended
	}
}
