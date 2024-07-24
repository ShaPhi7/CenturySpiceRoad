package board;

import java.util.NoSuchElementException;
import java.util.Optional;

import card.Card;
import card.MerchantCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public class MerchantCardDeckRow extends DeckRow {

	public MerchantCardDeckRow() {
		super(Optional.of(Integer.valueOf(Game.NUMBER_OF_VISIBLE_MERCHANT_CARDS)));
	}

	@Override
	public String getActionName() {
		return "Acquire";
	}

	@Override
	public void doAction(Player player) {
		//Choose card
		MerchantCard card = (MerchantCard) player.getSelectedCard().orElseThrow();
		
		//pay cubes
		placeCubesOnLowerCards(player, card);
		
		moveCardTo(player.getHand(), card);
		
		SpiceInventory cubesWithAcquire = card.getCubesWithAcquire();
		player.gainSpices(cubesWithAcquire);
	}

	private void placeCubesOnLowerCards(Player player, MerchantCard card) {
		int indexOf = deck.indexOf(card);
		for (int i=0;i<indexOf;i++)
		{
			Spice spice = Spice.YELLOW_TUMERIC; //TODO - give choice
			
			player.payCube(spice);
			
			MerchantCard lowerCard = (MerchantCard) deck.get(i);
			lowerCard.placeCubeOnCard(spice);
		}
	}
	
	@Override
	public boolean validateAction(Player player) {
		if (!basicValidation(player))
		{
			return false;
		}
		
		Optional<MerchantCard> cardOptional = validateSelectedCard(player);
		if (cardOptional.isEmpty())
		{
			return false;
		}
		MerchantCard card = cardOptional.orElseThrow();  
		
		if (!player.canAffordToAcquire(card))
		{
			System.out.println("Player can not afford to purchase that card");
			return false;
		}
		
		return true;
	}

	private Optional<MerchantCard> validateSelectedCard(Player player) {
		
		Optional<Card> selectedCard = player.getSelectedCard();
		
		MerchantCard card = null;
		try {
		    card = (MerchantCard) selectedCard.orElseThrow();
		} catch (NoSuchElementException e) {
		    System.out.println("No card was selected");
		    return Optional.empty();
		} catch (ClassCastException e) {
		    System.out.println("Selected card was not a merchant card");
		    return Optional.empty();
		}
		
		if (!deck.contains(card))
		{
			System.out.println("Card selected is not in deck");
			return Optional.empty();
		}
		
		if (!isCardVisible(card))
		{
			System.out.println("Card selected is not visible");
			return Optional.empty();
		}
		
		return Optional.of(card);
	}
	
}
