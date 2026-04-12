package kr.or.iei.common;

import kr.or.iei.model.vo.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Util {
    public ArrayList<Word> readFile(String fileName) {
        ArrayList<Word> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                // split line by slash
                // put it into String arr
                // construct Word object with it
                // put Word obj into ArrayList
                String[] wordArr = line.split("/");
                list.add(new Word(wordArr[0], wordArr[1], wordArr[2]));
            }

        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found.");
        } catch (IOException e) {
            System.out.println("I/O Error");
        }
        return list;
    }
}
