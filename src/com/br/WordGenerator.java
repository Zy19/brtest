package com.br;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class gets master word as a parameter, reads words from standard input/file/list/stream and finds words which can be constructed from master word
 */
public class WordGenerator {

    private static int LIST_THRESHOLD = 1000;
    private static int LIST_THRESHOLD_STEP = 1000;
    private static int DELAY_NANO = 1000;
    private static long ATTEMPT_THRESHOLD_PERCENT = 2;

    private LinkedList<WordCheckerStatHelper> attempts = new LinkedList<>();

    /**
     * Find scramdled words in a file
     *
     * @param masterWord master word
     * @param fileName   word file, words should be separated by CR
     * @return list of unique words which can be constructed from master word
     */
    public List<String> execute(final String masterWord, final String fileName) {
        return execute(masterWord, fileName, null, null, false, 0, 0);
    }

    /**
     * Find scramdled words in a list of strings
     *
     * @param masterWord master word
     * @param list       list of words
     * @return list of unique words which can be constructed from master word
     */
    public List<String> execute(final String masterWord, final List<String> list) {
        return execute(masterWord, null, list, null, false, 0, 0);
    }

    /**
     * Find scramdled words in a stream of strings
     *
     * @param masterWord master word
     * @param stream     String stream of words
     * @return list of unique words which can be constructed from master word
     */
    public List<String> execute(final String masterWord, final Stream<String> stream) {
        return execute(masterWord, null, null, stream, false, 0, 0);
    }

    /**
     * Multithreading mode, tries to use all processor cores. In reality not all cores can be used for the best performance.
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord master word
     * @param list       list of words
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingAllCores(final String masterWord, final List<String> list) {
        return execute(masterWord, null, list, null, true, 0, 0);
    }

    /**
     * Multithreading mode, tries to use all processor cores. In reality not all cores can be used for the best performance.
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord master word
     * @param stream     String stream of words
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingAllCores(final String masterWord, final Stream<String> stream) {
        return execute(masterWord, null, null, stream, true, 0, 0);
    }

    /**
     * Multithreading mode.
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord    master word
     * @param list          list of words
     * @param maxCoresInUse how many cores should be used. If maxCoresInUse is less than 1, 1 core will be used, if maxCoresInUse is more than max cores, all cores will be used
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingCores(final String masterWord, final List<String> list, final int maxCoresInUse) {
        return execute(masterWord, null, list, null, false, maxCoresInUse, 0);
    }

    /**
     * Multithreading mode.
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord    master word
     * @param stream        String stream of words
     * @param maxCoresInUse how many cores should be used. If maxCoresInUse is less than 1, 1 core will be used, if maxCoresInUse is more than max cores, all cores will be used
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingCores(final String masterWord, final Stream<String> stream, final int maxCoresInUse) {
        return execute(masterWord, null, null, stream, true, maxCoresInUse, 0);
    }

    /**
     * Multithreading mode.
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord            master word
     * @param list                  list of words
     * @param maxCoresInUsePercents Percentage of all cores should be used. If counted core amount is less than 1, 1 core will be used, if counted core amount is more than max cores, all cores will be used
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingCoresInPercents(final String masterWord, final List<String> list, final int maxCoresInUsePercents) {
        return execute(masterWord, null, list, null, false, 0, maxCoresInUsePercents);
    }

    /**
     * Multithreading mode
     * Deprecated, because using multicore mode has unpredictable performace and computer is overloaded
     *
     * @param masterWord            master word
     * @param stream                String stream of words
     * @param maxCoresInUsePercents Percentage of all cores should be used. If counted core amount is less than 1, 1 core will be used, if counted core amount is more than max cores, all cores will be used
     * @return list of unique words which can be constructed from master word
     */
    @Deprecated
    public List<String> executeUsingCoresInPercents(final String masterWord, final Stream<String> stream, final int maxCoresInUsePercents) {
        return execute(masterWord, null, null, stream, true, 0, maxCoresInUsePercents);
    }

    /**
     * Base method
     *
     * @param masterWord            master word
     * @param fileName              used for creating a stream if provided
     * @param list                  used for creating a stream if provided
     * @param stream                used as a stream
     * @param useAllCores           deprecated
     * @param maxCoresInUse         deprecated
     * @param maxCoresInUsePercents deprecated
     * @return - list of unique words which can be constructed from master word
     */
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

    /**
     * Single thread mode
     *
     * @param streamer    String stream of words
     * @param wordChecker WordCheckerSet3Impl or WordCheckerArray2Impl depends of Master Word lenght
     * @return list of words, which can be constructed by Master Word characters. Each word appears only once
     * @throws IOException
     */
    private List<String> executeInSingleThread(final WordGeneratorStreamer streamer, final WordChecker wordChecker) throws IOException {
        Set<String> resultSet = new HashSet<>();

        Stream<String> stream = streamer.getStream();
        stream.forEach(currentWord -> resultSet.add(wordChecker.checkWord(currentWord)));

        return convertResult(resultSet);
    }

    /**
     * @param streamer    String stream of words
     * @param wordChecker WordCheckerSet3Impl or WordCheckerArray2Impl depends of Master Word lenght
     * @param coresInUse  how many processon cores will be used max
     * @return list of words, which can be constructed by Master Word characters. Each word appears only once
     * @throws InterruptedException
     * @throws IOException
     */
    private List<String> executeMCore(final WordGeneratorStreamer streamer, final WordChecker wordChecker, final int coresInUse) throws InterruptedException, IOException {

        WordCheckerStatHelper wordCheckerStatHelper = getCurrentSettings();

        AtomicInteger taskRun = new AtomicInteger(0);
        List<String> list = new LinkedList<>();
        AtomicInteger delay = new AtomicInteger(10);
//        Set<String> resultSet = new HashSet<>();
        AtomicInteger counter = new AtomicInteger(0);
        List<Set<String>> result = new ArrayList<>();

        long startTime = System.nanoTime();

        Stream<String> stream = streamer.getStream();
        stream.forEach(currentWord -> {

            while (taskRun.intValue() >= coresInUse + 1) {
                try {
                    Thread.sleep(0, wordCheckerStatHelper.getDelay());
                    delay.addAndGet(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            list.add(currentWord);

            if (wordCheckerStatHelper.getChunk() == list.size()) {
                counter.addAndGet(list.size());
                taskRun.incrementAndGet();
                if (wordCheckerStatHelper.getMaxCoreInUse() < taskRun.intValue()) {
                    synchronized (wordCheckerStatHelper) {
                        wordCheckerStatHelper.setMaxCoreInUse(taskRun.intValue());
                    }
                }

                List<String> currentWords = new ArrayList<>(list);
                list.clear();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Set<String> resultSet = wordChecker.checkWords(currentWords);
                        result.add(resultSet);
                        taskRun.decrementAndGet();
                    }
                };

                (new Thread(runnable)).start();
            }
        });

        if (list.size() > 0) {
            Set<String> resultSet = wordChecker.checkWords(list);
            result.add(resultSet);
            counter.addAndGet(list.size());
        }

        while (taskRun.intValue() > 0) {
            // All threads should be done, if no we should wait
            try {
                Thread.sleep(0, wordCheckerStatHelper.getDelay());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        synchronized (wordCheckerStatHelper) {
            wordCheckerStatHelper.setElapsedTimePerWord((System.nanoTime() - startTime) / counter.intValue());
            wordCheckerStatHelper.setElapsedTime((System.nanoTime() - startTime) / 1000000);
        }

        Set<String> resultSetAll = new HashSet<>();
        result.forEach(v -> {
            if(v != null && v.size() > 0) {
                resultSetAll.addAll(v);
            }
        });

        return convertResult(resultSetAll);
    }

    /**
     * Convert result into a String list, filter NULL value
     *
     * @param set unique set of found words
     * @return unisue list of words
     */
    private List<String> convertResult(final Set<String> set) {

        try {
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
        } catch (Exception ex) {
            System.out.println(1111);
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Get settings for the next run in multithreading mode
     *
     * @return current calculated setting or the best if it was already calculated
     */
    private synchronized WordCheckerStatHelper getCurrentSettings() {

        WordCheckerStatHelper helper;
        WordCheckerStatHelper helperPrevious = null;

        if (attempts.size() == 0) {
            helper = new WordCheckerStatHelper();
            helper.setAttempt(1);
            helper.setChunk(LIST_THRESHOLD);
            helper.setDelay(DELAY_NANO);

            attempts.add(helper);
            return helper;
        }

        helper = attempts.get(attempts.size() - 1);

        if (attempts.size() > 1) {
            helperPrevious = attempts.get(attempts.size() - 2);
        }

        if (helperPrevious != null) {

            if (helperPrevious.getElapsedTime() == 0) {
                // prevoius attempt was not finished yet, return the same
                return helperPrevious;
            }

            long diff = helper.getElapsedTimePerWord() - helperPrevious.getElapsedTimePerWord();
            long percentDiff = 100 * diff / helperPrevious.getElapsedTimePerWord();

            if (Math.abs(percentDiff) < ATTEMPT_THRESHOLD_PERCENT) {
                // difference of elapsed time in % less then threshold, does not make sence to do anything
                return helper;
            }

            WordCheckerStatHelper helperNext = new WordCheckerStatHelper();
            helperNext.setAttempt(helper.getAttempt() + 1);
            helperNext.setDelay(DELAY_NANO);

            int increasedSizeNext = helper.getIncreaseSize();

            if (Math.abs(increasedSizeNext) < 1) {
                // difference with prevous attempt is less the 1 ns. Just return the last helper
                return helper;
            }

            if (diff > 0 && helper.getIncreaseSize() > 0) {
                // Increased chunk, but it becames slower, should decrease
                increasedSizeNext = increasedSizeNext / 2;
                helperNext.setIncreaseSize((-1) * increasedSizeNext);
                helperNext.setChunk(helper.getChunk() + (-1) * increasedSizeNext);
            } else if (diff > 0 && helper.getIncreaseSize() < 0) {
                // Decreased chunk, but it becames slower, should increase
                increasedSizeNext = increasedSizeNext / 2;
                helperNext.setIncreaseSize(increasedSizeNext);
                helperNext.setChunk(helper.getChunk() + increasedSizeNext);
            } else if (diff < 0 && helper.getIncreaseSize() > 0) {
                // Increased chunk, it becames faster, increase more
                increasedSizeNext = increasedSizeNext * 2;
                helperNext.setIncreaseSize(increasedSizeNext);
                helperNext.setChunk(helper.getChunk() + increasedSizeNext);
            } else if (diff < 0 && helper.getIncreaseSize() < 0) {
                // Decreased chunk, it becames faster, Decreased more
                increasedSizeNext = increasedSizeNext / 2;
                helperNext.setIncreaseSize((-1) * increasedSizeNext);
                helperNext.setChunk(helper.getChunk() + (-1) * increasedSizeNext);
            }

            attempts.add(helperNext);

            return helperNext;
        }

        // Just the second attempt
        WordCheckerStatHelper helperSecond = new WordCheckerStatHelper();
        helperSecond.setAttempt(helper.getAttempt() + 1);
        helperSecond.setDelay(DELAY_NANO);
        helperSecond.setIncreaseSize(LIST_THRESHOLD_STEP * 2);
        helperSecond.setChunk(helper.getChunk() + LIST_THRESHOLD_STEP);

        attempts.add(helperSecond);

        return helperSecond;
    }

    /**
     * Get stat info for multicore mode
     *
     * @return list of attempts generated so far
     */
    public LinkedList<WordCheckerStatHelper> getAttempts() {
        return attempts;
    }

    public static void main(String[] args) {

        if (args.length < 1 || args.length > 2) {

            System.err.println("Wrong argument list!");
            System.out.println("Expected arguments:");
            System.out.println("<string> - master word");
            System.out.println("<string> - dictionary file name. Optional. If not provided standard input will be used.");

            return;
        }

        WordGenerator wordGenerator = new WordGenerator();

        List<String> result = null;

        if (args.length == 2) {
            result = wordGenerator.execute(args[0], args[1]);
        } else if (args.length == 1) {

            System.out.println();
            System.out.println("Masterword: " + args[0]);

            System.out.println("Input words separated by CR. End of list - Ctrl-D, Ctrl-Z, Command-D depends of OS");

            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                System.out.println("For Windoes use Ctrl-Z");
            } else if (os.indexOf("mac") >= 0) {
                System.out.println("For OS X use Command-D");
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                System.out.println("For Linux X use Ctrl-D");
            }

            Scanner scanner = new Scanner(System.in);

            List<String> words = new ArrayList<>();

            while (scanner.hasNext()) {
                String next = scanner.next().trim();
                words.add(next.trim());
            }
            scanner.close();

            result = wordGenerator.execute(args[0], words);
        }

        if (result == null) {
            System.out.println("Nothing match.");
        } else {
            System.out.println("Words found:");
            result.forEach(System.out::println);
        }
    }
}