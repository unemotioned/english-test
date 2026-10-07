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
import java.util.Scanner;
import java.util.function.Function;

public class EditController {
    EditViewer editViewer;
    MenuController menuCon;
    SearchController searchCon;
    SearchViewer searchViewer;
    Util util;
    CommonViewer cViewer;

    public EditController(MenuController menuCon, Scanner scanner) {
        editViewer = new EditViewer(scanner);
        this.menuCon = menuCon;
        searchCon = new SearchController(menuCon, scanner);
        searchViewer = new SearchViewer(scanner);
        util = new Util();
        cViewer = new CommonViewer();
    }

    public void add() {
        ArrayList<Word> entries = menuCon.getWordList();
        editViewer.addHeader();

        // Method Reference ==> `::` operator, shortened for lambda
        // Word::getWord ==> word -> word.getWord()
        String value = readUniqueMember("New word: ", entries, Word::getWord);
        if (value == null) {
            return;
        }
        String def1 = readUniqueMember("Definition (1/2): ", entries, Word::getDef1);
        if (def1 == null) {
            return;
        }
        String def2 = readUniqueMember("Definition (2/2): ", entries, Word::getDef2);
        if (def2 == null) {
            return;
        }

        Word word = new Word(value, def1, def2, 0);
        boolean appendRes = util.appendToFile(word, Config.WORD_FILE);
        if (appendRes) {
            entries.add(word);
        }
        editViewer.addRes(appendRes);
    }

    // Function<Word, String> ==> take `Word` input and return `String`
    private String readUniqueMember(String prompt, ArrayList<Word> entries, Function<Word, String> getter) {
        while (true) {
            String input = editViewer.getMembers(prompt);
            if (input == null) {
                return null;
            }

            // .apply(): run the function with this args
            boolean duplicate = entries.stream().anyMatch(entry -> input.equalsIgnoreCase(getter.apply(entry)));
            if (!duplicate) {
                return input;
            }
            editViewer.printDup(input);
        }
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
        } else if (searchList.size() == 1) {
            editOrDel(searchList.getFirst());
        } else {
            int index = searchViewer.chooseWord(searchList);
            editOrDel(searchList.get(--index));
        }
    }

    private void editOrDel(Word word) {
        final String og = word.getWord();
        char input = editViewer.editOrDel(og);
        String file = Config.WORD_FILE;
        ArrayList<Word> wordList = menuCon.getWordList();

        if (input == 'e') {
            Word editedWord = editViewer.editWord(word);

            int index = wordList.indexOf(word);
            if (index != -1) {
                wordList.set(index, editedWord);
            }

            boolean editRes = util.overwrite(file, wordList);
            editViewer.editRes(editRes, og, editedWord.getWord());

        } else if (input == 'd') {
            boolean delWord = editViewer.delWordConsent();
            if (delWord) {
                wordList.remove(word);
                boolean res = util.overwrite(file, wordList);
                editViewer.delRes(file, word.getWord(), res);
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

        boolean res = util.overwrite(fileName, null);
        editViewer.nukeRes(fileName, res);
    }
}
