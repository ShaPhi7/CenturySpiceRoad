package view;

import java.util.Scanner;

import action.ActionRequest;

public class CliInputHandler extends GameInputHandler {

	private final Scanner scanner = new Scanner(System.in);
	private ActionRequest request = new ActionRequest();
	
	@Override
	public ActionRequest getInput() {

		CLI.print("Enter commands (type 'exit' to quit):");

		CLI.print("$ ");
		String nextLine = CLI.getInput();

		if (nextLine.contains("exit")) {
			CLI.print("Exiting program.");
			request.setExitRequested(true);
		}
		
		request.setAction(nextLine);

		return request;

	}

	@Override
	public ActionRequest getNumberOfPlayers() {
		CLI.print("It is a 2-5 player game.");
		CLI.print("How many players would you like to play with?");
		
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("setup");
		actionRequest.setSelectedNumberOfPlayers(Integer.valueOf(CLI.getInput()));
		
		return actionRequest;
	}
}
