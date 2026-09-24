package com.unemotioned.englishtest.common;

import com.unemotioned.englishtest.common.vo.Word;
import java.io.File;
import java.util.ArrayList;

public class FileInitializer {
    File allFile;
    File failFile;
    Util util;

    public FileInitializer() {
        allFile = new File(Config.WORD_FILE);
        failFile = new File(Config.FAILED_WORD_FILE);
        util = new Util();
    }

    public void init() {
        if (!allFile.isFile()) {
            ArrayList<Word> bakFile = util.readFile(Config.BAK);
            util.createFile(Config.WORD_FILE);
            util.overwrite(Config.WORD_FILE, bakFile);
        }

        if (!failFile.isFile()) {
            util.createFile(Config.FAILED_WORD_FILE);
        }
    }
}
