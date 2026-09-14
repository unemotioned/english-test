package com.unemotioned.englishtest.search.controller;

import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.menu.controller.MenuController;
import com.unemotioned.englishtest.search.viewer.SearchViewer;
import java.util.ArrayList;

public class SearchController {
    final short asciiLowerA = 65;
    final short asciiUpperZ = 122;

    MenuController menuCon;
    SearchViewer searchViewer;

    public SearchController(MenuController menuCon) {
        this.menuCon = menuCon;
        searchViewer = new SearchViewer();
    }

    public void search() {
        String searchWord;
        ArrayList<Word> wordList;

        while (true) {
            searchWord = searchViewer.searchViewer();

            if (searchWord.equals("C")) {
                searchViewer.cancelSearch();
                break;
            }

            // if input is english
            if (searchWord.charAt(0) >= asciiLowerA && searchWord.charAt(0) <= asciiUpperZ) {
                wordList = searchWord(searchWord);
            } else {
                wordList = searchDef(searchWord);
            }

            if (wordList.isEmpty()) {
                searchViewer.noSearchResults(searchWord);
            } else if (wordList.toArray().length == 1) {
                searchViewer.searchResHeader();
                searchViewer.searchRes(wordList.getFirst());
            } else {
                int index = searchViewer.chooseWord(wordList);
                searchViewer.searchRes(wordList.get(--index));
            }
        }
    }

    public ArrayList<Word> searchWord(String searchWord) {
        ArrayList<Word> searchResults = new ArrayList<>();
        int index = 1;

        for (Word word : menuCon.getWordList()) {
            String wordFromFile = word.getWord();

            if (wordFromFile.toLowerCase().contains(searchWord.toLowerCase())) {
                word.setIndex(index++);
                searchResults.add(word);
            }
        }

        return searchResults;
    }

    private ArrayList<Word> searchDef(String searchDef) {
        ArrayList<Word> searchResults = new ArrayList<>();

        for (Word word : menuCon.getWordList()) {
            String def1 = word.getDef1();
            String def2 = word.getDef2();

            if (def1.toLowerCase().contains(searchDef)) {
                searchResults.add(word);
                continue;
            }

            if (def2.toLowerCase().contains(searchDef)) {
                searchResults.add(word);
            }
        }

        return searchResults;
    }
}
