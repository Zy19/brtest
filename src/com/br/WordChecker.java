package com.br;

import java.util.*;

/**
 * Interface for checking word
 * It has 2 checking methods - hasExtraCharacters and hasExactCharacters because merging these is one works a bit slower
 */
interface WordChecker {

    /**
     * If master word is "dog" and parm is "doggy" return false because "y" is not in master word
     * @param word word
     * @return true or false
     */
    boolean hasExtraCharacters(final String word);

    /**
     * Does word has exact characters like master word and master word has enouhg characters
     * @param word word
     * @return true or false
     */
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

    /**
     * Check list of words
     * @param currentWords list of words
     * @return list of words which fit
     */
    default Set<String> checkWords(List<String> currentWords) {
        Set<String> set = new HashSet<>();
        currentWords.forEach(word -> set.add(checkWord(word)));
        return set;
    }
}
