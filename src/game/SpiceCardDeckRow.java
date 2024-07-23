package game;

import java.util.Optional;

public class SpiceCardDeckRow extends DeckRow {

	public SpiceCardDeckRow(Optional<Integer> numberOfVisibleCards) {
		super(Optional.of(Integer.valueOf(6)));
	}

	@Override
	public String getActionName() {
		return "Acquire";
	}

	@Override
	public void doAction(Player player) {
		//Choose card
		SpiceCard card = new SpiceCard(); //TODO
		
		//pay cubes
		placeCubesOnLowerCards(player, card);
		
		moveCardTo(player.getHand(), card);
	}

	private void placeCubesOnLowerCards(Player player, SpiceCard card) {
		int indexOf = deck.indexOf(card);
		for (int i=0;i<indexOf;i++)
		{
			Spice spice = player.selectCubeFromCaravan();
			
			SpiceCard lowerCard = (SpiceCard) deck.get(i);
			lowerCard.placeCubeOnCard(spice);
		}
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
