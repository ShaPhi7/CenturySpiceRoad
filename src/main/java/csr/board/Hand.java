package csr.board;

import java.util.List;
import java.util.Optional;

import csr.action.Action;
import csr.action.GameOutputHandler;
import csr.card.Card;
import csr.card.MerchantCard;
import csr.game.Game;
import csr.game.Player;
import csr.view.GameInputHandler;

public class Hand extends DeckRow {
	
	public Hand(Game game) {
		super(game, Optional.empty());
	}
	
	public Hand(Game game, List<Card> cards) {
		super(game, Optional.empty());
		deck.addAll(cards);
	}

	@Override
	public Action getAction() {
		return Action.PLAY;
	}

	@Override
	public void doAction(GameInputHandler input, GameOutputHandler output) {
		MerchantCard card = (MerchantCard) input.getSelectedCard().orElseThrow();
		input.getPlayer().play(card, input);
	}

	@Override
	public boolean validateAction(GameInputHandler input, GameOutputHandler output) {
		Player player = input.getPlayer();
		if (!basicValidation(input))
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
		
		Optional<Card> cardOptional = input.getSelectedCard();
		
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
		
		if (!player.canAfford(merchantCard, input))
		{
			System.out.println("Player can not afford to play this card");
			return false;
		}
		
		if (!input.selectedUpgradesAreSensible())
		{
			System.out.println("Trying to upgrade cubes beyond brown");
			return false;
		}
		
		if (!input.selectedMoreUpgradesThanPermitted(card))
		{
			System.out.println("Trying to upgrade more cubes than the card permits");
			return false;
		}
		
		return true;
	}

}
