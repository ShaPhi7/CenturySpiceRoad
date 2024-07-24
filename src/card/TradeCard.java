package card;

import game.Player;
import game.SpiceInventory;

public class TradeCard extends MerchantCard {
	
	private final SpiceInventory from;
	private final SpiceInventory to;
	
	public TradeCard()
	{
		from = new SpiceInventory();
		to = new SpiceInventory();
	}
	
	public TradeCard(SpiceInventory from, SpiceInventory to)
	{
		this.from = from;
		this.to = to;
	}
	
	@Override
	public void play(Player player) {
		for (int i=0;i<player.getSelectedTimes();i++)
		{
			player.payCubes(from);
			player.gainSpices(to);
		}
	}

	public SpiceInventory getFrom() {
		return from;
	}

	public SpiceInventory getTo() {
		return to;
	}	
}
