package com.br;

public class WordCheckerArrayImpl implements WordChecker {

    private final String masterWord;
    private int[] chars;
    private int charMin;
    private int charMax;

    public WordCheckerArrayImpl(String masterWordParm) {
        this.masterWord = masterWordParm.toLowerCase().trim();

        if(masterWord.length() == 0){
            return;
        }

        charMin = masterWord.charAt(0);
        charMax = masterWord.charAt(0);
        int size = masterWord.length();

        for(int i = 1; i < size; i++){
            int current = masterWord.charAt(i);

            if(charMin > current){
                charMin = current;
            } else if(charMax < current){
                charMax = current;
            }
        }

        int arraySize = charMax - charMin + 1;
        chars = new int[arraySize];

        for(int i = 0; i < size; i++){
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