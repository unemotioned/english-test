package com.unemotioned.englishtest.search.viewer;

import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchViewer {
    Scanner sc;

    public SearchViewer() {
        sc = new Scanner(System.in);
    }

    public String searchViewer() {
        System.out.print("Search Word / Cancel(C): ");
        return sc.next();
    }

    public void cancelSearch() {
        System.out.println("Canceling Search...");
    }

    public void searchResHeader() {
        System.out.println("===== Search Result =====");
    }

    public void searchRes(Word word) {
        System.out.println("Word: " + word.getWord());
        System.out.println("Definitions: " + word.getDef1() + ", " + word.getDef2());
    }

    public int chooseWord(ArrayList<Word> wordList) {
        System.out.println("===== Search Results =====");

        for (Word word : wordList) {
            System.out.println(word.getIndex() + ": " + word.getWord());
        }

        System.out.print("Select one: ");
        return sc.nextInt();
    }

    public void noSearchResults(String searchedWord) {
        System.out.println("No such words: " + searchedWord);
    }
}
