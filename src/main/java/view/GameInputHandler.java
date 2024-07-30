package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import action.ActionRequest;
import card.Card;
import card.UpgradeCard;
import game.Player;
import game.SpiceUpgrade;

public abstract class GameInputHandler {
	
	String uuid = "";
	private boolean exitRequested = false; 
	private String action = "";
	private Player player = null;
	private int selectedNumberOfPlayers = 0;
	private Optional<Card> selectedCard = Optional.empty(); //TODO - probably want a PlayerTurn object
	private int selectedNumberOfTrades = 1; 
	private List<SpiceUpgrade> selectedUpgrades = new ArrayList<>();
	
	public boolean isExitRequested() {
		return exitRequested;
	}
	
	public void setExitRequested(boolean exitRequested) {
		this.exitRequested = exitRequested;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getSelectedNumberOfPlayers() {
		return selectedNumberOfPlayers;
	}
	
	public void setSelectedNumberOfPlayers(int selectedNumberOfPlayers) {
		this.selectedNumberOfPlayers = selectedNumberOfPlayers;
	}	
	
	public Optional<Card> getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(Optional<Card> selectedCard) {
		this.selectedCard = selectedCard;
	}

	public int getSelectedNumberOfTrades() {
		return selectedNumberOfTrades;
	}

	public void setSelectedNumberOfTrades(int selectedNumberOfTrades) {
		this.selectedNumberOfTrades = selectedNumberOfTrades;
	}

	public List<SpiceUpgrade> getSelectedUpgrades() {
		return selectedUpgrades;
	}

	public void setSelectedUpgrades(List<SpiceUpgrade> selectedUpgrades) {
		this.selectedUpgrades = selectedUpgrades;
	}
	
	public abstract ActionRequest getInput();
	public abstract ActionRequest getNumberOfPlayers();
	
	/*
	 * If an Upgrade card is not selected, then we would not expect to have any selected upgrades to fail this check.
	 * I do not see a need to explicitly check that it is empty though.
	 */
	public boolean selectedUpgradesAreSensible() {
		 return getSelectedUpgrades().stream().noneMatch(spiceUpgrade ->
	        spiceUpgrade.getNumberOfTimesToUpgrade() > SpiceUpgrade.getMaxUpgrades().getOrDefault(spiceUpgrade.getCubeToBeUpgraded(), 0));
	}

	public boolean selectedMoreUpgradesThanPermitted(Card card) {
		if (!(card instanceof UpgradeCard upgradeCard))
		{
			//only applicable to upgrade cards
			return true;
		}
		
		return SpiceUpgrade.getTotalUpgrades(getSelectedUpgrades()) <= upgradeCard.getPermittedUpgrades();
	}
}
