package view;

import java.util.Scanner;

import action.ActionRequest;
import action.Output;

public class CLI extends GameView {

	private final Scanner scanner = new Scanner(System.in);
	private ActionRequest request = new ActionRequest();
	
	@Override
	public ActionRequest getInput() {

		System.out.println("Enter commands (type 'exit' to quit):");

		System.out.print("$ ");
		String nextLine = scanner.nextLine();

		if (nextLine.contains("exit")) {
			System.out.println("Exiting program.");
			request.setExitRequested(true);
		}
		
		request.setAction(nextLine);

		return request;

	}
    
    @Override
    public void displayOutput(Output output)
    {
    	for (String update : output.getGameUpdates())
    	{
    		System.out.println(update);
    	}
    	output.clear();
    }
	
}
