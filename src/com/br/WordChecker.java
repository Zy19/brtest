package com.br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

interface WordChecker {

    boolean hasExtraCharacters(final String word);

    boolean hasExactCharacters(final String word);

    default void checkWord(String currentWord) {
        if(hasExtraCharacters(currentWord)){
            return;
        }

        if(hasExactCharacters(currentWord)){
            //System.out.println(currentWord);
        }
    }

    default void checkWords(List<String> currentWords, final AtomicInteger taskRun) {
        currentWords.forEach(word -> checkWord(word));
        taskRun.decrementAndGet();
    }
}
