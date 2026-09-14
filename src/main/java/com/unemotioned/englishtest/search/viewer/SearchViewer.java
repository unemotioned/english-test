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
        System.out.println("\n--------------------");
        System.out.println("Search Word / Cancel(C)");
        System.out.print("=> ");
        return sc.next();
    }

    public void cancelSearch() {
        System.out.println("Canceling Search...\n");
    }

    public void searchResHeader() {
        System.out.println("\n===== Search Result =====");
    }

    public void searchRes(Word word) {
        System.out.println("\nWord: " + word.getWord());
        System.out.println("Definitions: " + word.getDef1() + ", " + word.getDef2());
    }

    public int chooseWord(ArrayList<Word> wordList) {
        System.out.println("\n===== Search Results =====\n");

        for (Word word : wordList) {
            System.out.println(word.getIndex() + ": " + word.getWord());
        }

        System.out.print("\nSelect index of word to show definitions: ");
        return sc.nextInt();
    }

    public void noSearchResults(String searchedWord) {
        System.out.println("No such words: " + searchedWord);
    }
}
