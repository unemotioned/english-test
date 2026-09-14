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
        boolean isLastLineEmpty = emptyLastLine(fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            if (!isLastLineEmpty) {
                bw.newLine();
            }
            bw.write(wordToString(word));
            return true;
        } catch (IOException e) {
            System.out.println("Util.appendToFile(): IOException");

            return false;
        }
    }

    public void appendToFile(ArrayList<Word> entries, String fileName) {
        boolean isLastLineEmpty = emptyLastLine(fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Word word : entries) {
                if (isLastLineEmpty) {
                    isLastLineEmpty = false;
                } else {
                    bw.newLine();
                }
                bw.write(wordToString(word));
            }
        } catch (IOException e) {
            System.out.println("Util.appendToFile(): IOException");
        }
    }

    private boolean emptyLastLine(String fileName) {
        boolean isPrevLineEmpty = false;
        String lastLine = null;

        try (LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
            while (lnr.readLine() != null) {
                lastLine = lnr.readLine();
            }

            if (lastLine == null) {
                isPrevLineEmpty = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Util.emptyLastLine(): FileNotFound");
        } catch (IOException e) {
            System.out.println("Util.emptyLastLine(): IOException");
        }

        return isPrevLineEmpty;
    }

    public boolean overwrite(String fileName, ArrayList<Word> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            if (list == null) {
                bw.write("");
                return true;
            }

            for (Word word : list) {
                bw.write(wordToString(word));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Util.overwrite(): IOException");
            return false;
        }
    }

    private String wordToString(Word word) {
        return word.getWord() + "/" + word.getDef1() + "/" + word.getDef2();
    }
}
