package com.unemotioned.englishtest.exam.viewer;

import com.unemotioned.englishtest.common.CommonViewer;
import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ExamViewer {
    Scanner sc;
    CommonViewer cViewer;

    public ExamViewer(Scanner scanner) {
        sc = scanner;
        cViewer = new CommonViewer();
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
                    cViewer.promptCancel("exam");
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
                sc.nextLine();

                if (numOfExam == 0) {
                    cViewer.promptCancel("exam");
                    break;
                } else if (numOfExam > max) {
                    System.out.println("Maximum number of tests possible: " + max);
                } else {
                    break;
                }

            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Please input integer type");
            }
        }

        return numOfExam;
    }

    public ArrayList<Word> engExam(ArrayList<Word> entries) {
        ArrayList<Word> failed = new ArrayList<>();

        System.out.println("You've selected word exam");
        System.out.println("Guess definition using word");

        for (int i = 0; i < entries.size(); i++) {
            Word word = entries.get(i);

            System.out.println("Word(" + (i + 1) + "): " + word.getWord());
            System.out.print("Guess one of definition: ");
            String guess = sc.nextLine().trim();

            if (guess.equals(word.getDef1()) || guess.equals(word.getDef2())) {
                System.out.println("Yay!!!");
            } else {
                System.out.println("Nay...");
                failed.add(entries.get(i));
            }
        }

        return failed;
    }

    public ArrayList<Word> korExam(ArrayList<Word> entries) {
        ArrayList<Word> failed = new ArrayList<>();

        System.out.println("You've selected definition exam");
        System.out.println("Guess word using definition");

        for (int i = 0; i < entries.size(); i++) {
            Word word = entries.get(i);

            System.out.println("Word(" + (i + 1) + "): " + word.getDef1() + ", " + word.getDef2());
            System.out.print("Guess word from definition: ");
            String guess = sc.nextLine().trim();

            if (word.getWord().equalsIgnoreCase(guess)) {
                System.out.println("Yay!!!");
            } else {
                System.out.println("Nay...");
                failed.add(entries.get(i));
            }
        }

        return failed;
    }

    public void showExamRes(int testSize, int failedSize) {
        int correct = testSize - failedSize;

        System.out.println("===== Exam results =====");
        if (failedSize == 0) {
            System.out.println("Perfect Score!!!");
            System.out.println(correct + "/" + testSize);
        } else {
            System.out.println("Correct: " + correct);
            System.out.println("Incorrect: " + failedSize);
        }

        String input;
        while (true) {
            System.out.println("Press enter to proceed");
            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                return;
            }
        }
    }

    // TODO: if selection is out of range
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
                assert inputs != null : "ExamViewer.showFailed().inputs is not null";
                if (inputs[0].equals("A")) {
                    int numSize = list.size() + 1;
                    numbers = new int[numSize];

                    for (int i = 0; i < numSize; i++) {
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

        System.out.println("Guess word using definition");

        for (int i = 0; i < list.size(); i++) {
            Word word = list.get(i);

            System.out.println("Word(" + (i + 1) + "): " + word.getDef1() + ", " + word.getDef2());
            System.out.print("Guess word from definition: ");
            String guess = sc.nextLine().trim();

            if (word.getWord().equalsIgnoreCase(guess)) {
                System.out.println("Yay!!!");
            } else {
                System.out.println("Nay...");
                results.add(list.get(i));
            }
        }
        return results;
    }

    public void emptyFile(String fileName) {
        System.out.println("File is empty: " + fileName);
    }
}
