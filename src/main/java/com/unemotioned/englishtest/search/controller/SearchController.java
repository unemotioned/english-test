package com.unemotioned.englishtest.search.controller;

import com.unemotioned.englishtest.common.CommonViewer;
import com.unemotioned.englishtest.common.vo.Word;
import com.unemotioned.englishtest.menu.controller.MenuController;
import com.unemotioned.englishtest.search.viewer.SearchViewer;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchController {
    MenuController menuCon;
    SearchViewer sView;
    CommonViewer cView;

    public SearchController(MenuController menuCon, Scanner scanner) {
        this.menuCon = menuCon;
        sView = new SearchViewer(scanner);
        cView = new CommonViewer();
    }

    public void search() {
        String key;
        ArrayList<Word> list;

        while (true) {
            key = sView.searchPrompt();

            if (key.isBlank()) {
                continue;
            } else if (key.equals("C")) {
                cView.promptCancel("search");
                break;
            }

            if (key.charAt(0) >= 'A' && key.charAt(0) <= 'z') {
                list = searchWord(key);
            } else {
                list = searchDef(key);
            }

            if (list.isEmpty()) {
                sView.noSearchResults(key);
            } else if (list.size() == 1) {
                sView.searchResHeader();
                sView.searchRes(list.getFirst());
            } else {
                int index = sView.chooseWord(list);

                if (index > 0) {
                    sView.searchRes(list.get(--index));
                } else {
                    return;
                }
            }
        }
    }

    public ArrayList<Word> searchWord(String key) {
        ArrayList<Word> res = new ArrayList<>();
        int i = 1;

        for (Word w : menuCon.getWordList()) {
            String s = w.getWord();

            if (s.toLowerCase().contains(key.toLowerCase())) {
                w.setIndex(i++);
                res.add(w);
            }
        }

        return res;
    }

    private ArrayList<Word> searchDef(String key) {
        ArrayList<Word> res = new ArrayList<>();
        int i = 1;

        for (Word w : menuCon.getWordList()) {
            String def1 = w.getDef1();
            String def2 = w.getDef2();

            if (def1.toLowerCase().contains(key)) {
                w.setIndex(i++);
                res.add(w);
                continue;
            }

            if (def2.toLowerCase().contains(key)) {
                w.setIndex(i++);
                res.add(w);
            }
        }

        return res;
    }
}
