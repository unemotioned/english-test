package com.unemotioned.englishtest.common;

import com.unemotioned.englishtest.common.vo.Word;

import java.io.*;
import java.util.ArrayList;

public class Util {

    public ArrayList<Word> readFile(String fileName) {
        ArrayList<Word> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                // split line by slash
                // put it into String arr
                // construct Word object with it
                // put Word obj into ArrayList
                String[] wordArr = line.split("/");
                list.add(new Word(wordArr[0], wordArr[1], wordArr[2], 0));
            }

        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found.");
        } catch (IOException e) {
            System.out.println("Util.readFile(): I/O Exception");
        }
        return list;
    }

    // TODO: consider counting lines from readFile()
    public int countWordEntries() {
        String fName = Config.WORD_FILE;
        File file = new File(fName);

        int lines = -1;
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
            while (lnr.readLine() != null) {
                lines++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(fName + " not found.");
        } catch (IOException e) {
            System.out.println("Util.countWordEntries(): I/O Exception");
        }

        return lines;
    }

    public boolean emptyLastLine() {
        String failDb = Config.FAILED_WORD_FILE;
        boolean isPrevLineEmpty = false;
        String lastLine = null;

        try (LineNumberReader lnr = new LineNumberReader(new FileReader(failDb))) {
            while (lnr.readLine() != null) {
                lastLine = lnr.readLine();
            }

            if (lastLine == null) {
                isPrevLineEmpty = true;
            }

        } catch (IOException e) {
            System.out.println("ExamController.emptyPrevLine(): I/O Exception");
        }

        return isPrevLineEmpty;
    }

    public void clearTerminal() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Util.clearTerminal() failed." + e);
        }
    }

    public void removeLine(Word word, ArrayList<Word> list) {
        list.remove(word);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.WORD_FILE))) {

            for (Word entry : list) {
                bw.write(entry.getWord() + "/" + entry.getDef1() + "/" + entry.getDef2());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Util.removeLine(): IOException");
        }
    }
}
