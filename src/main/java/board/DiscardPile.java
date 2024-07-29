package board;

import java.util.List;
import java.util.Optional;

import card.Card;
import game.Game;
import game.Player;

public class DiscardPile extends DeckRow {
	
	public DiscardPile(Game game) {
		super(game, Optional.empty());
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
	public boolean validateAction(Player player) {

		if (!basicValidation(player))
		{
			return false;
		}
		
		if (deck.isEmpty())
		{
			System.out.println("Player trying to pick up empty deck");
			return false;
		}
		
		if (!this.equals(player.getDiscard()))
		{
			System.out.println("Player trying to pick up a discard deck that is not their own");
			return false;
		}
		
		return true;
	}

}
