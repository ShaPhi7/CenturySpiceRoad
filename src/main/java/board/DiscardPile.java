package board;

import java.util.List;
import java.util.Optional;

import action.Action;
import action.GameOutputHandler;
import card.Card;
import game.Game;
import game.Player;
import view.GameInputHandler;

public class DiscardPile extends DeckRow {
	
	public DiscardPile(Game game) {
		super(game, Optional.empty());
	}
	
	@Override
	public Action getAction() {
		return Action.REST;
	}

	@Override
	public void doAction(GameInputHandler input, GameOutputHandler output) {
		Player player = input.getPlayer();
		List<Card> hand = player.getHand().getDeck();
		hand.addAll(deck);
		deck.clear();
	}

	@Override
	public boolean validateAction(GameInputHandler input, GameOutputHandler output) {
		
		if (!basicValidation(input))
		{
			return false;
		}
		
		if (deck.isEmpty())
		{
			System.out.println("Player trying to pick up empty deck");
			return false;
		}
		
		if (!this.equals(input.getPlayer().getDiscard()))
		{
			System.out.println("Player trying to pick up a discard deck that is not their own");
			return false;
		}
		
		return true;
	}

}
