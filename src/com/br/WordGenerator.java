package com.br;

import com.br.WordChecker;
import com.br.WordCheckerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class WordGenerator {

    private final String masterWord;
    private final String fileName;
    private final WordChecker wordChecker;
    private List<String> list;

    public WordGenerator(final String masterWord, final String fileName){
        this.masterWord = masterWord;
        this.fileName = fileName;

        wordChecker = WordCheckerFactory.createChecker(masterWord);
    }

    /**
     * Test constructor is used for test performance
     * @param masterWord
     * @param list - list of words
     */
    public WordGenerator(final String masterWord, final List<String> list){
        this.masterWord = masterWord;
        this.fileName = null;
        this.list = list;

        wordChecker = WordCheckerFactory.createChecker(masterWord);
    }

    public void execute(){
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(currentWord -> checkAndGenerate(currentWord));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testPerformance(WordChecker wordCheckerParm){
        list.forEach(l -> {
            if(!wordCheckerParm.hasExtraCharacters(l)){
                wordCheckerParm.hasExactCharacters(l);
            }
        });
    }

    private void checkAndGenerate(final String currentWord){
        if(wordChecker.hasExtraCharacters(currentWord)){
            return;
        }

        if(!wordChecker.hasExactCharacters(currentWord)){
            return;
        }

        System.out.println(currentWord);
    }


}