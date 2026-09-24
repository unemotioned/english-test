package com.unemotioned.englishtest.search.viewer;

import com.unemotioned.englishtest.common.CommonViewer;
import com.unemotioned.englishtest.common.vo.Word;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SearchViewer {
    Scanner sc;
    CommonViewer cViewer;

    public SearchViewer() {
        sc = new Scanner(System.in);
        cViewer = new CommonViewer();
    }

    public String searchViewer() {
        System.out.print("Search Word ([C]ancel): ");
        return sc.nextLine().trim();
    }

    public void searchResHeader() {
        System.out.println("===== Search Result =====");
    }

    public void searchRes(Word word) {
        System.out.println("Word: " + word.getWord());
        System.out.println("Definitions: " + word.getDef1() + ", " + word.getDef2());
    }

    public int chooseWord(ArrayList<Word> list) {
        System.out.println("===== Search Results =====");

        for (Word word : list) {
            System.out.println(word.getIndex() + ": " + word.getWord());
        }

        int listLen = list.toArray().length;
        int sel;

        while (true) {
            System.out.print("Select one (0 to Cancel): ");
            try {
                sel = sc.nextInt();

                if (sel > 0 && sel < listLen) {
                    break;
                } else if (sel == 0) {
                    cViewer.promptCancel("search");
                    break;
                } else {
                    System.out.println("Please choose between 1-" + listLen);
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type");
                // consume input buffer and go back to start of the while loop
                sc.nextLine();
            }
        }

        return sel;
    }

    public void noSearchResults(String searchedWord) {
        System.out.println("Not found: " + searchedWord);
    }
}
