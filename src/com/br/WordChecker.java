package com.br;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

interface WordChecker {

    boolean hasExtraCharacters(final String word);

    boolean hasExactCharacters(final String word);

    default String checkWord(String currentWord) {
        if(hasExtraCharacters(currentWord)){
            return null;
        }

        if(hasExactCharacters(currentWord)){
            return currentWord;
        }

        return null;
    }

    default Set<String> checkWords(List<String> currentWords, final AtomicInteger taskRun) {
        Set<String> set = new HashSet<>();
        currentWords.forEach(word -> set.add(checkWord(word)));
        taskRun.decrementAndGet();

        return set;
    }
}
