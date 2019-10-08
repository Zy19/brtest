package com.br;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.br.WordGeneratorPerformanceTest.DICT_FILE_NAME;

public class WordGeneratorContainerTest {

    private void test(int cores){
        String fileName = DICT_FILE_NAME;
        final List<String> lines = new ArrayList<>();
        String masterWord = "Abalienationnn";
        int step = 100;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(currentWord -> lines.add(currentWord));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final List<String> bigLinesOneCore = new ArrayList<>();
        final List<String>[] multicoreList = new List[cores];

        for(int i = 0; i < step * cores; i++){
            bigLinesOneCore.addAll(lines);
        }

        List<String> l = new ArrayList<>();
        for(int j = 0; j < step; j++){
            l.addAll(lines);
        }
        for(int i = 0; i < cores; i++){
            multicoreList[i] = l;
        }

        long timeSingleCoreStart = System.currentTimeMillis();
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.execute(masterWord, bigLinesOneCore);
        long timeSingleCoreEnd = System.currentTimeMillis();
        System.out.println("Single core: " + (timeSingleCoreEnd - timeSingleCoreStart));

        // Multicore
        long timeMcStart = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(0);
        for(int i = 0; i < cores; i++){
            List<String> chunk = multicoreList[i];
            counter.incrementAndGet();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    wordGenerator.execute(masterWord, chunk);
                    counter.decrementAndGet();
                }
            };
            (new Thread(runnable)).start();
        }

        while (counter.intValue() > 0) {
            // All threads should be done, if no we should wait
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        long timeMcEnds = System.currentTimeMillis();
        System.out.println("Multicore core: " + (timeMcEnds - timeMcStart));
        System.out.println("Cores in use:" + cores);
        System.out.println("Expected ratio:" + cores);
        System.out.println("Real ratio:" + ((timeSingleCoreEnd - timeSingleCoreStart) / (timeMcEnds - timeMcStart)));
        System.out.println();
    }

    public static void main(String[] args) throws Exception {

        WordGeneratorContainerTest test = new WordGeneratorContainerTest();

        for(int i = 2; i < 8; i++) {
            test.test(i);
        }

    }
}
