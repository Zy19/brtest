package com.br;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class WordGenerator {

    private static int LIST_THRESHOLD = 50000;

    private final String masterWord;
    private final String fileName;
    private final WordChecker wordChecker;
    private List<String> wordList;
    private int coresInUse;

    public WordGenerator(final String masterWord, final String fileName, final int cores, final int percantageOfCoresInUse) {
        this.masterWord = masterWord;
        this.fileName = fileName;

        int allCores = Runtime.getRuntime().availableProcessors();

        if (cores > 0) {
            coresInUse = cores;
        } else if (percantageOfCoresInUse > 0) {
            coresInUse = Runtime.getRuntime().availableProcessors() * percantageOfCoresInUse / 100;
        }

        if (coresInUse > allCores) {
            coresInUse = allCores;
        } else if (coresInUse < 1) {
            coresInUse = 1;
        }

        wordChecker = WordCheckerFactory.createChecker(masterWord);
    }

    /**
     * Test constructor is used for test performance
     *
     * @param masterWord
     * @param list       - wordList of words
     */
    public WordGenerator(final String masterWord, final List<String> list) {
        this.masterWord = masterWord;
        this.fileName = null;
        this.wordList = list;

        wordChecker = WordCheckerFactory.createChecker(masterWord);
    }

    public void execute() {

        if (coresInUse == 1) {

            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                stream.forEach(currentWord -> this.wordChecker.checkWord(currentWord));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                executeMCore();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeMCore() throws InterruptedException {

        AtomicInteger taskRun = new AtomicInteger(0);
        List<String> listCurrent = new LinkedList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(currentWord -> {

                while(taskRun.intValue() >= coresInUse + 1){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                listCurrent.add(currentWord);

                if(LIST_THRESHOLD == listCurrent.size()){
                    taskRun.incrementAndGet();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            wordChecker.checkWords(listCurrent, taskRun);
                        }
                    };
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For testing only
     * @param wordCheckerParm
     */
    public void testPerformance(WordChecker wordCheckerParm) {

        coresInUse = 8;
        AtomicInteger taskRun = new AtomicInteger(0);
        List<String> list = new LinkedList<>();
        AtomicInteger delay = new AtomicInteger(10);

        wordList.forEach(l -> {
            while(taskRun.intValue() >= coresInUse + 1){
                try {
                    Thread.sleep(0, 10);
                    delay.addAndGet(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(delay.intValue() > 10){
                System.out.println();
            }

            list.add(l);

            if(LIST_THRESHOLD == list.size()) {
                taskRun.incrementAndGet();
                List<String> currentWords = new ArrayList<>(list);
                list.clear();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        wordChecker.checkWords(currentWords, taskRun);
                    }
                };

                //System.out.println("Tasks in air: " + taskRun.intValue());
                (new Thread(runnable)).start();
            }
        });
    }

//    private void checkAndGenerate(final String currentWord) {
//        if (wordChecker.hasExtraCharacters(currentWord)) {
//            return;
//        }
//
//        if (!wordChecker.hasExactCharacters(currentWord)) {
//            return;
//        }
//
//        System.out.println(currentWord);
//    }

    public static void main(String[] args) {
        // 象形字
        // éviter
        // 汉语大字典
        WordGenerator wordGenerator = new WordGenerator("Abalienationnn", "/usr/share/dict/words", 8, 0);
 //       WordGenerator wordGenerator = new WordGenerator("Abalienationnn", "/usr/share/dict/words", 0, 0);

        long time = System.currentTimeMillis();

        wordGenerator.execute();

        System.out.println("Done: " + (System.currentTimeMillis() - time));
    }
}