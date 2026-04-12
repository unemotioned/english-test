package kr.or.iei.controller;

import kr.or.iei.model.vo.Word;
import kr.or.iei.viewer.SearchViewer;

import java.util.ArrayList;

public class SearchController {
    MenuController menuCon;
    SearchViewer searchViewer;

    public SearchController(MenuController menuCon) {
        this.menuCon = menuCon;
        this.searchViewer = new SearchViewer();
    }

    public void search() {
        String searchWord;
        final String cancelSearch = "C";
        boolean searching = true;

        while (searching) {
            searchWord = searchViewer.searchViewer(cancelSearch);

            if (searchWord.equals(cancelSearch)) {
                searching = false;
                searchViewer.cancelSearch();
                continue;
            }

            ArrayList<Word> wordList = searchWord(searchWord);

            if (!wordList.isEmpty()) {
                searchViewer.searchResultsHeader();
                for (Word word : wordList) {
                    searchViewer.showSearchResults(word);
                }
            } else {
                searchViewer.noSearchResults(searchWord);
            }
        }
    }

    public ArrayList<Word> searchWord(String searchWord) {
        ArrayList<Word> searchResults = new ArrayList<>();
        Word searchResult;
        int searchWordLength = searchWord.length();

        for (Word word : menuCon.getWordList()) {
            String wordFromFile = word.getWord();
            int wordFromFileLength = wordFromFile.length();

            if (searchWordLength > wordFromFileLength) {
                continue;
            } else if (searchWordLength == wordFromFileLength) {
                if (searchWord.equalsIgnoreCase(wordFromFile)) {
                    searchResult = new Word();
                    searchResult.setWord(word.getWord());
                    searchResult.setDef1(word.getDef1());
                    searchResult.setDef2(word.getDef2());
                    searchResults.add(searchResult);
                }
            } else if (searchWordLength < wordFromFileLength) {
                for (int i = 0; i < wordFromFileLength - searchWordLength + 1; i++) {
                    String subStr = wordFromFile.substring(i, i + searchWordLength);
                    if (searchWord.equalsIgnoreCase(subStr)) {
                        searchResult = new Word();
                        searchResult.setWord(word.getWord());
                        searchResult.setDef1(word.getDef1());
                        searchResult.setDef2(word.getDef2());
                        searchResults.add(searchResult);
                        break;
                    }
                }
            }
        }
        return searchResults;
    }

    public void searchDef() {}
}
