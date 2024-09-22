package csr.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csr.card.PointCard;

public class GameTest {
	
	Game game = new Game();
	
	@BeforeEach
    public void setUp() {
		game = new Game();
    	game.getPlayers().clear();
    }
    
    @Test
    public void testShouldNotEndGameNotStartingPlayer() {
    	Player startingPlayer = new Player(game, true);
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	
    	Player otherPlayer = new Player(game, false);
    	game.setCurrentPlayer(otherPlayer);
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	game.getPlayers().add(startingPlayer);
    	game.getPlayers().add(otherPlayer);
    	
    	game.setPlayers(players);
    	
    	assertFalse(game.shouldEndGame());
    }
    
    @Test
    public void testShouldNotEndGameNotEnoughCardsTwoPlayer() {
    	game.setNumberOfPlayers(2);
    	
    	Player startingPlayer = new Player(game, true);
    	game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(game, true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	players.add(startingPlayer);
    	players.add(otherPlayer);
    	
    	game.setPlayers(players);
    	
    	assertFalse(game.shouldEndGame());
    }
    
    @Test
    public void testShouldNotEndGameNotEnoughCardsMoreThanTwoPlayers() {
    	game.setNumberOfPlayers(5);
    	Player startingPlayer = new Player(game, true);
    	game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(game, true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	List<Player> players = new ArrayList<Player>();
    	
		players.add(startingPlayer);
    	players.add(otherPlayer);
    	players.add(new Player(game, false));
    	players.add(new Player(game, false));
    	players.add(new Player(game, false));
    	
    	game.setPlayers(players);
    	
    	assertFalse(game.shouldEndGame());
    }
    
    
    @Test
    public void testShouldEndGameTwoPlayers() {
    	game.setNumberOfPlayers(2);
    	Player startingPlayer = new Player(game, true);
    	game.setCurrentPlayer(startingPlayer);
    	
    	Player otherPlayer = new Player(game, true);
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	otherPlayer.addToHand(new PointCard());
    	
    	List<Player> players = new ArrayList<Player>();
    	
    	players.add(startingPlayer);
    	players.add(otherPlayer);
    	
    	game.setPlayers(players);
    	
    	assertTrue(game.shouldEndGame());
    }
    
    @Test
    public void testShouldEndGameMoreThanTwoPlayers() {
    	game.setNumberOfPlayers(5);
    	Player startingPlayer = new Player(game, true);
    	game.setCurrentPlayer(startingPlayer);
    	
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	startingPlayer.addToHand(new PointCard());
    	
    	List<Player> players = new ArrayList<Player>();
		players.add(startingPlayer);
    	players.add(new Player(game, false));
    	players.add(new Player(game, false));
    	players.add(new Player(game, false));
    	players.add(new Player(game, false));
    	
    	game.setPlayers(players);
    	
    	assertTrue(game.shouldEndGame());
    }
}
