package com.unemotioned.englishtest.exam.viewer;

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
                System.out.print("English / Korean (e/k): ");

                examType = sc.next().charAt(0);
                if (examType == 'e' || examType == 'k') {
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
                System.out.print("Enter number of words to test: ");
                numOfExam = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please input integer type.\n");
            }
        }

        return numOfExam;
    }
}
