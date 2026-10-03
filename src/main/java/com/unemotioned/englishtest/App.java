package com.unemotioned.englishtest;

import com.unemotioned.englishtest.common.FileInitializer;
import com.unemotioned.englishtest.menu.controller.MenuController;
import java.util.Scanner;

public class App {
    void main() {
        FileInitializer fileInit = new FileInitializer();
        fileInit.init();

        try (Scanner scanner = new Scanner(System.in)) {
            MenuController menuCon = new MenuController(scanner);
            menuCon.mainMenu();
        }
    }
}
