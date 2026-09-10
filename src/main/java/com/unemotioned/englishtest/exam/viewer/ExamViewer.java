package com.unemotioned.englishtest.exam.viewer;

import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExamViewer {
    Scanner sc;

    public ExamViewer() {
        sc = new Scanner(System.in);
    }

    public char examType() {
        System.out.println("\n===== Exam Start =====");
        System.out.println("Select Type of Exam");

        char examType;
        while (true) {
            try {
                System.out.print("English / Korean / Cancel (e/k/C): ");
                examType = sc.next().charAt(0);

                if (examType == 'e' || examType == 'k') {
                    break;
                } else if (examType == 'C') {
                    System.out.println("Canceling Exam...\n");
                    break;
                } else {
                    System.out.println("Please choose one of e or k.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input character type.\n");
            }
        }

        return examType;
    }

    public int numOfExam() {
        int numOfExam;
        while (true) {
            try {
                System.out.print("Enter number of words to test (0 to cancel): ");
                numOfExam = sc.nextInt();

                if (numOfExam == 0) {
                    System.out.println("Canceling Exam...\n");
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type.\n");
            }
        }

        return numOfExam;
    }

    public ArrayList<Integer> engExam(ArrayList<Word> list) {
        ArrayList<Integer> results = new ArrayList<>();

        System.out.println("You've selected word exam.");
        System.out.println("Guess definition using word.\n");

        int index = 1;
        for (Word word : list) {
            System.out.println("\nWord(" + index + "): " + word.getWord());
            System.out.print("Guess one of definition: ");
            String defGuess = sc.next();

            index--;
            if (defGuess.equals(word.getDef1()) || defGuess.equals(word.getDef2())) {
                System.out.println("Yay!!!");
                results.add(index);
            } else {
                System.out.println("Nay...");
            }
            index = index + 2;
        }

        return results;
    }

    public ArrayList<Integer> korExam(ArrayList<Word> list) {
        ArrayList<Integer> results = new ArrayList<>();

        System.out.println("You've selected definition exam.");
        System.out.println("Guess word using definition.\n");

        int index = 1;
        for (Word word : list) {
            System.out.println("\nWord(" + index + "): " + word.getDef1() + ", " + word.getDef2());
            System.out.print("Guess word from definition: ");
            String wordGuess = sc.next();

            index--;
            if (wordGuess.equalsIgnoreCase(word.getWord())) {
                System.out.println("Yay!!!");
                results.add(index);
            } else {
                System.out.println("Nay...");
            }
            index = index + 2;
        }

        return results;
    }

    public void printPerfect() {
        System.out.println("Perfect.");
    }
}
