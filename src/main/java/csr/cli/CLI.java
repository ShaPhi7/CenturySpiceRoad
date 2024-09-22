package csr.cli;

import java.util.Scanner;

public class CLI {
    private static Scanner scanner = new Scanner(System.in);
    
    public static String getInput() {
        return scanner.nextLine();
    }

    public static void print(String message) {
        System.out.println(message);
    }
}