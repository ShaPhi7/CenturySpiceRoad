package csr.card;

import csr.game.Player;
import csr.game.SpiceInventory;
import csr.view.GameInputHandler;

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
	public SpiceInventory getCost(GameInputHandler input) {

		SpiceInventory multSpiceInventory = new SpiceInventory();
		for (int i=0; i<input.getSelectedNumberOfTrades(); i++)
		{
			multSpiceInventory.addSpices(from);
		}
		return multSpiceInventory;
	}
	
	@Override
	public void play(GameInputHandler input) {
		Player player = input.getPlayer();
		for (int i=0;i<input.getSelectedNumberOfTrades();i++)
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
	
	@Override
	public String toString() {
		return "MerchantCard [Trade " + from + " -> " + to + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeCard other = (TradeCard) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
