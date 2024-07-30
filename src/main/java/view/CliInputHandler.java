package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import action.Action;
import board.DeckRow;
import card.Card;
import card.PointCard;
import card.TradeCard;
import card.UpgradeCard;
import game.Spice;
import game.SpiceUpgrade;

public class CliInputHandler extends GameInputHandler {
	
	@Override
	public String getInput() {

		CLI.print(">> ");
		String nextLine = CLI.getInput();
		
		if (nextLine.contains("exit")) {
			CLI.print("Exiting program.");
			setExitRequested(true);
		}

		return nextLine;
	}

	@Override
	public void setSelectedNumberOfPlayers() {
		CLI.print("It is a 2-5 player game.");
		CLI.print("How many players would you like to play with?");
		
		setAction(Action.SETUP);
		setSelectedNumberOfPlayers(Integer.valueOf(getInput()));
	}

	public void setAction() {
		CLI.print("It is the next players turn.");
		CLI.print("What would you like to do?");
		while (getAction().isEmpty()) {
			CLI.print("You can enter play, rest, acquire, claim or exit");
			String input = CLI.getInput();
			Optional<Action> action = Action.fromString(input);
			setAction(action);
		}
	}

	public void setSelectedCard(DeckRow deckrow) {
		CLI.print("Which card?");
		for (int i=0; i<deckrow.getVisibleCards().size(); i++)
		{
			CLI.print("[ " + i + "] " + deckrow.getVisibleCards().get(i));
		}
		String input = CLI.getInput();
		Card card = deckrow.getVisibleCards().get(Integer.valueOf(input));
		setSelectedCard(Optional.of(card));
	}
	
	public void setSelectedNumberOfTrades() {
		if (Action.PLAY.equals(getAction().orElse(null))
		  && getSelectedCard().orElse(new PointCard()) instanceof TradeCard)
		{
			CLI.print("How many times?");
			String input = CLI.getInput();
			setSelectedNumberOfTrades(Integer.valueOf(input));
		}
	}
	
	public void setSelectedUpgrades() {
		if (Action.PLAY.equals(getAction().orElse(null))
		  && getSelectedCard().orElse(new PointCard()) instanceof UpgradeCard)
		{
			List<SpiceUpgrade> spiceUpgrades = new ArrayList<>();
			
			String input = "yes";
			
			while (!input.equals("no"))
			{
				SpiceUpgrade spiceUpgrade = getSpiceUpgrade();
				spiceUpgrades.add(spiceUpgrade);
				
				CLI.print("Any more upgrades? yes/no");
				input = CLI.getInput();
			}
			
			setSelectedUpgrades(spiceUpgrades);
		}
	}

	private SpiceUpgrade getSpiceUpgrade() {
		CLI.print("Which cube?");
		String input = CLI.getInput();
		Optional<Spice> spice = Spice.fromString(input);
		
		CLI.print("How many times?");
		String numberOfTimes = CLI.getInput();
		
		SpiceUpgrade spiceUpgrade = new SpiceUpgrade(spice.orElse(Spice.YELLOW_TUMERIC), Integer.valueOf(numberOfTimes));
		return spiceUpgrade;
	}
}
