package com.unemotioned.englishtest.menu.viewer;

import com.unemotioned.englishtest.common.Util;
import java.util.Scanner;

public class MenuViewer {
    Scanner sc;
    Util util;

    public MenuViewer() {
        sc = new Scanner(System.in);
        util = new Util();
    }

    public int menu() {
        util.clearTerminal();

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

    public void terminated() {
        System.out.println("Terminated");
    }
}
