package com.unemotioned.englishtest;

import com.unemotioned.englishtest.common.FileInitializer;
import com.unemotioned.englishtest.menu.controller.MenuController;


public class App {
    void main() {
        FileInitializer fileInit = new FileInitializer();
        fileInit.init();

        MenuController menuCon = new MenuController();
        menuCon.mainMenu();
    }
}
