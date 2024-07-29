package action;

import java.util.ArrayList;
import java.util.List;

public abstract class GameOutputHandler {

	public abstract void displayOutput();
	public abstract void welcome();
	
	protected List<String> gameUpdates = new ArrayList<>();

	public List<String> getGameUpdates() {
		return gameUpdates;
	}

	public void setGameUpdates(List<String> gameUpdates) {
		this.gameUpdates = gameUpdates;
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
