package kr.or.iei.model.vo;

public class Word {
    private String word;
    private String def1;
    private String def2;
    private int index;

    public Word() {
        super();
    }

    public Word(String word, String def1, String def2) {
        this.word = word;
        this.def1 = def1;
        this.def2 = def2;
    }

    public Word(String word, String def1, String def2, int index) {
        this.word = word;
        this.def1 = def1;
        this.def2 = def2;
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return word + "\t" + def1 + "\t" + def2;
    }
}
