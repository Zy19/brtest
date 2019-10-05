package com.br;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordGenerator {

    private static int LIST_THRESHOLD = 50000;

    public List<String> execute(final String masterWord, final String fileName) {
        return execute(masterWord, fileName, null, null, false, 0, 0);
    }

    public List<String> execute(final String masterWord, final List<String> list) {
        return execute(masterWord, null, list, null, false, 0, 0);
    }

    public List<String> execute(final String masterWord, final Stream<String> stream) {
        return execute(masterWord, null, null, stream, false, 0, 0);
    }

    public List<String> executeUsingAllCores(final String masterWord, final List<String> list) {
        return execute(masterWord, null, list, null, true, 0, 0);
    }

    public List<String> executeUsingAllCores(final String masterWord, final Stream<String> stream) {
        return execute(masterWord, null, null, stream, true, 0, 0);
    }

    public List<String> executeUsingCores(final String masterWord, final List<String> list, final int maxCoresInUse) {
        return execute(masterWord, null, list, null, false, maxCoresInUse, 0);
    }

    public List<String> executeUsingCores(final String masterWord, final Stream<String> stream, final int maxCoresInUse) {
        return execute(masterWord, null, null, stream, true, maxCoresInUse, 0);
    }

    public List<String> executeUsingCoresInPercents(final String masterWord, final List<String> list, final int maxCoresInUsePercents) {
        return execute(masterWord, null, list, null, false, 0, maxCoresInUsePercents);
    }

    public List<String> executeUsingCoresInPercents(final String masterWord, final Stream<String> stream, final int maxCoresInUsePercents) {
        return execute(masterWord, null, null, stream, true, 0, maxCoresInUsePercents);
    }

    private List<String> execute(final String masterWord, final String fileName, final List<String> list,
                                 final Stream<String> stream, final boolean useAllCores, final int maxCoresInUse,
                                 final int maxCoresInUsePercents) {

        final WordGeneratorStreamer streamer;

        if (fileName != null) {
            streamer = new WordGeneratorStreamer(fileName);
        } else if (list != null) {
            streamer = new WordGeneratorStreamer(list);
        } else {
            streamer = new WordGeneratorStreamer(stream);
        }

        WordChecker wordChecker = WordCheckerFactory.createChecker(masterWord);

        int coresInUse = 0;
        if (useAllCores) {
            coresInUse = Runtime.getRuntime().availableProcessors();
        } else if (maxCoresInUse > 0) {
            int cores = Runtime.getRuntime().availableProcessors();
            if (cores <= maxCoresInUse) {
                coresInUse = maxCoresInUse;
            } else {
                coresInUse = cores;
            }
        } else if (maxCoresInUsePercents > 0) {
            int maxCores = Runtime.getRuntime().availableProcessors();
            int cores = maxCores * 100 / maxCoresInUsePercents;
            if (cores < 1) {
                coresInUse = 1;
            } else if (cores > maxCores) {
                coresInUse = maxCores;
            } else {
                coresInUse = cores;
            }
        }

        try {
            if (coresInUse <= 1) {
                return executeInSingleThread(streamer, wordChecker);
            }

            return executeMCore(streamer, wordChecker, coresInUse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> executeInSingleThread(final WordGeneratorStreamer streamer, final WordChecker wordChecker) throws IOException {
        Set<String> resultSet = new HashSet<>();

        Stream<String> stream = streamer.getStream();
        stream.forEach(currentWord -> resultSet.add(wordChecker.checkWord(currentWord)));

        return convertResult(resultSet);
    }

    private List<String> executeMCore(final WordGeneratorStreamer streamer, final WordChecker wordChecker, final int coresInUse) throws InterruptedException, IOException {

        AtomicInteger taskRun = new AtomicInteger(0);
        List<String> list = new LinkedList<>();
        AtomicInteger delay = new AtomicInteger(10);
        Set<String> resultSet = new HashSet<>();

        Stream<String> stream = streamer.getStream();
        stream.forEach(currentWord -> {

            while (taskRun.intValue() >= coresInUse + 1) {
                try {
                    Thread.sleep(0, 10);
                    delay.addAndGet(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (delay.intValue() > 10) {
                System.out.println();
            }

            list.add(currentWord);

            if (LIST_THRESHOLD == list.size()) {
                taskRun.incrementAndGet();
                List<String> currentWords = new ArrayList<>(list);
                list.clear();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        resultSet.addAll(wordChecker.checkWords(currentWords, taskRun));
                    }
                };

                //System.out.println("Tasks in air: " + taskRun.intValue());
                (new Thread(runnable)).start();
            }
        });

        if(list.size() > 0){
            resultSet.addAll(wordChecker.checkWords(list, taskRun));
        }

        return convertResult(resultSet);
    }

    private List<String> convertResult(final Set<String> set) {

        if (set.size() == 0) {
            return null;
        }

        List<String> list = new ArrayList<>(set);

        if (list.size() == 1) {
            if (list.get(0) == null) {
                return null;
            }
        }

        List<String> result = list.stream().filter(v -> v != null).collect(Collectors.toList());

        return result;
    }
}