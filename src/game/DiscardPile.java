package game;

import java.util.List;
import java.util.Optional;

public class DiscardPile extends DeckRow {
	
	public DiscardPile(Optional<Integer> numberOfVisibleCards) {
		super(numberOfVisibleCards);
	}

	@Override
	public String getActionName() {
		return "Rest";
	}

	@Override
	public void doAction(Player player) {
		List<Card> hand = player.getHand().getDeck();
		hand.addAll(deck);
		deck.clear();
	}

	@Override
	public boolean valid(Player player) {
		if (deck.isEmpty())
		{
			return false;
		}
		return true;
	}

}
