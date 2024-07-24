package game;

import java.util.List;
import java.util.Optional;

public class Hand extends DeckRow {
	
	public Hand() {
		super(Optional.empty());
	}
	
	public Hand(List<Card> cards) {
		super(Optional.empty());
		deck.addAll(cards);
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
