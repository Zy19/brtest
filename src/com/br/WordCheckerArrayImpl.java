package com.br;

public class WordCheckerArrayImpl implements WordChecker {

    private final String masterWord;
    private int[] chars;
    private int charMin;
    private int charMax;

    public WordCheckerArrayImpl(String masterWordParm, final int charMin, int charMax) {
        this.masterWord = masterWordParm.toLowerCase().trim();

        this.charMin = charMin;
        this.charMax = charMax;

        int arraySize = charMax - charMin + 1;
        chars = new int[arraySize];

        for(int i = 0; i < masterWord.length(); i++){
            int current = masterWord.charAt(i) - charMin;
            chars[current] = chars[current] + 1;
        }
    }

    @Override
    public boolean hasExtraCharacters(final String wordParm) {

        String word = wordParm.toLowerCase().trim();
        int size = word.length();

        for(int i = 0; i < size; i++){
            int current = word.charAt(i);
            if(current < charMin || current > charMax){
                return true;
            }

            if(chars[current - charMin] == 0){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasExactCharacters(final String wordParm) {
        String word = wordParm.toLowerCase().trim();

        int[] masterArray = new int[chars.length];
        System.arraycopy(chars, 0, masterArray, 0, chars.length);

        for(int i = 0; i < word.length(); i++){
            int current = word.charAt(i) - charMin;

            if(masterArray[current] == 0) {
                return false;
            }
            masterArray[current] = masterArray[current] - 1;
        }

        return true;
    }
}
