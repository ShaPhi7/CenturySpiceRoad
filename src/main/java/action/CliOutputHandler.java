package action;

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
}
