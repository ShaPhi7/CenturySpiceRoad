package csr.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component(value="GameManager")
public class GameManager {

	private final static Object monitor = new Object();
	private static boolean readyToProceed = false;
	
	private List<GameSession> sessions;
	private Scanner scanner;

	public static void main(String[] args) {
		GameManager gameManager = new GameManager();
		gameManager.start();
	}

	public GameManager() {
		this.sessions = new ArrayList<>();
		this.scanner = new Scanner(System.in);
	}

	public void start() {
		while (true) {
			System.out.println("Welcome to Century Spice Road!");
			System.out.println("Enter 'new' to start a new game, 'exit' to quit:");
			String input = getUserInput();
			
			if ("exit".equalsIgnoreCase(input)) {
				System.out.println("Exiting program.");
				exit();
				break;
			} else if ("new".equalsIgnoreCase(input)) {
				startNewGame();
				pause();
			} else if ("resume".equalsIgnoreCase(input)) {
				resumeGame();
			} else {
				
				System.out.println("Unknown command.");
			}
		}
	}

	private void resumeGame() {
		System.out.println("Which game do you wish to resume?");
		for (int i=0; i< sessions.size(); i++)
		{
			System.out.println("[" + i + "] " + sessions.get(i).getUuid());
		}
		
		String nextLine = scanner.nextLine();
		GameSession session = null;
		try {
			session = sessions.get(Integer.valueOf(nextLine));
			session.resume();
			pause();
		}
		catch (NumberFormatException | IndexOutOfBoundsException e)
		{
			System.out.println("Invalid Entry: " + nextLine);
		}
	}

	private void pause() {
		synchronized (monitor) {
			try {
				while (!readyToProceed) {
					monitor.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				readyToProceed = false;
			}
		}
	}

	public synchronized static void resume() {
		synchronized (monitor) {
			readyToProceed = true;
			monitor.notify();  // Notify one waiting thread
		}
	}
	
	private void exit() {
		scanner.close();
	}
	
	private void startNewGame() {
		GameSession session = new GameSession();
		sessions.add(session);
		session.start();
	}

	private String getUserInput() {
		System.out.print("> ");
		return scanner.nextLine();
	}
}