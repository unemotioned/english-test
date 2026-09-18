package com.unemotioned.englishtest.edit.viewer;

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
        String input = sc.nextLine().trim();
        if (input.equals("C")) {
            System.out.println("Cancel adding new word...\n");
            return null;
        }
        newWord.setWord(input);

        System.out.print("Definition (1/2): ");
        newWord.setDef1(sc.nextLine().trim());
        System.out.print("Definition (2/2): ");
        newWord.setDef2(sc.nextLine().trim());

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

    public String editViewer() {
        System.out.print("\nSearch word to edit or delete ([C]ancel): ");
        return sc.next();
    }

    public void printCancelEdit() {
        System.out.println("Canceling edit...");
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

    public boolean delWordConsent() {
        sc.nextLine();
        System.out.print("Are you sure? (y/N): ");
        while (true) {
            try {
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("y")) {
                    return true;
                } else if (input.isEmpty() || input.equals("N")) {
                    return false;
                } else {
                    System.out.println("Please choose between Yes or No: ");
                }

            } catch (InputMismatchException e) {
                System.out.println("EditViewer.delWordConsent(): InputMismatchException");
            }
        }
    }

    public String selFile2Nuke(String[] files) {
        String selFile = "";
        int input;
        int len = files.length;

        System.out.println("Select file to delete it's contents (0 to cancel)");
        for (int i = 0; i < len; i++) {
            System.out.println(i + 1 + ": " + files[i]);
        }

        while (true) {
            System.out.print("=> ");
            try {
                input = sc.nextInt();

                if (input == 0) {
                    System.out.println("Canceling Nuke...");
                } else if (input <= len) {
                    selFile = files[input - 1];
                } else {
                    System.out.println("Please select between 1-" + len);
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("EditViewer.SelFile2Nuke(): InputMismatchException");
            }
        }

        return selFile;
    }

    public boolean confirmNuke(String fileName) {
        boolean confirmation = false;
        sc.nextLine();
        String input;

        System.out.println("Confirm nuking: " + fileName);
        while (true) {
            try {
                System.out.print("y/N: ");
                input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("y")) {
                    confirmation = true;
                    System.out.println("Nuclear launch detected");
                } else if (input.isEmpty() || input.equals("N")) {
                    System.out.println("Canceling nuke...");
                } else {
                    System.out.println("Choose between Yes or No");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("EditViewer.confirmNuke(): InputMismatchException");
            }
        }

        return confirmation;
    }
}
