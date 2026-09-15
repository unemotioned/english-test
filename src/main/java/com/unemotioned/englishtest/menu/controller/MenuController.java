package com.unemotioned.englishtest.menu.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.edit.controller.EditController;
import com.unemotioned.englishtest.exam.controller.ExamController;
import com.unemotioned.englishtest.menu.viewer.MenuViewer;
import com.unemotioned.englishtest.search.controller.SearchController;
import lombok.Getter;

import java.util.ArrayList;

public class MenuController {
    MenuViewer mViewer;
    SearchController searchCon;
    EditController editCon;
    ExamController examCon;

    Util util;

    @Getter
    ArrayList<Word> wordList;

    public MenuController() {
        mViewer = new MenuViewer();
        searchCon = new SearchController(this);
        editCon = new EditController(this);
        examCon = new ExamController(this);

        util = new Util();
        wordList = new ArrayList<>();
    }

    public void mainMenu() {
        while (true) {
            wordList = util.readFile(Config.WORD_FILE);

            int menu = mViewer.menu();
            switch (menu) {
                case 1:
                    searchCon.search();
                    break;
                case 2:
                    editCon.add();
                    break;
                case 3:
                    editCon.edit();
                    break;
                case 4:
                    examCon.exam();
                    break;
                case 5:
                    examCon.showFailed();
                    break;
                case 6:
                    examCon.makeup();
                    break;
                case 7:
                    editCon.nuke();
                    break;
                case 0:
                    mViewer.terminate();
                    return;
                default:
                    break;
            }
        }
    }
}
