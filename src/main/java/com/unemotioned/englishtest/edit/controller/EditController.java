package com.unemotioned.englishtest.edit.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.edit.viewer.EditViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;
import com.unemotioned.englishtest.search.controller.SearchController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditController {
    EditViewer editViewer;
    MenuController menuCon;
    SearchController searchCon;
    Util util;

    Scanner sc;

    public EditController(MenuController menuCon) {
        editViewer = new EditViewer();
        this.menuCon = menuCon;
        searchCon = new SearchController(menuCon);
        util = new Util();

        sc = new Scanner(System.in);
    }

    private boolean checkDup(String word) {
        for (Word foo : menuCon.getWordList()) {
            String wordFromFile = foo.getWord();

            if (wordFromFile.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    public void add() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.WORD_FILE, true))) {
            Word word = editViewer.add();

            if (word == null) {
                return;
            }

            boolean isDup = checkDup(word.getWord().toLowerCase());
            if (isDup) {
                editViewer.printDup(word.getWord());
                return;
            }

            bw.newLine();
            bw.write(word.getWord() + "/" + word.getDef1() + "/" + word.getDef2());
            editViewer.addSuccess();
        } catch (IOException e) {
            System.out.println("EditController.add(): I/O Exception");
        }
    }

    // TODO: search and edit / delete the word
    public void edit() {
        String keyword = editViewer.editViewer();

        if (keyword.equals("C")) {
            editViewer.printCancelEdit();
        } else if (keyword.equals("A")) {
            final char delAllConsent = editViewer.promptDelAllConsent();

            if (delAllConsent == 'y') {
                emptyAllDb();

            } else {
                editViewer.printCancelEdit();
            }
        } else {
            ArrayList<Word> searchList = searchCon.searchWord(keyword);

            if (searchList.isEmpty()) {
                editViewer.promptNotFound(keyword);
            } else if (searchList.toArray().length == 1) {
                // edit or delete
                editOrDel(searchList.getFirst());
            } else {
                // TODO: Add indices to the words and let the user choose
                System.out.println("Multiple search results...");
            }
        }
    }

    private void editOrDel(Word word) {
        char foo = editViewer.editOrDel(word.getWord());

        if (foo == 'e') {
            System.out.println("edit");

            // TODO:
            // show the searched word's word, def1 and def2
            // prompt to change the definition
            // if input is empty keep the previous def
            // change the line with new entry

        } else if (foo == 'd') {
            ArrayList<Word> wordList = menuCon.getWordList();
            util.removeLine(word, wordList);
        }
    }

    private void emptyAllDb() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.WORD_FILE))) {
            bw.write("");
            editViewer.promptDelAllComplete();
            Thread.sleep(1500);

        } catch (InterruptedException e) {
            System.out.println("EditController.add(): InterruptedException");
        } catch (IOException e) {
            System.out.println("EditController.add(): I/O Exception");
        }
    }
}
