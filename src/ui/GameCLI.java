package com.ntu.sc2002.arena.ui;

import java.util.Scanner;

public class GameCLI {
    private final Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nWelcome to the Arena!");
            System.out.println("1) Enter Arena\n0) Quit");
            
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> System.out.println("Feature coming soon...");
                case "0" -> quit = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

}
