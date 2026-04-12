package kr.or.iei.controller;

import kr.or.iei.common.Config;
import kr.or.iei.model.vo.Word;
import kr.or.iei.viewer.Viewer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditController {
    Scanner sc;
    MenuController menuCon;
    Viewer viewer;

    public EditController(MenuController menuCon) {
        sc = new Scanner(System.in);
        this.menuCon = menuCon;
        this.viewer = new Viewer();
    }

    public void newWord() {
        String newWord = viewer.newWord();

        for (Word word : menuCon.getWordList()) {
            if (word.getWord().equalsIgnoreCase(newWord)) {
                viewer.dupWord();
                return;
            } else {
                // TODO: get two different definitions
            }
        }
    }

    // WARN: added word might be duplicated
    public void add() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.WORD_FILE, true))) {
            Word word = viewer.add();
            bw.newLine();
            bw.write(word.getWord() + "/" + word.getDef1() + "/" + word.getDef2());
            viewer.addSuccess();
        } catch (IOException e) {
            System.out.println("I/O Error");
        }
    }

    public void edit() {
        String editWord = viewer.editViewer();
        boolean found = false;

        if (editWord.equalsIgnoreCase("c")) {
            System.out.println("Canceling Search");
        } else if (editWord.equalsIgnoreCase("a")) {
            System.out.println("Are you sure you want to delete all?");
            System.out.println("y / s");
            char yesOrNo = sc.next().charAt(0);

            final char yesSelected = 'y';
            if (yesOrNo == yesSelected) {
                // TODO: confirm editing word
            } else {
                System.out.println("Canceling ...");
            }

        } else {
            for (Word word : menuCon.getWordList()) {
                if (word.getWord().equalsIgnoreCase(editWord)) {
                    System.out.println(word);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No such word");
            } else {
                // char editOrDelete = view.editOrDelete();
            }
        }
    }
}
