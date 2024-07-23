package game;

import java.util.Optional;

public class PointCardDeckRow extends DeckRow {

	private int goldCoins;
	private int silverCoins;
	
	public PointCardDeckRow() {
		super(Optional.of(Integer.valueOf(5)));
		this.goldCoins = Game.players.size() * 2;
		this.silverCoins = Game.players.size() * 2;
	}

	@Override
	public String getActionName() {
		return "Claim";
	}

	@Override
	public void doAction(Player player) {
		//Choose card
		PointCard card = new PointCard(); //TODO
		
		//pay cubes
		SpiceInventory cost = card.getCost();
		player.payCubes(cost);
		
		giveCoinsTo(player, card);
		moveCardTo(player.getHand(), card);
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
		//TODO
		return true;
	}

}
