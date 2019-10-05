package com.br;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class WordGeneratorTest {

    private static final String[] SET1 = {"alienation", "binational", "antialien", "lineation", "notabilia", "ablation", "atonable", "biennial", "lenition", "libation", "national", "neonatal", "tailbone", "tannable", "abalone", "abelian", "aeolian", "aeonian", "alanine", "alation", "aniline", "antlion", "biennia", "elation", "enation", "labiate", "lantana", "niobate", "niobite", "nitinol", "notable", "toenail", "abelia", "ablate", "alanin", "albata", "albeit", "albino", "albite", "anilin", "anneal", "anoint", "atabal", "atonal", "atonia", "bailie", "balata", "banana", "banian", "bannet", "binate", "biotin", "boatel", "boleti", "bonita", "bonnet", "bonnie", "entail", "entoil", "eolian", "eonian", "etalon", "innate", "intine", "intone", "iolite", "lanate", "latina", "latino", "linnet", "lobate", "nation", "obelia", "oblate", "obtain", "online", "taenia", "talion", "tenail", "tibiae", "tibial", "tineal", "tolane", "aalii", "abate", "aboil", "aioli", "alane", "alant", "alate", "alibi", "alien", "aline", "aloin", "alone", "anent", "anile", "anion", "annal", "anole", "antae", "atone", "banal", "baton", "beano", "belon", "benni", "bento", "beton", "biali", "binal", "binit", "biont", "biota", "blain", "blate", "bleat", "blent", "blini", "blite", "bloat", "boite", "bonne", "botel", "elain", "elint", "eloin", "entia", "inane", "inion", "inlet", "labia", "lanai", "laten", "leant", "lento", "liana", "liane", "linen", "linin", "litai", "natal", "niton", "noble", "nonet", "notal", "oaten", "obeli", "olein", "tabla", "table", "talon", "telia", "teloi", "tenia", "tenon", "tibia", "tinea", "toile", "tolan", "tonal", "tonne", "abet", "able", "aeon", "alae", "alan", "alba", "alit", "aloe", "alto", "anal", "anil", "anna", "anoa", "anon", "anta", "ante", "anti", "baal", "bail", "bait", "bale", "bane", "bani", "bate", "bean", "beat", "belt", "bent", "beta", "bile", "bine", "bint", "bite", "blae", "blat", "blet", "blin", "blot", "boat", "boil", "bola", "bole", "bolt", "bone", "bota", "ebon", "elan", "enol", "etna", "ilea", "ilia", "inia", "inti", "into", "iota", "lain", "lane", "late", "lati", "lean", "leno", "lent", "lien", "line", "linn", "lino", "lint", "lion", "lite", "loan", "lobe", "loin", "lone", "lota", "loti", "naan", "nabe", "nail", "nala", "nana", "naoi", "neat", "neon", "nine", "nite", "noel", "noil", "nona", "none", "nota", "note", "obia", "obit", "olea", "tael", "tail", "tain", "tala", "tale", "tali", "teal", "tela", "tile", "tine", "toea", "toil", "tola", "tole", "tone", "aal", "aba", "abo", "ail", "ain", "ait", "ala", "alb", "ale", "alt", "ana", "ane", "ani", "ant", "ate", "baa", "bal", "ban", "bat", "bel", "ben", "bet", "bin", "bio", "bit", "boa", "bot", "eat", "eon", "eta", "inn", "ion", "lab", "lat", "lea", "lei", "let", "lib", "lie", "lin", "lit", "lob", "lot", "nab", "nae", "nan", "neb", "net", "nib", "nil", "nit", "nob", "not", "oat", "oba", "obe", "obi", "oil", "ole", "one", "tab", "tae", "tan", "tao", "tea", "tel", "ten", "tie", "til", "tin", "toe", "ton", "aa", "ab", "ae", "ai", "al", "an", "at", "ba", "be", "bi", "bo", "el", "en", "et", "in", "it", "la", "li", "lo", "na", "ne", "no", "oe", "oi", "on", "ta", "ti", "to"};

    public static void testPerformanceForCheckerLongArray() throws Exception {
        testPerformanceBase("典");
    }

    public static void testPerformanceForCheckerLongArray2() throws Exception {
        Character c = new Character((char) 5000);

        testPerformanceBase("" + c);
    }

    public static void testPerformanceForCheckerLongArray3() throws Exception {
        Character c = new Character((char) 2000);

        testPerformanceBase("" + c);
    }

    public static void testPerformanceForCheckerLongArray4() throws Exception {
        Character c = new Character((char) 1000);

        testPerformanceBase("" + c);
    }

    public static void testPerformanceForCheckerLongArray5() throws Exception {
        Character c = new Character((char) 1500);

        testPerformanceBase("" + c);
    }

    public static void testPerformanceForCheckerLongArray6() throws Exception {
        Character c = new Character((char) 1500);

        for (int i = 1000; i < 2000; i += 50) {
            testPerformanceBase("" + i);
        }
    }

    public static void testPerformanceForChecker() throws Exception {
        testPerformanceBase(null);
//
//    Done: 49027, com.br.WordCheckerArrayImpl
//    Done: 59326, com.br.WordCheckerSetImpl
//    Done: 59521, com.br.WordCheckerSet2Impl
//    Done: 57083, com.br.WordCheckerSet3Impl
    }

    public static void testPerformanceBase(String ch) throws Exception {
        String fileName = "/usr/share/dict/words";
        final List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(currentWord -> {

                if (ch == null) {
                    lines.add(currentWord);
                } else {
                    lines.add(currentWord + ch);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String wordMaster = "Abalienationnn";

        if (ch != null) {
            wordMaster += ch;
        }

        WordGenerator wordGenerator = new WordGenerator();

        String masterWord = wordMaster.toLowerCase().trim();
        int charMin = masterWord.charAt(0);
        int charMax = masterWord.charAt(0);
        int size = masterWord.length();

        for (int i = 1; i < size; i++) {
            int current = masterWord.charAt(i);

            if (charMin > current) {
                charMin = current;
            } else if (charMax < current) {
                charMax = current;
            }
        }

        WordChecker[] checkers = {new WordCheckerArray2Impl(wordMaster, charMin, charMax), new WordCheckerArrayImpl(wordMaster, charMin, charMax), new WordCheckerSet3Impl(wordMaster, charMin, charMax),
        };

        WordGeneratorStreamer streamer = new WordGeneratorStreamer(lines);

        for (WordChecker checker : checkers) {
            long time = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                //List<String> l = wordGenerator.executeInSingleThread(streamer, checker);
            }
            System.out.println("Done: " + (System.currentTimeMillis() - time) + ", " + checker.getClass().getName());
        }
    }


    public static void testPerformanceBaseMc(String ch) throws Exception {
        String fileName = "/usr/share/dict/words";
        final List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(currentWord -> {

                if (ch == null) {
                    lines.add(currentWord);
                } else {
                    lines.add(currentWord + ch);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String wordMaster = "Abalienationnn";

        if (ch != null) {
            wordMaster += ch;
        }

        WordGenerator wordGenerator = new WordGenerator();

        String masterWord = wordMaster.toLowerCase().trim();
        int charMin = masterWord.charAt(0);
        int charMax = masterWord.charAt(0);
        int size = masterWord.length();

        for (int i = 1; i < size; i++) {
            int current = masterWord.charAt(i);

            if (charMin > current) {
                charMin = current;
            } else if (charMax < current) {
                charMax = current;
            }
        }

        WordChecker[] checkers = {new WordCheckerArray2Impl(wordMaster, charMin, charMax), new WordCheckerArrayImpl(wordMaster, charMin, charMax), new WordCheckerSet3Impl(wordMaster, charMin, charMax),
        };

        WordGeneratorStreamer streamer = new WordGeneratorStreamer(lines);

        for (WordChecker checker : checkers) {
            long time = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                //List<String> l = wordGenerator.executeMCore(streamer, checker, 8);
            }
            System.out.println("Done: " + (System.currentTimeMillis() - time) + ", " + checker.getClass().getName());
        }
    }

    private static void test1() {

        List<String> lines = Arrays.asList(SET1);

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.execute(wordMaster, lines);

        assert lines.size() == result.size() : "test1 did not pass";
    }

    private static void testLong() {

        String ch = "典";

        List<String> linesBase = new ArrayList(Arrays.asList(SET1));
        List<String> lines = new ArrayList();

        linesBase.forEach(l -> lines.add(l + ch));

        String wordMaster = "Abalienationnn" + ch;
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.execute(wordMaster, lines);

        assert lines.size() == result.size() : "testLong did not pass";
    }

    private static void test2() {

        List<String> lines = new ArrayList();
        lines.add("aaaaaaaa");

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.execute(wordMaster, lines);

        assert result == null : "test2 did not pass";
    }

    private static void test3() {

        List<String> lines = new ArrayList(Arrays.asList(SET1));
        lines.add("aaaaaaaa");

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.execute(wordMaster, lines);

        assert lines.size() - 1 == result.size() : "test3 did not pass";
    }

    private static void test1Mt() {

        List<String> lines = new ArrayList();
        lines.add("aaaaaaaa");

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.executeUsingAllCores(wordMaster, lines);

        assert result == null : "test1Mt did not pass";
    }

    private static void test2Mt() {

        List<String> lines = new ArrayList();
        lines.add("aaaaaaaa");

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.executeUsingAllCores(wordMaster, lines);

        assert result == null : "test2Mt did not pass";
    }

    private static void test3Mt() {

        List<String> lines = new ArrayList(Arrays.asList(SET1));
        lines.add("aaaaaaaa");

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = wordGenerator.executeUsingAllCores(wordMaster, lines);

        assert lines.size() - 1 == result.size() : "test3Mt did not pass";
    }


    private static void test4Mt() {

        String fileName = "/usr/share/dict/words";
        final List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(currentWord -> lines.add(currentWord));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String wordMaster = "Abalienationnn";
        WordGenerator wordGenerator = new WordGenerator();
        List<String> result = null;
        for(int i = 0; i < 1000; i++) {
            result = wordGenerator.executeUsingAllCores(wordMaster, lines);
        }

        List<WordCheckerStatHelper> stat = wordGenerator.getAttempts();

        stat.forEach(s -> System.out.println(s));
    }

    public static void main(String[] args) throws Exception {
        /**
         * Just performance testing, works only if you change wordGenerator.executeInSingleThread to public
         */
//        testPerformanceForChecker();
//        testPerformanceForCheckerLongArray();
//        testPerformanceForCheckerLongArray2();
//        testPerformanceForCheckerLongArray3();
//        testPerformanceForCheckerLongArray4();
//        testPerformanceForCheckerLongArray5();
//        testPerformanceForCheckerLongArray6();
//        testPerformanceBaseMc(null);

        test1();
        testLong();
        test2();
        test3();
        test1Mt();
        test2Mt();
        test3Mt();

        test4Mt();// stat demo
    }

}
