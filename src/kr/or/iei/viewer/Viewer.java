package kr.or.iei.viewer;

import kr.or.iei.model.vo.Word;

import java.util.ArrayList;
import java.util.Scanner;

public class Viewer {
    Scanner sc;

    public Viewer() {
        sc = new Scanner(System.in);
    }

    public String newWord() {
        System.out.print("New Word: ");
        return sc.next();
    }

    public void dupWord() {
        System.out.println("Duplicated Word.");
    }

    public Word add() {
        Word newWord = new Word();

        System.out.println();
        System.out.print("Enter new word: ");
        newWord.setWord(sc.next());
        System.out.print("Definition (1/2): ");
        newWord.setDef1(sc.next());
        System.out.print("Definition (2/2): ");
        newWord.setDef2(sc.next());

        return newWord;
    }

    public void addSuccess() {
        System.out.println("New word added successfully!");
    }

    public String editViewer() {
        System.out.println("Search word to edit / delete");
        System.out.println("Delete all / Cancel (a/c)");
        System.out.print("=> ");
        return sc.next();
    }

    public String startTest() {
        System.out.print("Select to English or Korean (e/k): ");
        return sc.next();
    }

    public int random() {
        System.out.print("Please enter the number of tests : ");
        return sc.nextInt();
    }

    public String randomTest() {
        System.out.print("Answer : ");
        return sc.next();
    }

    public char editOrDelete() {
        return sc.next().charAt(0);
    }

    public int searchView(ArrayList<Word> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%2d %-13s\t", i + 1, list.get(i).getWord());
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.print("\nChoose index : ");
        return sc.nextInt();
    }

    public void showChosenIndex(int chosenIndex, ArrayList<Word> list) {
        System.out.print(list.get(chosenIndex).toString());
        System.out.println();
    }
}
