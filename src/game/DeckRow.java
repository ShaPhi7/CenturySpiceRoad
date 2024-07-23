package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import action.Actionable;

public abstract class DeckRow implements Actionable {

	List<Card> deck = new ArrayList<Card>();
	final Optional<Integer> numberOfVisibleCards;
	
	public DeckRow(Optional<Integer> numberOfVisibleCards)
	{
		this.numberOfVisibleCards = numberOfVisibleCards;
	}
	
	public boolean execute()
	{
		if (!this.validateAction(Game.currentPlayer))
		{
			return false;
		}
		this.doAction(Game.currentPlayer);
		return true;
	}
	
	public boolean basicValidation(Player player) {
		if (!player.equals(Game.currentPlayer))
		{
			System.out.println("Player is not the current player");
			return false;
		}
		
		return true;
	}
	
	public List<Card> getVisibleCards(){
		
		List<Card> ret = new ArrayList<>();
		
		for (int i=0;i<numberOfVisibleCards.orElse(100);i++) //no real limit, just don't go crazy
		{
			ret.add(deck.get(i));
		}
		
		return ret;
	}

	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}

	public Optional<Integer> getNumberOfVisibleCards() {
		return numberOfVisibleCards;
	}	
}
