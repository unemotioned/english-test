package com.unemotioned.englishtest.exam.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.exam.viewer.ExamViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ExamController {
    ExamViewer examViewer;
    MenuController menuCon;
    Util util;

    public ExamController(MenuController menuCon) {
        examViewer = new ExamViewer();
        this.menuCon = menuCon;
        util = new Util();
    }

    public void exam() {
        char examType = examViewer.examType();
        if (examType == 'C') {
            return;
        }

        int numOfExam = examViewer.numOfExam();
        if (numOfExam == 0) {
            return;
        }

        ArrayList<Word> list = getRandWords(numOfExam);
        ArrayList<Word> failedList;
        assert list != null : "ExamController.list must not be null!";

        if (examType == 'e') {
            failedList = examViewer.engExam(list);
        } else {
            failedList = examViewer.korExam(list);
        }

        if (failedList == null) {
            return;
        }

        // TODO: write failedList words to failDB.txt
        int i = 0;
        for (Word word : failedList) {
            System.out.println("word(" + i + "): " + word.getWord());
        }
    }

    private ArrayList<Word> getRandWords(int cnt) {
        int numOfWords = util.countWordEntries();
        if (numOfWords == -1) {
            return null;
        }

        // use numOfWords for random number range
        Random random = new Random();
        Set<Integer> set = new TreeSet<>();
        int[] numbers = new int[cnt];

        for (int i = 0; i < cnt; i++) {
            set.add(random.nextInt(numOfWords));
        }

        int i = 0;
        for (int n : set) {
            numbers[i++] = n;
        }

        // select words from that line
        ArrayList<Word> wordList = menuCon.getWordList();
        ArrayList<Word> testList = new ArrayList<>();

        for (int num : numbers) {
            testList.add(wordList.get(num));
        }

        return testList;
    }

    public void makeup() {
        ArrayList<Word> failList = util.readFile(Config.FAILED_WORD_FILE);

        for (Word word : failList) {
            int i = 0;
            System.out.println("word(" + ++i + "): " + word.getWord());
        }
    }
}
