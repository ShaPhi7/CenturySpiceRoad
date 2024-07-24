package board;

import java.util.List;
import java.util.Optional;

import card.Card;
import card.MerchantCard;
import game.Player;

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
		MerchantCard card = (MerchantCard) player.getSelectedCard().orElseThrow();
		player.play(card);
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
		
		Optional<Card> cardOptional = player.getSelectedCard();
		
		if (cardOptional.isEmpty())
		{
			System.out.println("No selected card");
			return false;
		}
		
		Card card = cardOptional.orElseThrow();
		
		if (!(card instanceof MerchantCard))
		{
			System.out.println("Player trying to play a non-Merchant card");
			return false;
		}
		
		MerchantCard merchantCard = (MerchantCard) card;
		
		if (!deck.contains(merchantCard))
		{
			System.out.println("Trying to play a card not in players hand");
			return false;
		}
		
		if (!player.canAffordToPlay(merchantCard))
		{
			System.out.println("Player can not afford to play this card");
			return false;
		}
		
		return true;
	}

}
