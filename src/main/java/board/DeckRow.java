package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import action.Actionable;
import card.Card;
import game.Game;
import game.Player;

public abstract class DeckRow implements Actionable {

	List<Card> deck = new ArrayList<Card>();
	final Optional<Integer> greatestNumberOfVisibleCards;
	
	public DeckRow(Optional<Integer> numberOfVisibleCards)
	{
		this.greatestNumberOfVisibleCards = numberOfVisibleCards;
	}
	
	public boolean execute()
	{
		if (!validateAction(Game.currentPlayer))
		{
			return false;
		}
		doAction(Game.currentPlayer);
		completeAction();
		return true;
	}
	
	private void completeAction() {
		// TODO discard down to 10 cubes in caravan
		
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
		
		for (int i=0;i<getNumberOfVisibleCards().orElse(100);i++) //no real limit, just don't go crazy
		{
			ret.add(deck.get(i));
		}
		
		return ret;
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	private Optional<Integer> getNumberOfVisibleCards() {
		if (deck.size() < greatestNumberOfVisibleCards.orElse(100))
		{
			return Optional.of(deck.size());
		}
		
		return greatestNumberOfVisibleCards;
	}
	
	public boolean isCardVisible(Card card) {
		List<Card> visibleCards = getVisibleCards();
		return visibleCards.contains(card);
	}

	public boolean contains(Card card) {
		return deck.contains(card);
	}
	
	public void moveCardTo(DeckRow otherDeck, Card card) {
		remove(card);
		otherDeck.add(card);
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	public void remove(Card card) {
		deck.remove(card);
	}
	
	public List<Card> getDeck() {
		return deck;
	}

	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}
}
