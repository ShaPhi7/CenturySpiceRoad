package view;

import action.ActionRequest;
import action.GameOutputHandler;

public abstract class GameInputHandler {
	public abstract ActionRequest getInput();
	public abstract ActionRequest getNumberOfPlayers();
}
