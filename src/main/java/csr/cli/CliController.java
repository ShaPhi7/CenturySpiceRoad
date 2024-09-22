package csr.cli;

import csr.action.Action;
import csr.action.CliOutputHandler;
import csr.game.Game;

public class CliController {

	private final Game game;
	private CliInputHandler input = new CliInputHandler();
	private CliOutputHandler output = new CliOutputHandler();
	
	public CliController(Game game) {
		
		this.game = game;
	}

	public void start() {
		
		output.welcome();
		setup();
		play();
	}

	private void setup() {
		input.setSelectedNumberOfPlayers();
		game.doAction(input, output);
		output.displayOutput();
	}

	public void resume() {
		play();
	}
	
	public void play() {
		while (!input.isExitRequested())
		{
			input = new CliInputHandler();
			output = new CliOutputHandler();
			
			input.setPlayer(game.getCurrentPlayer());
			input.setAction();
			
			switch (input.getAction().orElse(Action.SETUP))
			{
				case ACQUIRE -> {
                                    input.setSelectedCard(game.getMerchantCardDeckRow());
                                    game.getMerchantCardDeckRow().execute(input, output);
                        }
				case CLAIM -> {
                                    input.setSelectedCard(game.getPointCardDeckRow());
                                    game.getPointCardDeckRow().execute(input, output);
                        }
				case DISCARD -> output.addUpdate("Not yet implemented, choose a different action.");
				case PLAY -> {
                                    input.setSelectedCard(game.getCurrentPlayer().getHand());
                                    input.setSelectedNumberOfTrades();
                                    input.setSelectedUpgrades();
                                    input.getPlayer().getHand().execute(input, output);
                        }
				case REST -> input.getPlayer().getDiscard().execute(input, output);
				case SETUP -> game.validateAction(input, output);
				case EXIT -> {
                        }
				default -> output.addUpdate("Unknown action, please try again.");
			}
			output.updateAllVisibleComponents(game);
			output.displayOutput();
		}
		
		stop();
	}
	
	public void stop()
	{
		GameManager.resume();
	}
}
