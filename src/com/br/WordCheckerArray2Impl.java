package com.br;

/**
 * Array based implementstion of WordChecker
 */
class WordCheckerArray2Impl implements WordChecker {

    private final String masterWord;
    private WordCheckerPair[] chars;
    private int charMin;
    private int charMax;

    public WordCheckerArray2Impl(String masterWordParm, final int charMin, int charMax) {
        this.masterWord = masterWordParm.toLowerCase().trim();

        this.charMin = charMin;
        this.charMax = charMax;

        int arraySize = charMax - charMin + 1;
        chars = new WordCheckerPair[arraySize];

        for(int i = 0; i < arraySize; i++){
            chars[i] = new WordCheckerPair(0, 0);
        }

        for (int i = 0; i < masterWord.length(); i++) {
            int current = masterWord.charAt(i) - charMin;
            chars[current].masterCounter++;
        }
    }

    @Override
    public boolean hasExtraCharacters(final String wordParm) {

        String word = wordParm.toLowerCase().trim();
        int size = word.length();

        for (int i = 0; i < size; i++) {
            int current = word.charAt(i);
            if (current < charMin || current > charMax) {
                return true;
            }

            if (chars[current - charMin].masterCounter == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasExactCharacters(final String wordParm) {
        String word = wordParm.toLowerCase().trim();

        for (WordCheckerPair p : chars) {
            if (p.workCounter != p.masterCounter) {
                p.workCounter = p.masterCounter;
            }
        }

        for (int i = 0; i < word.length(); i++) {
            int current = word.charAt(i) - charMin;

            if (chars[current].workCounter == 0) {
                return false;
            }
            chars[current].workCounter--;
        }

        return true;
    }
}
