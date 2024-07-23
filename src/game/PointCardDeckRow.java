package game;

import java.util.Optional;

public class PointCardDeckRow extends DeckRow {

	public PointCardDeckRow() {
		super(Optional.of(Integer.valueOf(5)));
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
		
		//TODO - get coins
		
		//get card
		moveTo(card, player.getHand());
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
