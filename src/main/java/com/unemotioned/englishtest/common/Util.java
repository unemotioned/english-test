package com.unemotioned.englishtest.common;

import com.unemotioned.englishtest.common.vo.MenuOpt;
import com.unemotioned.englishtest.common.vo.Word;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class Util {

    public int[] menuMinMax() {
        int[] minMax = new int[2];
        MenuOpt[] opt = MenuOpt.values();

        MenuOpt min = opt[0];
        MenuOpt max = opt[opt.length - 1];

        minMax[0] = min.ordinal();
        minMax[1] = max.ordinal();

        return minMax;
    }

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
        boolean isLastLineEmpty = checkLastLine(fileName);

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
        boolean isLastLineEmpty = checkLastLine(fileName);

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

    private boolean checkLastLine(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return true;
        }

        // read any position of file without iterating
        // works in byte level
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long length = raf.length();
            if (length == 0) return true;

            // read last 2 bytes
            long pos = Math.max(0, length - 2);
            raf.seek(pos);

            byte[] lastBytes = new byte[(int) (length - pos)];
            raf.readFully(lastBytes);

            // check for LF or CRLF
            for (int i = lastBytes.length - 1; i >= 0; i--) {
                byte b = lastBytes[i];
                if (b == '\n' || b == '\r') {
                    return true;
                }
                if (b != ' ' && b != '\t') {
                    break;
                }
            }
            return false;

        } catch (IOException e) {
            System.out.println("Util.checkLastLine(): IOException");
            return false;
        }
    }

    public boolean overwrite(String fileName, ArrayList<Word> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            if (list == null) {
                bw.write("");
                return true;
            }

            int size = list.size();
            for (int i = 0; i < size; i++) {
                bw.write(wordToString(list.get(i)));

                if (i != size - 1) {
                    bw.newLine();
                }
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
