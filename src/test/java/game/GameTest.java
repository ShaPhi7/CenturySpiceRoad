package game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import card.PointCard;

public class GameTest {
    @Before
    public void setUp() {
    	Game.players.clear();
    }
    
    @Test
    public void testShouldNotEndGameNotStartingPlayer() {
    	Player startingPlayer = new Player(true);
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	
    	Player otherPlayer = new Player(false);
    	Game.setCurrentPlayer(otherPlayer);
    	
    	Game.players.add(startingPlayer);
    	Game.players.add(otherPlayer);
    	
    	assertFalse(Game.shouldEndGame());
    }
    
    @Test
    public void testShouldNotEndGameNotEnoughCardsTwoPlayer() {
    	Game.NUMBER_OF_PLAYERS = 2;
    	
    	Player startingPlayer = new Player(true);
    	Game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	Game.players.add(startingPlayer);
    	Game.players.add(otherPlayer);
    	
    	assertFalse(Game.shouldEndGame());
    }
    
    @Test
    public void testShouldNotEndGameNotEnoughCardsMoreThanTwoPlayers() {
    	Game.NUMBER_OF_PLAYERS = 5;
    	Player startingPlayer = new Player(true);
    	Game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	Game.players.add(startingPlayer);
    	Game.players.add(otherPlayer);
    	Game.players.add(new Player(false));
    	Game.players.add(new Player(false));
    	Game.players.add(new Player(false));
    	
    	assertFalse(Game.shouldEndGame());
    }
    
    
    @Test
    public void testShouldEndGameTwoPlayers() {
    	Game.NUMBER_OF_PLAYERS = 2;
    	Player startingPlayer = new Player(true);
    	Game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	Game.players.add(startingPlayer);
    	Game.players.add(otherPlayer);
    	
    	assertTrue(Game.shouldEndGame());
    }
    
    @Test
    public void testShouldEndGameMoreThanTwoPlayers() {
    	Game.NUMBER_OF_PLAYERS = 5;
    	Player startingPlayer = new Player(true);
    	Game.setCurrentPlayer(startingPlayer);
    	
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	
    	Game.players.add(startingPlayer);
    	Game.players.add(new Player(false));
    	Game.players.add(new Player(false));
    	Game.players.add(new Player(false));
    	Game.players.add(new Player(false));
    	
    	assertTrue(Game.shouldEndGame());
    }
}
