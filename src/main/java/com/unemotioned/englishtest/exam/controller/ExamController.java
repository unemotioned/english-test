package com.unemotioned.englishtest.exam.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.exam.viewer.ExamViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;

import java.util.ArrayList;
import java.util.Scanner;

public class ExamController {
    Scanner sc;
    MenuController menuCon;
    ExamViewer examViewer;

    Util util;
    ArrayList<Word> testList;
    ArrayList<Word> failList;
    ArrayList<Word> list;

    public ExamController(MenuController menuCon) {
        sc = new Scanner(System.in);

        this.menuCon = menuCon;
        examViewer = new ExamViewer();

        util = new Util();
        testList = new ArrayList<>();
        failList = new ArrayList<>();
        list = new ArrayList<>();
    }

    public void exam() {
        char examType = examViewer.examType();
        int numOfExam = examViewer.numOfExam();

        list = getRandWords(numOfExam);

        if (examType == 'e') {
            System.out.println("You've selected word exam.");
            engExam();
        } else {
            System.out.println("You've selected definition exam.");
            korExam();
        }
    }

    private ArrayList<Word> getRandWords(int cnt) {
        return null;
    }

    private void engExam() {
        System.out.println("Guess definition using word.");
    }

    private void korExam() {
        System.out.println("Guess word using definition.");
    }

    public void makeup() {
        failList = util.readFile(Config.FAILED_WORD_FILE);
    }
}
