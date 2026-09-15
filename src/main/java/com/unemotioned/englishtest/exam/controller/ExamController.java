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
        ArrayList<Integer> results;

        if (examType == 'e') {
            results = examViewer.engExam(list);
        } else {
            results = examViewer.korExam(list);
        }

        ArrayList<Word> failedList = createFailedList(results, list);
        if (failedList == null) {
            return;
        }

        writeToFailDb(failedList);
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

        for (Word word : failedList) {
            if (prevFailed.contains(word)) {
                failedList.remove(word);
            }
        }

        if (!failedList.isEmpty()) {
            util.appendToFile(failedList, Config.FAILED_WORD_FILE);
        }
    }

    private ArrayList<Word> createFailedList(ArrayList<Integer> results, ArrayList<Word> list) {
        if (results.toArray().length == list.toArray().length) {
            examViewer.printPerfect();
            return null;
        }

        // reverse the array to remove words from list backwards to not mess up the index
        Stack<Integer> stack = new Stack<>();
        stack.addAll(results);

        while (!stack.isEmpty()) {
            int anotherIndex = stack.pop();
            list.remove(anotherIndex);
        }

        return list;
    }

    private ArrayList<Word> getRandWords(int cnt) {
        Random random = new Random();
        Set<Integer> set = new TreeSet<>();
        int[] numbers = new int[cnt];
        int numOfWords = menuCon.getWordList().toArray().length;

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

    // TODO: if none is selected
    public void showFailed() {
        List<Word> failedList = util.readFile(Config.FAILED_WORD_FILE);

        int index = 0;
        for (Word word : failedList) {
            index++;
            word.setIndex(index);
        }

        int[] inputs = examViewer.showFailed(failedList);
        List<Word> selected = getSelected(inputs, failedList);

        examViewer.showFailedDef(selected);
    }

    private List<Word> getSelected(int[] selections, List<Word> list) {
        List<Word> selected = new ArrayList<>();

        for (int j = 0; j < list.toArray().length; j++) {
            for (int selection : selections) {
                if (list.get(j).getIndex() == selection) {
                    selected.add(list.get(j));
                }
            }
        }

        return selected;
    }

    public void makeup() {
        ArrayList<Word> failList = util.readFile(Config.FAILED_WORD_FILE);

        for (Word word : failList) {
            int i = 0;
            System.out.println("word(" + ++i + "): " + word.getWord());
        }
    }
}
