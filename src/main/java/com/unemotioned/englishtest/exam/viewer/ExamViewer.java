package com.unemotioned.englishtest.exam.viewer;

import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

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

    public ArrayList<Word> engExam(ArrayList<Word> list) {
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
            System.out.println("index at the final: " + index);
        }

        if (results.toArray().length == 0) {
            System.out.println("Perfect.");
            return null;
        }

        // reverse the array to remove words from list backwards to not mess up the index
        Stack<Integer> stack = new Stack<>();
        stack.addAll(results);

        while (!stack.isEmpty()) {
            System.out.println("bar");
            int anotherIndex = stack.pop();
            list.remove(anotherIndex);
        }

        return list;
    }

    public ArrayList<Word> korExam(ArrayList<Word> list) {
        System.out.println("You've selected definition exam.");
        System.out.println("Guess word using definition.\n");

        for (Word word : list) {
            int i = 0;
            System.out.println("word(" + ++i + "): " + word.getWord());
        }

        return list;
    }
}
