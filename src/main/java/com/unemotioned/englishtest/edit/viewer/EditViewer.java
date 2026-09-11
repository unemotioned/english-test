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

    public void addSuccess() {
        System.out.println("New word added successfully!");
    }

    public String editViewer() {
        System.out.println("Search word to edit or delete");
        System.out.print("or delete All / Cancel (A/C): ");

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

        System.out.println("\nEdit or Delete the word: " + word + "?");
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
}
