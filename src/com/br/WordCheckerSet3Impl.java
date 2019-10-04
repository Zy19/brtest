package com.br;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class WordCheckerSet3Impl implements WordChecker {

    private final String masterWord;
    private final Map<Integer, Pair> mapMaster;
    private int charMin;
    private int charMax;

    private class Pair{
        int masterCounter;
        int workCounter;

        public Pair(int masterCounter, int workCounter) {
            this.masterCounter = masterCounter;
            this.workCounter = workCounter;
        }
    }

    public WordCheckerSet3Impl(final String masterWordParm, final int charMin, int charMax) {
        this.masterWord = masterWordParm.toLowerCase().trim();

        this.charMin = charMin;
        this.charMax = charMax;

        mapMaster = new HashMap<>();
        Pair counter = null;

        for(int i = 0; i < this.masterWord.length(); i++){
            int value = masterWord.charAt(i);
            counter = mapMaster.get(value);
            if(counter == null){
                counter = new Pair(1, 0);
                mapMaster.put(value, counter);
            } else {
                counter.workCounter++;
                mapMaster.put(value, counter);
            }
        }
    }

    @Override
    public boolean hasExtraCharacters(final String wordParm) {

        String word = wordParm.toLowerCase().trim();

        for(int i = 0; i < word.length(); i++){
            int current = word.charAt(i);
            if(current < charMin || current > charMax){
                return true;
            }

            if(!this.mapMaster.containsKey(current)){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasExactCharacters(final String wordParm) {

        String word = wordParm.toLowerCase().trim();

        this.mapMaster.values().forEach(p -> p.workCounter = 0);

        for(int i = 0; i < word.length(); i++){
            Pair counter = mapMaster.get((int) word.charAt(i));
            if(counter == null || counter.workCounter == 0){
                return false;
            }

            counter.workCounter++;
        }

        return true;
    }
}
