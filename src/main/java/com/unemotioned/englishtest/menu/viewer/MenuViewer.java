package com.unemotioned.englishtest.menu.viewer;

import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.MenuOpt;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuViewer {
    Util util;
    Scanner sc;

    public MenuViewer() {
        util = new Util();
        sc = new Scanner(System.in);
    }

    public MenuOpt menu() {
        clearTerminal();

        System.out.println("===== English Test =====");
        System.out.println("1 Search");
        System.out.println("2 Add new");
        System.out.println("3 Edit / Delete");
        System.out.println("4 Start exam");
        System.out.println("5 Show failed");
        System.out.println("6 Make-up exam");
        System.out.println("7 Nuclear");
        System.out.println("0 Terminate");

        final int minOpt = util.menuMinMax()[0];
        final int maxOpt = util.menuMinMax()[1];

        int input;
        while (true) {
            System.out.print("=> ");
            try {
                input = sc.nextInt();
                sc.nextLine();

                if (input >= minOpt && input <= maxOpt) {
                    break;
                } else {
                    System.out.println("Please choose between " + minOpt + " and " + maxOpt);
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type");
            }
        }

        return switch (input) {
            case 1 -> MenuOpt.SEARCH;
            case 2 -> MenuOpt.ADD;
            case 3 -> MenuOpt.EDIT;
            case 4 -> MenuOpt.EXAM;
            case 5 -> MenuOpt.SHOW;
            case 6 -> MenuOpt.MAKEUP;
            case 7 -> MenuOpt.NUKE;
            case 0 -> MenuOpt.TERMINATE;
            default -> throw new IllegalArgumentException("foobar");
        };
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
