package kr.or.iei.viewer;

import java.util.Scanner;

public class MenuViewer {
    Scanner sc;

    public MenuViewer() {
        sc = new Scanner(System.in);
    }

    public int menu() {
        System.out.println("===== Study English =====");
        System.out.println("1 Search");
        System.out.println("2 Add new");
        System.out.println("3 Edit / Delete");
        System.out.println("4 Start test");
        System.out.println("5 Make-up exam");
        System.out.println("0 Terminate");
        System.out.print("=> ");
        return sc.nextInt();
    }

    public void terminated() {
        System.out.println("Terminated");
    }
}
