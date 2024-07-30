package action;

import game.Game;
import view.CLI;

public class CliOutputHandler extends GameOutputHandler {

    @Override
    public void displayOutput()
    {
    	for (String update : getGameUpdates())
    	{
    		CLI.print(update);
    	}
    	getGameUpdates().clear();
    }
    
	@Override
	public void welcome() {
		addUpdate("Welcome to your game of Century: Spice Road!");
		displayOutput();
	}	
	
	@Override
	public void updateAllVisibleComponents(Game game) {
		addUpdate("Merchant cards:");
		addUpdate(game.getMerchantCardDeckRow().getVisibleCards());
		addUpdate("Point cards:");
		addUpdate(game.getPointCardDeckRow().getVisibleCards());
		addUpdate("Your cards: ");
		addUpdate(game.getCurrentPlayer().getHand().getDeck());
	}
}
