package board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Optional;

import action.Action;
import card.Card;
import card.PointCard;
import game.Game;
import game.Player;
import game.SpiceInventory;

public class PointCardDeckRow extends DeckRow {

	private int goldCoins;
	private int silverCoins;
	
	public PointCardDeckRow(Game game) {
		super(game, Optional.of(Integer.valueOf(Game.NUMBER_OF_VISIBLE_POINT_CARDS)));
		this.goldCoins = game.getNumberOfPlayers() * 2;
		this.silverCoins = game.getNumberOfPlayers() * 2;
	}

	@Override
	public Action getAction() {
		return Action.CLAIM;
	}

	@Override
	public void doAction(Player player) {
		//Choose card
		PointCard selectedCard = (PointCard) player.getSelectedCard().orElseThrow();
		
		//pay cubes
		SpiceInventory cost = selectedCard.getCost();
		player.payCubes(cost);
		
		giveCoinsTo(player, selectedCard);
		moveCardTo(player.getHand(), selectedCard);
	}

	//TODO - it would be nice to tidy the nested ifs
	private void giveCoinsTo(Player player, PointCard card) {
		int cardPosition = deck.indexOf(card);
		if (cardPosition == 0)
		{
			if (goldCoins > 0)
			{
				giveGoldCoinTo(player);
			}
			else if (silverCoins > 0)
			{
				giveSilverCoinTo(player);
			}
		}
		else if (cardPosition == 1)
		{
			if (goldCoins > 0 && silverCoins > 0)
			{
				giveSilverCoinTo(player);
			}
		}
	}

	public int getGoldCoins() {
		return goldCoins;
	}

	public void setGoldCoins(int goldCoins) {
		this.goldCoins = goldCoins;
	}

	public int getSilverCoins() {
		return silverCoins;
	}

	public void setSilverCoins(int silverCoins) {
		this.silverCoins = silverCoins;
	}
	
	private void giveGoldCoinTo(Player player) {
		takeGoldCoin();
		player.gainGoldCoin();
	}

	private void takeGoldCoin() {
		goldCoins--;
	}
	
	private void giveSilverCoinTo(Player player) {
		takeSilverCoin();
		player.gainSilverCoin();
	}

	private void takeSilverCoin() {
		silverCoins--;
	}

	@Override
	public boolean validateAction(Player player) {
		if (!basicValidation(player))
		{
			return false;
		}

		Optional<PointCard> cardOptional = validateSelectedCard(player);
		if (cardOptional.isEmpty())
		{
			return false;
		}
		PointCard card = cardOptional.orElseThrow();
		
		if (!player.canAfford(card))
		{
			System.out.println("Player can not afford to purchase that point card");
			return false;
		}
		
		if (player.getPointCards().size() > game.getTargetNumberOfPointCards())
		{
			System.out.println("Player has too many points cards, why has the game not ended already?");
			return false;
		}
		
		return true;
	}

	private Optional<PointCard> validateSelectedCard(Player player) {
		
		Optional<Card> selectedCard = player.getSelectedCard();

		PointCard card = null;
		try {
		    card = (PointCard) selectedCard.orElseThrow();
		} catch (NoSuchElementException e) {
		    System.out.println("No card was selected");
		    return Optional.empty();
		} catch (ClassCastException e) {
		    System.out.println("Selected card was not a point card");
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
                if (parts.length == 5) {
                    int y = Integer.parseInt(parts[0].trim());
                    int r = Integer.parseInt(parts[1].trim());
                    int g = Integer.parseInt(parts[2].trim());
                    int b = Integer.parseInt(parts[3].trim());
                    int v = Integer.parseInt(parts[4].trim());
                    
                    deck.add(new PointCard(y, r, g, b, v));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
		return true;
	}

}
