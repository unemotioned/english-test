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

        ArrayList<Word> list = menuCon.getWordList();
        int numOfExam = examViewer.numOfExam(list.size());
        if (numOfExam == 0) {
            return;
        }

        list = getRandWords(numOfExam, list);
        Collections.shuffle(list);

        ArrayList<Word> results;
        if (examType == 'e') {
            results = examViewer.engExam(list);
        } else {
            results = examViewer.korExam(list);
        }

        examViewer.showExamRes(numOfExam, results.size());
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
        Random random = new Random();
        Set<Integer> set = new TreeSet<>();
        int[] numbers = new int[cnt];
        int numOfWords = list.size();

        do {
            set.add(random.nextInt(numOfWords));
        } while (set.size() < cnt);

        int index = 0;
        for (int n : set) {
            numbers[index] = n;
            index++;
        }

        // select words from that line
        ArrayList<Word> testList = new ArrayList<>();

        for (int num : numbers) {
            testList.add(list.get(num));
        }

        Collections.shuffle(testList);
        return testList;
    }

    public void showFailed() {
        List<Word> failedList = util.readFile(Config.FAILED_WORD_FILE);

        if (failedList.isEmpty()) {
            examViewer.emptyFile(Config.FAILED_WORD_FILE);
            return;
        }

        int index = 0;
        for (Word word : failedList) {
            index++;
            word.setIndex(index);
        }

        while (true) {
            int[] inputs = examViewer.showFailed(failedList);
            if (inputs.length == 1 && inputs[0] == 0) {
                break;
            }

            List<Word> selected = getSelected(inputs, failedList);
            examViewer.showFailedDef(selected);
        }
    }

    private List<Word> getSelected(int[] selections, List<Word> list) {
        List<Word> selected = new ArrayList<>();

        for (Word word : list) {
            for (int selection : selections) {
                if (word.getIndex() == selection) {
                    selected.add(word);
                }
            }
        }

        return selected;
    }

    public void makeup() {
        ArrayList<Word> failedList = util.readFile(Config.FAILED_WORD_FILE);
        if (failedList.isEmpty()) {
            examViewer.emptyFile(Config.FAILED_WORD_FILE);
            return;
        }

        int numOfExam = examViewer.numOfExam(failedList.size());
        if (numOfExam == 0) {
            return;
        }

        ArrayList<Word> testList = getRandWords(numOfExam, failedList);

        Collections.shuffle(testList);

        ArrayList<Word> correct = examViewer.makeupExam(testList);

        examViewer.showExamRes(numOfExam, numOfExam - correct.size());

        // remove elements without fear of ConcurrentModificationException
        failedList.removeIf(correct::contains);

        if (failedList.isEmpty()) {
            return;
        }

        boolean res = util.overwrite(Config.FAILED_WORD_FILE, failedList);
        examViewer.writeFailedRes(res);
    }
}
