package game;

import java.util.Optional;

public class Hand extends DeckRow {

	public Hand(Optional<Integer> numberOfVisibleCards) {
		super(numberOfVisibleCards);
	}

	@Override
	public String getActionName() {
		return "Play";
	}

	@Override
	public void doAction(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validateAction(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}
