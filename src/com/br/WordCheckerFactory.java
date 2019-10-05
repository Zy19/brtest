package com.br;

/**
 * Create and return WordChecker based on master word.
 * If difference between characters in master word is less then 1300 Array Implementattion is used.
 * If more Set Implementation is used
 */
class WordCheckerFactory {

    private static final int MAX_DIFFERENCE = 1300;

    public static WordChecker createChecker(final String masterWordParm){

        String masterWord = masterWordParm.toLowerCase().trim();
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

        if(charMax - charMin > MAX_DIFFERENCE){
            return new WordCheckerSet3Impl(masterWord, charMin, charMax);
        }

        return new WordCheckerArray2Impl(masterWord, charMin, charMax);
    }
}
