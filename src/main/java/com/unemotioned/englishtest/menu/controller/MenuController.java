package com.unemotioned.englishtest.menu.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.MenuOpt;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.edit.controller.EditController;
import com.unemotioned.englishtest.exam.controller.ExamController;
import com.unemotioned.englishtest.menu.viewer.MenuViewer;
import com.unemotioned.englishtest.search.controller.SearchController;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {
    MenuViewer mViewer;
    SearchController searchCon;
    EditController editCon;
    ExamController examCon;

    Util util;

    @Getter
    ArrayList<Word> wordList;

    private long lastModified = 0;

    public MenuController(Scanner scanner) {
        mViewer = new MenuViewer(scanner);
        searchCon = new SearchController(this, scanner);
        editCon = new EditController(this, scanner);
        examCon = new ExamController(this, scanner);

        util = new Util();
        wordList = new ArrayList<>();
    }

    public void mainMenu() {
        while (true) {
            File wordFile = new File(Config.WORD_FILE);
            long currentModified = wordFile.lastModified();

            if (currentModified != lastModified) {
                wordList = util.readFile(Config.WORD_FILE);
                lastModified = currentModified;
            }

            MenuOpt menu = mViewer.menu();
            switch (menu) {
                case SEARCH -> searchCon.search();
                case ADD -> editCon.add();
                case EDIT -> editCon.edit();
                case EXAM -> examCon.exam();
                case SHOW -> examCon.showFailed();
                case MAKEUP -> examCon.makeup();
                case NUKE -> editCon.nuke();
                case TERMINATE -> {
                    return;
                }
            }
        }
    }
}
