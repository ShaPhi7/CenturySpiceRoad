package csr.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import csr.action.Action;
import csr.board.DeckRow;
import csr.card.Card;
import csr.card.PointCard;
import csr.card.TradeCard;
import csr.card.UpgradeCard;
import csr.game.Spice;
import csr.game.SpiceUpgrade;
import csr.view.GameInputHandler;

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
		setSelectedNumberOfPlayers(Integer.parseInt(getInput()));
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
		for (int i=0; i<deckrow.getVisibleCards().size(); i++)
		{
			CLI.print("[" + i + "] " + deckrow.getVisibleCards().get(i));
		}
		CLI.print("Which card?");
		String input = CLI.getInput();
		Card card = deckrow.getVisibleCards().get(Integer.parseInt(input));
		setSelectedCard(Optional.of(card));
	}
	
	public void setSelectedNumberOfTrades() {
		if (Action.PLAY.equals(getAction().orElse(null))
		  && getSelectedCard().orElse(new PointCard()) instanceof TradeCard)
		{
			CLI.print("How many times?");
			String input = CLI.getInput();
			setSelectedNumberOfTrades(Integer.parseInt(input));
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
		
		SpiceUpgrade spiceUpgrade = new SpiceUpgrade(spice.orElse(Spice.YELLOW_TUMERIC), Integer.parseInt(numberOfTimes));
		return spiceUpgrade;
	}
}
