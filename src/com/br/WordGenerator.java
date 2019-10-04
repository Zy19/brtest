package com.br;

import com.br.WordChecker;
import com.br.WordCheckerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordGenerator {

    private final String masterWord;
    private final String fileName;
    private final WordChecker wordChecker;

    public WordGenerator(final String masterWord, final String fileName){
        this.masterWord = masterWord;
        this.fileName = fileName;

        wordChecker = WordCheckerFactory.createChecker(masterWord);
    }

    public void execute(){
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(currentWord -> checkAndGenerate(currentWord));

        } catch (IOException e) {
            e.printStackTrace();
        }
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