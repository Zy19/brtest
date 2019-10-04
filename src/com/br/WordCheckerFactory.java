package com.br;

public class WordCheckerFactory {

    public static WordChecker createChecker(final String masterWord){
        return new WordCheckerArrayImpl(masterWord);
    }
}
