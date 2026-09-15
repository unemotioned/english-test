package com.unemotioned.englishtest.exam.viewer;

import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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

    public int numOfExam(int max) {
        int numOfExam;
        System.out.println("Enter number of words to test 1 - " + max + " (0 to cancel)");
        while (true) {
            System.out.print("=> ");
            try {
                numOfExam = sc.nextInt();

                if (numOfExam == 0) {
                    System.out.println("Canceling Exam...\n");
                    break;
                } else if (numOfExam > max) {
                    System.out.println("Maximum number of tests possible: " + max);
                } else {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type.\n");
            }
        }

        return numOfExam;
    }

    // TODO: handle input error
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

    // TODO: handle input error
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

    public int[] showFailed(List<Word> list) {
        for (Word word : list) {
            System.out.println(word.getIndex() + ": " + word.getWord());
        }
        System.out.print("Select words to show definitions (separated by space): ");
        String[] inputs = sc.nextLine().split(" ");

        int[] numbers = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            numbers[i] = Integer.parseInt(inputs[i]);
        }

        return numbers;
    }

    public void showFailedDef(List<Word> list) {
        for (Word word : list) {
            System.out.println(word.getIndex() + ". " + word.getWord() + ": " + word.getDef1() + ", " + word.getDef2());
        }
    }

    // TODO: handle input error
    public ArrayList<Word> makeupExam(ArrayList<Word> list) {
        ArrayList<Word> results = new ArrayList<>();

        System.out.println("Guess word using definition.\n");

        int index = 1;
        for (Word word : list) {
            System.out.println("\nWord(" + index + "): " + word.getDef1() + ", " + word.getDef2());
            System.out.print("Definition: ");
            String wordGuess = sc.next();

            index--;
            if (wordGuess.equalsIgnoreCase(word.getWord())) {
                System.out.println("Yay!!!");
                results.add(word);
            } else {
                System.out.println("Nay...");
            }
            index = index + 2;
        }

        return results;
    }

    public void emptyFile(String fileName) {
        System.out.println("File is empty: " + fileName);
    }
}
