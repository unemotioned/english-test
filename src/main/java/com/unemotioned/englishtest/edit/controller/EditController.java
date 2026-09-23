package com.unemotioned.englishtest.edit.controller;

import com.unemotioned.englishtest.common.CommonViewer;
import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.edit.viewer.EditViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;
import com.unemotioned.englishtest.search.controller.SearchController;
import com.unemotioned.englishtest.search.viewer.SearchViewer;
import java.util.ArrayList;

public class EditController {
    EditViewer editViewer;
    MenuController menuCon;
    SearchController searchCon;
    SearchViewer searchViewer;
    Util util;
    CommonViewer cViewer;

    public EditController(MenuController menuCon) {
        editViewer = new EditViewer();
        this.menuCon = menuCon;
        searchCon = new SearchController(menuCon);
        searchViewer = new SearchViewer();
        util = new Util();
        cViewer = new CommonViewer();
    }

    public void add() {
        Word word = editViewer.add();

        if (word == null) {
            return;
        } else if (menuCon.getWordList().contains(word)) {
            editViewer.printDup(word.getWord());
            return;
        }

        boolean appendRes = util.appendToFile(word, Config.WORD_FILE);
        editViewer.addRes(appendRes);
    }

    public void edit() {
        editViewer.editHeader();
        String keyword = searchViewer.searchViewer();
        if (keyword.equals("C")) {
            cViewer.promptCancel("edit");
            return;
        }

        ArrayList<Word> searchList = searchCon.searchWord(keyword);

        if (searchList.isEmpty()) {
            editViewer.promptNotFound(keyword);
        } else if (searchList.toArray().length == 1) {
            editOrDel(searchList.getFirst());
        } else {
            int index = searchViewer.chooseWord(searchList);
            editOrDel(searchList.get(--index));
        }
    }

    private void editOrDel(Word word) {
        char input = editViewer.editOrDel(word.getWord());
        ArrayList<Word> wordList = menuCon.getWordList();

        if (input == 'e') {
            Word editedWord = editViewer.editWord(word);

            int index = wordList.indexOf(word);
            if (index != -1) {
                wordList.set(index, editedWord);
            }

            boolean editRes = util.overwrite(Config.WORD_FILE, wordList);
            editViewer.editRes(editRes);

        } else if (input == 'd') {
            boolean delWord = editViewer.delWordConsent();
            if (delWord) {
                wordList.remove(word);
                util.overwrite(Config.WORD_FILE, wordList);
            }
        }
    }

    public void nuke() {
        String[] files = {Config.WORD_FILE, Config.FAILED_WORD_FILE};
        String fileName = editViewer.selFile2Nuke(files);

        if (!fileName.isEmpty()) {
            boolean consent = editViewer.confirmNuke(fileName);
            if (!consent) {
                return;
            }
        } else {
            return;
        }

        util.overwrite(fileName, null);
    }
}
