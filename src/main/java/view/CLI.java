package view;

import java.util.Scanner;

import action.ActionRequest;
import action.Output;
import controller.GameManager;

public class CLI extends GameView {

	private final Scanner scanner = new Scanner(System.in);
	private ActionRequest input = new ActionRequest();
	
	@Override
    public ActionRequest getInput() {

        System.out.println("Enter commands (type 'exit' to quit):");

        while (true) {
            System.out.print("$ ");
            String nextLine = scanner.nextLine();

            if (nextLine.contains("exit")) {
                System.out.println("Exiting program.");
                input.exitRequested = true;
                break;
            }

            // Process the input
            //handleCommand(input);
        }
        
        GameManager.resume();
        
        return input;
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
