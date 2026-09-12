package com.unemotioned.englishtest.edit.viewer;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.vo.Word;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EditViewer {
    Scanner sc;

    public EditViewer() {
        sc = new Scanner(System.in);
    }

    public Word add() {
        Word newWord = new Word();

        System.out.println("\n===== Add new Word =====");
        System.out.println("Cancel (C)");

        System.out.print("\nEnter new word: ");
        String input = sc.next();
        if (input.equals("C")) {
            System.out.println("Cancel adding new word...\n");
            return null;
        }
        newWord.setWord(input);

        System.out.print("Definition (1/2): ");
        newWord.setDef1(sc.next());
        System.out.print("Definition (2/2): ");
        newWord.setDef2(sc.next());

        return newWord;
    }

    public void printDup(String word) {
        System.out.println("The word: " + word + " is already saved.");
    }

    public void addRes(boolean addRes) {
        if (addRes) {
            System.out.println("Add word: success!");
        } else {
            System.out.println("Add word: failed...");
        }
    }

    public void delAllRes(boolean delAllRes) {
        if (delAllRes) {
            System.out.println("Delete all words: success!");
        } else {
            System.out.println("Delete all words: failed...");
        }
    }

    public String editViewer() {
        System.out.println("\nSearch word to edit or delete.");
        System.out.print("Delete All / Cancel (A/C): ");

        return sc.next();
    }

    public void printCancelEdit() {
        System.out.println("Canceling edit...");
    }

    public char promptDelAllConsent() {
        System.out.println("Are you sure you want to delete all entries in " + Config.WORD_FILE + "?");
        System.out.print("No by default (y / N): ");

        char input;
        while (true) {
            try {
                input = sc.next().charAt(0);
                if (input == 'y' || input == 'N') {
                    break;
                } else {
                    System.out.print("Choose between y and N: ");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input character type.\n");
            }
        }

        return input;
    }

    public void promptDelAllComplete() {
        System.out.println(Config.WORD_FILE + " is empty.");
    }

    public void promptNotFound(String keyword) {
        System.out.println("Not found: " + keyword);
    }

    public char editOrDel(String word) {
        char input;

        System.out.println("\nEdit or Delete the word: " + word);
        while (true) {
            System.out.print("Edit / Delete / Cancel (e/d/C): ");
            try {
                input = sc.next().charAt(0);
                if (input == 'e' || input == 'd') {
                    break;
                } else if (input == 'C') {
                    System.out.println("Canceling edit...");
                    break;
                } else {
                    System.out.println("Please choose between e or d.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input character type.\n");
            }
        }
        return input;
    }

    public Word editWord(Word word) {
        System.out.println("\n===== Edit Word =====");
        System.out.println("Word: " + word.getWord());
        System.out.println("Definition 1: " + word.getDef1());
        System.out.println("Definition 2: " + word.getDef2());
        System.out.println("(Press enter key to skip)\n");

        sc.nextLine(); // consume input buffer after .nextLine()

        word.setWord(editOrSkip("Edit word: ", word.getWord()));
        word.setDef1(editOrSkip("Edit def1: ", word.getDef1()));
        word.setDef2(editOrSkip("Edit def2: ", word.getDef2()));

        return word;
    }

    private String editOrSkip(String prompt, String prev) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();

        if (input.isEmpty()) {
            input = prev;
        }

        return input;
    }

    // prompt what is changed to what
    public void editRes(boolean res) {
        if (res) {
            System.out.println("Edit word: Success!");
        } else {
            System.out.println("Edit word: Failed...");
        }
    }
}
