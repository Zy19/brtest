package com.br;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordGeneratorWrapper {

    public static void main(String[] args) {
        // 象形字
        // éviter
        // 汉语大字典
//        WordGenerator wordGenerator = new WordGenerator("Abalienationnn", "/usr/share/dict/words");
        WordGenerator wordGenerator = new WordGenerator("Abalienationnn", "/usr/share/dict/words");

        long time = System.currentTimeMillis();

        for(int i = 0; i < 1000; i++) {
            wordGenerator.execute();
        }

        System.out.println("Done: " + (System.currentTimeMillis() - time));
    }
}
