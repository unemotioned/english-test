package com.unemotioned.englishtest;

import com.unemotioned.englishtest.common.*;
import com.unemotioned.englishtest.menu.controller.MenuController;
import java.util.Scanner;

public class App {
    void main() {
        FileInitializer fileInit = new FileInitializer();
        fileInit.init();

        Util util = new Util();
        String wFile = Config.WORD_FILE;
        String fFile = Config.FAILED_WORD_FILE;
        long wFileMod = util.modified(wFile);
        long fFileMod = util.modified(fFile);

        try (Scanner scanner = new Scanner(System.in)) {
            MenuController menuCon = new MenuController(scanner);
            menuCon.mainMenu();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CommonViewer cViewer = new CommonViewer();

            if (util.checkMod(wFile, wFileMod)) {
                Sorter sorter = new Sorter(wFile);
                cViewer.sortingMsg(wFile);
                sorter.sort();
            }

            if (util.checkMod(fFile, fFileMod)) {
                Sorter sorter = new Sorter(fFile);
                cViewer.sortingMsg(fFile);
                sorter.sort();
            }

            cViewer.terminate();
        }));
    }
}
