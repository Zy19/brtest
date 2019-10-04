package com.br;


public class WordGeneratorWrapper {

    public static void main(String[] args) {
        // 象形字
        // éviter
        // 汉语大字典
//        WordGenerator wordGenerator = new WordGenerator("Abalienationnn", "/usr/share/dict/words");
        WordGenerator wordGenerator = new WordGenerator("汉语大字典", "/usr/share/dict/words");

        wordGenerator.execute();
    }
}
