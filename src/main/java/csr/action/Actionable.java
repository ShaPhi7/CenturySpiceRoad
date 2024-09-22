package csr.action;

import csr.view.GameInputHandler;

public interface Actionable {
	public Action getAction();
	public void doAction(GameInputHandler input, GameOutputHandler output);
	public boolean validateAction(GameInputHandler input, GameOutputHandler output);
}