package com.unemotioned.englishtest.menu.viewer;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuViewer {
    Scanner sc;

    public MenuViewer() {
        sc = new Scanner(System.in);
    }

    public int menu() {
        clearTerminal();

        int input;
        System.out.println("===== English Test =====");
        System.out.println("1 Search");
        System.out.println("2 Add new");
        System.out.println("3 Edit / Delete");
        System.out.println("4 Start exam");
        System.out.println("5 Show failed");
        System.out.println("6 Make-up exam");
        System.out.println("0 Terminate");

        while (true) {
            System.out.print("=> ");
            try {
                input = sc.nextInt();
                sc.nextLine();

                if (input >= 0 && input <= 5) {
                    break;
                } else {
                    System.out.println("Please choose 1-5 or 0");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type");
            }
        }
        return input;
    }

    private void clearTerminal() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Util.clearTerminal() failed." + e);
        }
    }

    public void terminate() {
        System.out.println("Terminated");
    }
}
