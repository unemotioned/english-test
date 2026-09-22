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

            MenuOpt menu = mViewer.menu();
            switch (menu) {
                case SEARCH:
                    searchCon.search();
                    break;
                case ADD:
                    editCon.add();
                    break;
                case EDIT:
                    editCon.edit();
                    break;
                case EXAM:
                    examCon.exam();
                    break;
                case SHOW:
                    examCon.showFailed();
                    break;
                case MAKEUP:
                    examCon.makeup();
                    break;
                case NUKE:
                    editCon.nuke();
                    break;
                case TERMINATE:
                    mViewer.terminate();
                    return;
                default:
                    break;
            }
        }
    }
}
