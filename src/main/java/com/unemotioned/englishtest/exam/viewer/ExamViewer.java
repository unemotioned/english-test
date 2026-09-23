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
        System.out.println("===== Exam Start =====");
        System.out.println("Select Type of Exam");

        char examType;
        while (true) {
            try {
                System.out.print("English / Korean / Cancel (e/k/C): ");
                examType = sc.next().charAt(0);

                if (examType == 'e' || examType == 'k') {
                    break;
                } else if (examType == 'C') {
                    System.out.println("Canceling Exam...");
                    break;
                } else {
                    System.out.println("Please choose one of e or k");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input character type");
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
                    System.out.println("Canceling Exam...");
                    break;
                } else if (numOfExam > max) {
                    System.out.println("Maximum number of tests possible: " + max);
                } else {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please input integer type");
            }
        }

        return numOfExam;
    }

    public ArrayList<Integer> engExam(ArrayList<Word> list) {
        sc.nextLine();

        ArrayList<Integer> results = new ArrayList<>();

        System.out.println("You've selected word exam");
        System.out.println("Guess definition using word");

        int index = 1;
        for (Word word : list) {
            System.out.println("Word(" + index + "): " + word.getWord());
            System.out.print("Guess one of definition: ");
            String defGuess = sc.nextLine().trim();

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

        System.out.println("You've selected definition exam");
        System.out.println("Guess word using definition");

        int index = 1;
        for (Word word : list) {
            System.out.println("Word(" + index + "): " + word.getDef1() + ", " + word.getDef2());
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
        System.out.println("Perfect");
    }

    public int[] showFailed(List<Word> list) {
        System.out.println("===== Show Failed =====");
        for (Word word : list) {
            System.out.println(word.getIndex() + ": " + word.getWord());
        }

        String[] inputs = null;
        int[] numbers;

        while (true) {
            System.out.print("Select words to show definitions (separated by space. `0` to cancel. `A` to show all): ");

            try {
                // replace more than one space into singe space character
                inputs = sc.nextLine().trim().replaceAll("\\s+", " ").split(" ");

                numbers = new int[inputs.length];
                for (int i = 0; i < inputs.length; i++) {
                    numbers[i] = Integer.parseInt(inputs[i]);
                }

                break;
            } catch (NumberFormatException e) {

                assert inputs != null : "ExamViewer.showFailed().String[] inputs is not null";
                if (inputs[0].equals("A")) {
                    numbers = new int[list.toArray().length];
                    for (int i = 0; i < list.toArray().length + 1; i++) {
                        numbers[i] = i;
                    }

                    return numbers;
                } else {
                    System.out.println("Please input integer type");
                }
            }
        }

        return numbers;
    }

    public void showFailedDef(List<Word> list) {
        System.out.println("===== Definitions =====");
        for (Word word : list) {
            System.out.println(word.getIndex() + ". " + word.getWord() + ": " + word.getDef1() + ", " + word.getDef2());
        }
    }

    public ArrayList<Word> makeupExam(ArrayList<Word> list) {
        ArrayList<Word> results = new ArrayList<>();

        sc.nextLine();
        System.out.println("Guess word using definition");

        int index = 1;
        for (Word word : list) {
            System.out.println("Word(" + index + "): " + word.getDef1() + ", " + word.getDef2());
            System.out.print("Definition: ");
            String wordGuess = sc.nextLine().trim();

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
