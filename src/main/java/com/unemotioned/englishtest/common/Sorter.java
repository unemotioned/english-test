package com.unemotioned.englishtest.common;

import com.unemotioned.englishtest.common.vo.Word;

import java.util.ArrayList;
import java.util.Random;

public class Sorter {
    ArrayList<Word> entries;
    Util util;
    String file;
    Random rand;

    public Sorter(String file) {
        this.file = file;
        util = new Util();
        entries = util.readFile(file);
        rand = new Random();
    }

    public void sort() {
        quickSort(entries, 0, entries.size() - 1);
    }

    public void quickSort(ArrayList<Word> entries, int low, int high) {
        if (low >= high) {
            return;
        }

        int pivotIndex = low + rand.nextInt(high - low + 1);

        // move pivot to the end
        swap(entries, pivotIndex, high);

        // Partition and get final pivot position
        int pivotPos = partition(entries, low, high);

        // sort left
        quickSort(entries, low, pivotPos - 1);
        // sort right
        quickSort(entries, pivotPos + 1, high);

        // overwrite
        util.overwrite(file, entries);
    }

    private int partition(ArrayList<Word> entries, int low, int high) {
        String pivot = entries.get(high).getWord();

        int i = low;

        for (int j = low; j < high; j++) {
            String current = entries.get(j).getWord();

            if (current.compareTo(pivot) <= 0) {
                swap(entries, i, j);
                i++;
            }
        }

        // put pivot at the last position
        swap(entries, i, high);

        return i;
    }

    private void swap(ArrayList<Word> entries, int j, int k) {
        Word temp = entries.get(j);
        entries.set(j, entries.get(k));
        entries.set(k, temp);
    }
}
