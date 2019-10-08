package com.br;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Use {@link com.br.WordCheckerSet3Impl} instead
 */
@Deprecated
class WordCheckerSet2Impl implements WordChecker {

    private final String masterWord;
    private final Map<Integer, Integer> mapMaster;
    private int charMin;
    private int charMax;

    public WordCheckerSet2Impl(final String masterWordParm) {
        this.masterWord = masterWordParm.toLowerCase().trim();

        charMin = masterWord.charAt(0);
        charMax = masterWord.charAt(0);
        int size = masterWord.length();

        for(int i = 1; i < size; i++){
            int current = masterWord.charAt(i);

            if(charMin > current){
                charMin = current;
            }

            if(charMax < current){
                charMax = current;
            }
        }

        mapMaster = new HashMap<>();
        Integer counter = null;

        for(int i = 0; i < this.masterWord.length(); i++){
            int value = masterWord.charAt(i);
            counter = mapMaster.get(value);
            if(counter == null){
                mapMaster.put(value, 1);
            } else {
                counter++;
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

        HashMap<Integer, Integer> wordMap = (HashMap<Integer, Integer>) mapMaster.entrySet().stream()
                .collect(Collectors.toMap(k -> k.getKey(), k -> new Integer(k.getValue().intValue())));

        for(int i = 0; i < word.length(); i++){
            Integer counter = wordMap.get((int) word.charAt(i));
            if(counter == null || counter.intValue() == 0){
                return false;
            }

            counter--;
            wordMap.put((int)word.charAt(i), counter);
        }

        return true;
    }
}
