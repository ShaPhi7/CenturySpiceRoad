package view;

import java.util.Scanner;

public class CLI {
    private static Scanner scanner;

    public CLI() {
        scanner = new Scanner(System.in);
    }

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void print(String message) {
        System.out.println(message);
    }
}