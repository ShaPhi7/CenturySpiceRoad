package view;

import action.ActionRequest;
import action.Output;

public abstract class GameView {
	public abstract ActionRequest getInput();
	public abstract void displayOutput(Output output);
}
