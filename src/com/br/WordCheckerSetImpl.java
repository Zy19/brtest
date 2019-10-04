package com.br;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WordCheckerSetImpl implements WordChecker {

    private final String masterWord;
    private final Map<Integer, AtomicInteger> mapMaster;

    public WordCheckerSetImpl(final String masterWordParm) {
        this.masterWord = masterWordParm.toLowerCase().trim();
        mapMaster = new HashMap<>();
        AtomicInteger counter = null;

        for(int i = 0; i < this.masterWord.length(); i++){
            int value = masterWord.charAt(i);
            counter = mapMaster.get(value);
            if(counter == null){
                mapMaster.put(value, new AtomicInteger(1));
            } else {
                counter.incrementAndGet();
            }
        }
    }

    @Override
    public boolean hasExtraCharacters(final String wordParm) {

        String word = wordParm.toLowerCase().trim();

        for(int i = 0; i < word.length(); i++){
            if(!this.mapMaster.containsKey(word.charAt(i))){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasExactCharacters(final String word) {

        HashMap<Integer, AtomicInteger> wordMap = (HashMap<Integer, AtomicInteger>) mapMaster.entrySet().stream()
                .collect(Collectors.toMap(k -> k.getKey(), k -> new AtomicInteger(k.getValue().intValue())));



//        HashMap<Integer, AtomicInteger> shallowCopy = (HashMap<Integer, AtomicInteger>) entries.stream()
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return false;
    }
}
