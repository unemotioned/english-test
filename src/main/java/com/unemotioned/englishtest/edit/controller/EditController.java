package com.unemotioned.englishtest.edit.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.edit.viewer.EditViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditController {
    Scanner sc;
    MenuController menuCon;
    EditViewer editViewer;

    public EditController(MenuController menuCon) {
        sc = new Scanner(System.in);
        this.menuCon = menuCon;
        editViewer = new EditViewer();
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
        char editWord = editViewer.editViewer();

        if (editWord == 'C') {
            editViewer.printCancelEdit();
            return;
        }

        if (editWord == 'A') {
            final char delAllConsent = editViewer.promptDelAllConsent();

            if (delAllConsent == 'y') {
                emptyAllDb();

            } else {
                editViewer.printCancelEdit();
            }
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
