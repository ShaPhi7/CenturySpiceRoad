package action;

import java.util.Set;

import game.Card;
import game.Player;

public class RestAction extends AbstractAction {

	@Override
	public void doAction(Player player) {
		Set<Card> discard = player.getDiscard();
		Set<Card> hand = player.getHand();
		hand.addAll(discard);
	}

}
