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
		//Card card = player.getSelectedCard().orElseThrow();

		//TODO
	}

	@Override
	public boolean validateAction(Player player) {
		
		if (!basicValidation(player))
		{
			return false;
		}
		
		if (deck.isEmpty())
		{
			System.out.println("Player trying to play cards from an empty deck");
			return false;
		}
		
		if (player.getHand() != this)
		{
			System.out.println("Player trying to play cards from other players hand");
			return false;
		}
		
		Optional<Card> card = player.getSelectedCard();
		
		if (card.isEmpty())
		{
			System.out.println("No selected card");
			return false;
		}
		
		if (!deck.contains(card.orElse(null)))
		{
			System.out.println("Trying to play a card not in players hand");
			return false;
		}
		
		return true;
	}

}
