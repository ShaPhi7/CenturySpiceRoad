package board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import card.Card;
import card.MerchantCard;
import card.SpiceCard;
import card.TradeCard;
import card.UpgradeCard;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;

public class MerchantCardDeckRow extends DeckRow {

	public MerchantCardDeckRow(Game game) {
		super(game, Optional.of(Integer.valueOf(Game.NUMBER_OF_VISIBLE_MERCHANT_CARDS)));
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
			System.out.println("Player can not afford to purchase that merchant card");
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

	public boolean populateFromCsv(String filename) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            System.out.println("File not found!");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            
            System.out.println("Header: " + reader.readLine());
            
            while ((line = reader.readLine()) != null) {

            	String[] parts = line.split(",");
                String type = parts[0];
				int[] ints = Arrays.stream(parts, 1, parts.length)
				        .mapToInt(Integer::parseInt)
				        .toArray();
				MerchantCard merchantCard = null;
				switch (type) {
                    case "s":
                    	merchantCard = new SpiceCard(new SpiceInventory(ints[0], ints[1], ints[2], ints[3]));
                        break;
                    case "t":
                    	merchantCard = new TradeCard(new SpiceInventory(ints[0], ints[1], ints[2], ints[3]),
                        			  new SpiceInventory(ints[4], ints[5], ints[6], ints[7]));
                        break;
                    case "u":
                    	merchantCard = new UpgradeCard(ints[0]);
                        break;
                    default:
                        System.out.println("Unknown card type: " + type);
                }
				deck.add(merchantCard);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
		return true;
	}
}