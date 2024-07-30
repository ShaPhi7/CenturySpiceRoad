package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import action.Action;
import action.Actionable;
import action.GameOutputHandler;
import card.Card;
import game.Game;
import game.Player;
import game.Spice;
import game.SpiceInventory;
import game.State;
import view.GameInputHandler;

public abstract class DeckRow implements Actionable {

	Game game;
	List<Card> deck = new ArrayList<Card>();
	final Optional<Integer> greatestNumberOfVisibleCards;
	
	public DeckRow(Game game, Optional<Integer> numberOfVisibleCards)
	{
		this.game = game;
		this.greatestNumberOfVisibleCards = numberOfVisibleCards;
	}
	
	public boolean execute(GameInputHandler input, GameOutputHandler output)
	{
		if (!validateAction(input, output))
		{
			return false;
		}
		doAction(input, output);
		completeAction();
		return true;
	}
	
	private void completeAction() {
		SpiceInventory caravan = game.getCurrentPlayer().getCaravan();
		while (caravan.getTotalCubes() > 10) {
			Spice spice = caravan.getLowestValueCubePresent(); //TODO - give choice
			caravan.removeSpices(spice, 1);
		}
		
		Player.nextPlayer(game);
	}

	public boolean basicValidation(GameInputHandler input) {
		if (!input.getPlayer().equals(game.getCurrentPlayer()))
		{
			System.out.println("Player is not the current player");
			return false;
		}
		
		if (!game.getCurrentState().equals(State.NEW_TURN))
		{
			System.out.println("Not expecting a player turn action");
			return false;
		}
		
		if (!input.getAction().orElse(Action.DISCARD).equals(getAction()))
		{
			System.out.println("Actions do not match");
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
