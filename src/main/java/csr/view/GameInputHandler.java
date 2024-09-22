package csr.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import csr.action.Action;
import csr.card.Card;
import csr.card.UpgradeCard;
import csr.game.Player;
import csr.game.SpiceUpgrade;

public abstract class GameInputHandler {
	
	String uuid = "";
	private boolean exitRequested = false; 
	private Optional<Action> action = Optional.empty();
	private Player player;
	private int selectedNumberOfPlayers = 0;
	private Optional<Card> selectedCard = Optional.empty();
	private int selectedNumberOfTrades = 1; 
	private List<SpiceUpgrade> selectedUpgrades = new ArrayList<>();
	
	public boolean isExitRequested() {
		return exitRequested;
	}
	
	public void setExitRequested(boolean exitRequested) {
		this.exitRequested = exitRequested;
	}
	
	public Optional<Action> getAction() {
		return action;
	}
	
	public void setAction(Optional<Action> action) {
		this.action = action;
	}
	
	public void setAction(Action action) {
		this.action = Optional.of(action);
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
	
	public abstract String getInput();
	public abstract void setSelectedNumberOfPlayers();
	
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
