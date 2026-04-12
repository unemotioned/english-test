package kr.or.iei.controller;

import kr.or.iei.common.Config;
import kr.or.iei.common.Util;
import kr.or.iei.model.vo.Word;
import kr.or.iei.viewer.MenuViewer;

import java.util.ArrayList;

public class MenuController {
    MenuViewer mViewer;
    SearchController searchCon;
    EditController editCon;
    TestController testCon;

    Util util;
    ArrayList<Word> wordList;

    public MenuController() {
        mViewer = new MenuViewer();
        searchCon = new SearchController(this);
        editCon = new EditController(this);
        testCon = new TestController(this);

        util = new Util();
        wordList = new ArrayList<>();
    }

    public void mainMenu() {
        while (true) {
            readWordFile();
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
                    testCon.test();
                    break;
                case 5:
                    testCon.reTest();
                    break;
                case 0:
                    mViewer.terminated();
                    return;
                default:
                    break;
            }
        }
    }

    public void readWordFile() {
        wordList = util.readFile(Config.WORD_FILE);
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }
}
