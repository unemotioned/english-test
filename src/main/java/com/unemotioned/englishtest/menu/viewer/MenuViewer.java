package com.unemotioned.englishtest.menu.viewer;

import com.unemotioned.englishtest.common.Util;
import java.io.*;
import java.util.Scanner;

public class MenuViewer {
    Scanner sc;
    Util util;

    public MenuViewer() {
        sc = new Scanner(System.in);
        util = new Util();
    }

    public int menu() {
        clearTerminal();

        System.out.println("===== English Test =====");
        System.out.println("1 Search");
        System.out.println("2 Add new");
        System.out.println("3 Edit / Delete");
        System.out.println("4 Start exam");
        System.out.println("5 Make-up exam");
        System.out.println("0 Terminate");
        System.out.print("=> ");

        return sc.nextInt();
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

    public void terminated() {
        System.out.println("Terminated");
    }
}
