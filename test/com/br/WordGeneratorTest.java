package com.br;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WordGeneratorTest {

    public static void testPerformanceForChecher() {
        String fileName = "/usr/share/dict/words";
        final List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(currentWord -> lines.add(currentWord));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String wordMaster = "汉语大字典";

        WordGenerator wordGenerator = new WordGenerator(wordMaster, lines);

        String masterWord = wordMaster.toLowerCase().trim();
        int charMin = masterWord.charAt(0);
        int charMax = masterWord.charAt(0);
        int size = masterWord.length();

        for(int i = 1; i < size; i++){
            int current = masterWord.charAt(i);

            if(charMin > current){
                charMin = current;
            } else if(charMax < current){
                charMax = current;
            }
        }

        WordChecker[] checkers = {new WordCheckerArrayImpl(wordMaster, charMin, charMax), new WordCheckerSetImpl(wordMaster),
                new WordCheckerSet2Impl(wordMaster), new WordCheckerSet3Impl(wordMaster, charMin, charMax),
        };

        for(WordChecker checker : checkers) {
            long time = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                wordGenerator.testPerformance(checker);
            }
            System.out.println("Done: " + (System.currentTimeMillis() - time) + ", " + checker.getClass().getName());
        }


    }

    public static void main(String[] args){
        WordGeneratorTest.testPerformanceForChecher();
    }

    // 11528 - 54867 / 54301 / 47561
    // 9665 - 48928
}