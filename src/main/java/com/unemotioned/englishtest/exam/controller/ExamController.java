package com.unemotioned.englishtest.exam.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.exam.viewer.ExamViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;

import java.util.*;

public class ExamController {
    Scanner sc;

    MenuController menuCon;
    ExamViewer examViewer;
    Util util;

    public ExamController(MenuController menuCon) {
        sc = new Scanner(System.in);

        this.menuCon = menuCon;
        examViewer = new ExamViewer();
        util = new Util();
    }

    public void exam() {
        char examType = examViewer.examType();
        int numOfExam = examViewer.numOfExam();

        ArrayList<Word> list = getRandWords(numOfExam);
        assert list != null : "ExamController.list must not be null!";
        for (Word word : list) {
            int i = 0;
            System.out.println("word(" + ++i + "): " + word.getWord());
        }

        if (examType == 'e') {
            System.out.println("You've selected word exam.");
            engExam();
        } else {
            System.out.println("You've selected definition exam.");
            korExam();
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

    private void engExam() {
        System.out.println("Guess definition using word.");
    }

    private void korExam() {
        System.out.println("Guess word using definition.");
    }

    public void makeup() {
        ArrayList<Word> failList = util.readFile(Config.FAILED_WORD_FILE);

        for (Word word : failList) {
            int i = 0;
            System.out.println("word(" + ++i  + "): "+ word.getWord());
        }
    }
}
