package kr.or.iei.controller;

import kr.or.iei.common.Config;
import kr.or.iei.common.Util;
import kr.or.iei.model.vo.Word;
import kr.or.iei.viewer.Viewer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestController {
    Scanner sc;
    MenuController menuCon;
    Viewer viewer;

    Util util;
    ArrayList<Word> testList;
    ArrayList<Word> failList;

    public TestController(MenuController menuCon) {
        sc = new Scanner(System.in);

        this.menuCon = menuCon;
        this.viewer = new Viewer();

        util = new Util();
        testList = new ArrayList<>();
        failList = new ArrayList<>();
    }

    public void test() {
        ArrayList<Word> list = new ArrayList<>();

        System.out.println(failList);

        Random random = new Random();
        String selWord = viewer.startTest();
        int ranNum = viewer.random();
        int[] ran = new int[ranNum];

        for (int j = 0; j < ran.length; j++) {
            ran[j] = random.nextInt(list.size());
            for (int k = 0; k < j; k++) {
                if (ran[j] == ran[k]) {
                    j--;
                }
            }
        }

        for (int j = 0; j < ranNum; j++) {
            final String engSelected = "e";
            final String korSelected = "k";

            if (selWord.equalsIgnoreCase(engSelected)) {
                System.out.println(list.get(ran[j]).getDef1() + "\t" + list.get(ran[j]).getDef2());
                String answer = viewer.randomTest();

                if (!answer.equals(list.get(ran[j]).getWord())) {
                    testList.add(list.get(ran[j]));
                }

            } else if (selWord.equalsIgnoreCase(korSelected)) {
                System.out.println(list.get(ran[j]).getWord());
                String answer = viewer.randomTest();

                if (!answer.equals(list.get(ran[j]).getDef1())
                        || !answer.equals(list.get(ran[j]).getDef2())) {
                    testList.add(list.get(ran[j]));
                }
            } else {
                System.out.println("Error");
            }
        }
        BufferedWriter bw = null;

        try {
            FileWriter fw = new FileWriter(Config.FAILED_WORD_FILE, true);
            bw = new BufferedWriter(fw);

            for (int k = 0; k < testList.size(); k++) {
                for (int l = 0; l < failList.size(); l++) {
                    if (!failList.get(k).getWord().equals(testList.get(k).getWord())) {
                        bw.write(testList.get(k).getWord() + "/");
                        bw.write(testList.get(k).getDef1() + "/");
                        bw.write(testList.get(k).getDef2());
                        bw.newLine();
                    }
                }
            }
            failList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reTest() {
        failList = util.readFile(Config.FAILED_WORD_FILE);
    }
}
