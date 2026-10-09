package com.unemotioned.englishtest.exam.controller;

import com.unemotioned.englishtest.common.Config;
import com.unemotioned.englishtest.common.Util;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.exam.viewer.ExamViewer;
import com.unemotioned.englishtest.menu.controller.MenuController;
import java.io.File;
import java.util.*;

public class ExamController {
    ExamViewer examViewer;
    MenuController menuCon;
    Util util;

    public ExamController(MenuController menuCon, Scanner scanner) {
        examViewer = new ExamViewer(scanner);
        this.menuCon = menuCon;
        util = new Util();
    }

    public void exam() {
        char examType = examViewer.examType();
        if (examType == 'C') {
            return;
        }

        ArrayList<Word> entries = menuCon.getWordList();
        int num = examViewer.numOfExam(entries.size());
        if (num == 0) {
            return;
        }

        ArrayList<Word> list = getRandWords(num, entries);
        Collections.shuffle(list);

        ArrayList<Word> results;
        if (examType == 'e') {
            results = examViewer.engExam(list);
        } else {
            results = examViewer.korExam(list);
        }

        examViewer.showExamRes(num, results.size());
        if (results.isEmpty()) {
            return;
        }

        writeToFailDb(results);
    }

    private void writeToFailDb(ArrayList<Word> failedList) {
        final String fileName = Config.FAILED_WORD_FILE;
        File failedFile = new File(fileName);
        ArrayList<Word> prevFailed = new ArrayList<>();

        if (failedFile.isFile()) {
            prevFailed = util.readFile(fileName);
        } else {
            util.createFile(fileName);
        }

        failedList.removeIf(prevFailed::contains);

        if (!failedList.isEmpty()) {
            boolean res = util.appendToFile(failedList, Config.FAILED_WORD_FILE);
            examViewer.writeFailedRes(res);
        }
    }

    private ArrayList<Word> getRandWords(int cnt, ArrayList<Word> list) {
        Random rand = new Random();
        Set<Integer> set = new TreeSet<>();
        int len = list.size();

        do {
            set.add(rand.nextInt(len));
        } while (set.size() < cnt);

        int i = 0;
        int[] nums = new int[cnt];
        for (int n : set) {
            nums[i] = n;
            i++;
        }

        ArrayList<Word> testList = new ArrayList<>();
        for (int num : nums) {
            testList.add(list.get(num));
        }

        Collections.shuffle(testList);

        return testList;
    }

    public void showFailed() {
        ArrayList<Word> entries = util.readFile(Config.FAILED_WORD_FILE);

        if (entries.isEmpty()) {
            examViewer.promptEmpty(Config.FAILED_WORD_FILE);
            return;
        }

        int i = 0;
        for (Word w : entries) {
            i++;
            w.setIndex(i);
        }

        while (true) {
            int[] inputs = examViewer.showFailed(entries);
            if (inputs.length == 1 && inputs[0] == 0) {
                break;
            }

            ArrayList<Word> sel = getSelected(inputs, entries);
            examViewer.showFailedDef(sel);
        }
    }

    private ArrayList<Word> getSelected(int[] selections, ArrayList<Word> list) {
        ArrayList<Word> res = new ArrayList<>();

        for (Word w : list) {
            for (int sel : selections) {
                if (w.getIndex() == sel) {
                    res.add(w);
                }
            }
        }

        return res;
    }

    public void makeup() {
        ArrayList<Word> entries = util.readFile(Config.FAILED_WORD_FILE);
        if (entries.isEmpty()) {
            examViewer.promptEmpty(Config.FAILED_WORD_FILE);
            return;
        }

        int num = examViewer.numOfExam(entries.size());
        if (num == 0) {
            return;
        }

        ArrayList<Word> list = getRandWords(num, entries);

        Collections.shuffle(list);

        ArrayList<Word> correct = examViewer.makeupExam(list);

        examViewer.showExamRes(num, num - correct.size());

        // remove elements without fear of ConcurrentModificationException
        entries.removeIf(correct::contains);

        if (entries.isEmpty()) {
            return;
        }

        boolean res = util.overwrite(Config.FAILED_WORD_FILE, entries);
        examViewer.writeFailedRes(res);
    }
}
