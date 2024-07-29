package action;

public class ActionRequest {

	String uuid = "";
	private boolean exitRequested = false; 
	private String action = "";
	private int selectedNumberOfPlayers = 0;
	
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
	public int getSelectedNumberOfPlayers() {
		return selectedNumberOfPlayers;
	}
	public void setSelectedNumberOfPlayers(int selectedNumberOfPlayers) {
		this.selectedNumberOfPlayers = selectedNumberOfPlayers;
	}	
}
