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
            System.out.println("Util.readFile(): IOException");
        }
        return list;
    }

    public void createFile(String fileName) {
        File failedFile = new File(fileName);

        try {
            if (failedFile.createNewFile()) {
                System.out.println("File created: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Util.createFile(): IOException");
        }
    }

    public boolean appendToFile(Word word, String fileName) {
        String entry = word.getWord() + "/" + word.getDef1() + "/" + word.getDef2();
        boolean isLastLineEmpty = emptyLastLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            if (isLastLineEmpty) {
                isLastLineEmpty = false;
            } else {
                bw.newLine();
            }
            bw.write(entry);

            return true;
        } catch (IOException e) {
            System.out.println("Util.appendToFile(): IOException");

            return false;
        }
    }

    public void appendToFile(ArrayList<Word> entries, String fileName) {
        boolean isLastLineEmpty = emptyLastLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Word word : entries) {
                String entry = word.getWord() + "/" + word.getDef1() + "/" + word.getDef2();

                if (isLastLineEmpty) {
                    isLastLineEmpty = false;
                } else {
                    bw.newLine();
                }
                bw.write(entry);
            }
        } catch (IOException e) {
            System.out.println("Util.appendToFile(): IOException");
        }
    }

    public boolean emptyAllDb() {
        String fname = Config.WORD_FILE;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fname))) {
            bw.write("");
            return true;
        } catch (IOException e) {
            System.out.println("Util.emptyAllDb(): IOException");
        }
        return false;
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
            System.out.println("Util.countWordEntries(): IOException");
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
            System.out.println("ExamController.emptyPrevLine(): IOException");
        }

        return isPrevLineEmpty;
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

    public boolean overwrite(String fileName, ArrayList<Word> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (Word word : list) {
                String entry = word.getWord() + "/" + word.getDef1() + "/" + word.getDef2();
                bw.write(entry);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Util.overwrite(): IOException");
            return false;
        }
    }
}
