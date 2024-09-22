package csr.action;

import java.util.ArrayList;
import java.util.List;

import csr.game.Game;

public abstract class GameOutputHandler {

	public abstract void displayOutput();
	public abstract void welcome();
	public abstract void updateAllVisibleComponents(Game game);
	
	protected List<String> gameUpdates = new ArrayList<>();

	public List<String> getGameUpdates() {
		return gameUpdates;
	}

	public void setGameUpdates(List<String> gameUpdates) {
		this.gameUpdates = gameUpdates;
	}
	
	public <T> void addUpdate(List<T> gameUpdates)
	{
		for (T update : gameUpdates)
		{
			getGameUpdates().add(update.toString());
		}
	}
	
	public void addUpdate(String gameUpdate)
	{
		getGameUpdates().add(gameUpdate);
	}
	
	public void clear()
	{
		getGameUpdates().clear();
	}
}
