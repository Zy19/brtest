package com.br;

import java.util.*;

/**
 * Test class
 *
 * Because of "You may use anything from your language's standard libraries, but don't use anything
 * that would make the problem trivial such that we cannot see enough of a sample of your
 * personal code." I don't use JUnit or something like that, all tests are based on Java "assert", so it should be run with "-ea" JVM parameter
 */

public class WordGeneratorTest {

    private static final String WORD1 = "Abalienationnn";
    private static final String[] SET_FOR_WORD1 = {"alienation", "binational", "antialien", "lineation", "notabilia", "ablation", "atonable", "biennial", "lenition", "libation", "national", "neonatal", "tailbone", "tannable", "abalone", "abelian", "aeolian", "aeonian", "alanine", "alation", "aniline", "antlion", "biennia", "elation", "enation", "labiate", "lantana", "niobate", "niobite", "nitinol", "notable", "toenail", "abelia", "ablate", "alanin", "albata", "albeit", "albino", "albite", "anilin", "anneal", "anoint", "atabal", "atonal", "atonia", "bailie", "balata", "banana", "banian", "bannet", "binate", "biotin", "boatel", "boleti", "bonita", "bonnet", "bonnie", "entail", "entoil", "eolian", "eonian", "etalon", "innate", "intine", "intone", "iolite", "lanate", "latina", "latino", "linnet", "lobate", "nation", "obelia", "oblate", "obtain", "online", "taenia", "talion", "tenail", "tibiae", "tibial", "tineal", "tolane", "aalii", "abate", "aboil", "aioli", "alane", "alant", "alate", "alibi", "alien", "aline", "aloin", "alone", "anent", "anile", "anion", "annal", "anole", "antae", "atone", "banal", "baton", "beano", "belon", "benni", "bento", "beton", "biali", "binal", "binit", "biont", "biota", "blain", "blate", "bleat", "blent", "blini", "blite", "bloat", "boite", "bonne", "botel", "elain", "elint", "eloin", "entia", "inane", "inion", "inlet", "labia", "lanai", "laten", "leant", "lento", "liana", "liane", "linen", "linin", "litai", "natal", "niton", "noble", "nonet", "notal", "oaten", "obeli", "olein", "tabla", "table", "talon", "telia", "teloi", "tenia", "tenon", "tibia", "tinea", "toile", "tolan", "tonal", "tonne", "abet", "able", "aeon", "alae", "alan", "alba", "alit", "aloe", "alto", "anal", "anil", "anna", "anoa", "anon", "anta", "ante", "anti", "baal", "bail", "bait", "bale", "bane", "bani", "bate", "bean", "beat", "belt", "bent", "beta", "bile", "bine", "bint", "bite", "blae", "blat", "blet", "blin", "blot", "boat", "boil", "bola", "bole", "bolt", "bone", "bota", "ebon", "elan", "enol", "etna", "ilea", "ilia", "inia", "inti", "into", "iota", "lain", "lane", "late", "lati", "lean", "leno", "lent", "lien", "line", "linn", "lino", "lint", "lion", "lite", "loan", "lobe", "loin", "lone", "lota", "loti", "naan", "nabe", "nail", "nala", "nana", "naoi", "neat", "neon", "nine", "nite", "noel", "noil", "nona", "none", "nota", "note", "obia", "obit", "olea", "tael", "tail", "tain", "tala", "tale", "tali", "teal", "tela", "tile", "tine", "toea", "toil", "tola", "tole", "tone", "aal", "aba", "abo", "ail", "ain", "ait", "ala", "alb", "ale", "alt", "ana", "ane", "ani", "ant", "ate", "baa", "bal", "ban", "bat", "bel", "ben", "bet", "bin", "bio", "bit", "boa", "bot", "eat", "eon", "eta", "inn", "ion", "lab", "lat", "lea", "lei", "let", "lib", "lie", "lin", "lit", "lob", "lot", "nab", "nae", "nan", "neb", "net", "nib", "nil", "nit", "nob", "not", "oat", "oba", "obe", "obi", "oil", "ole", "one", "tab", "tae", "tan", "tao", "tea", "tel", "ten", "tie", "til", "tin", "toe", "ton", "aa", "ab", "ae", "ai", "al", "an", "at", "ba", "be", "bi", "bo", "el", "en", "et", "in", "it", "la", "li", "lo", "na", "ne", "no", "oe", "oi", "on", "ta", "ti", "to"};

    private static final String WORD2 = "Unscrambler";
    private static final String[] SET_FOR_WORD2 = {"unscramble", "scrambler", "albumens", "arbuscle", "bluesman", "clambers", "clumbers", "crumbers", "crumbles", "lucarnes", "manurers", "marblers", "mensural", "numerals", "ramblers", "rumblers", "scramble", "surnamer", "unbraces", "acumens", "albumen", "almners", "almuces", "amblers", "armures", "barrels", "barrens", "bascule", "becalms", "blamers", "bracers", "burlers", "burners", "bursera", "cablers", "cambers", "carrels", "censual", "clamber", "clubman", "clubmen", "clumber", "crambes", "crumber", "crumble", "cumbers", "curable", "curares", "curbers", "curlers", "currans", "labrums", "lacunes", "lambers", "lancers", "launces", "lucarne", "lucerns", "lumbars", "lumbers", "macules", "manurer", "manures", "marbler", "marbles", "marcels", "maulers", "mesclun", "nebular", "nebulas", "nuclear", "numbers", "numbles", "numeral", "rambler", "rambles", "recusal", "rubaces", "rumbler", "rumbles", "scumble", "secular", "serumal", "slumber", "slurban", "snarler", "subclan", "subrace", "sunbeam", "surname", "surreal", "unbales", "unbears", "unbrace", "unclear", "unlaces", "urbaner", "abuser", "acumen", "albums", "almner", "almuce", "ambers", "ambler", "ambles", "amuser", "armers", "armure", "balers", "barmen", "barrel", "barren", "barres", "becalm", "blamer", "blames", "blares", "blears", "blumes", "bracer", "braces", "breams", "brumal", "brumes", "burans", "burler", "burner", "bursae", "bursal", "bursar", "busman", "busmen", "cabers", "cabler", "cables", "cabmen", "calmer", "camber", "camels", "caners", "carers", "carles", "carmen", "carrel", "casern", "caules", "causer", "cesura", "clause", "cleans", "clears", "crambe", "cranes", "creams", "crumbs", "crural", "cubers", "cumber", "cuneal", "curare", "curber", "curers", "curler", "curran", "curser", "embars", "labrum", "lacers", "lacune", "lamber", "lancer", "lances", "larums", "launce", "learns", "lemans", "lemurs", "lucern", "lucres", "lumbar", "lumber", "lumens", "lunars", "lurers", "macers", "macles", "macule", "manure", "marble", "marcel", "mauler", "mensal", "mescal", "murals", "murras", "murres", "muscae", "muscle", "nacres", "namers", "nebula", "neural", "number", "nurser", "racers", "ramble", "rances", "rasure", "realms", "rearms", "rebars", "recurs", "remans", "reruns", "rubace", "rubels", "rubles", "rulers", "rumbas", "rumble", "rumens", "rurban", "sacrum", "sambur", "saucer", "saurel", "scaler", "scarer", "sclera", "scream", "snarer", "suable", "ulcers", "ulemas", "umbels", "umbers", "umbles", "umbrae", "umbral", "umbras", "unable", "unarms", "unbale", "unbars", "unbear", "uncase", "uncles", "unlace", "unreal", "unseal", "unseam", "urares", "urbane", "usable", "usance", "abler", "ables", "abuse", "acerb", "acmes", "acnes", "acres", "album", "alecs", "almes", "alums", "alure", "amber", "amble", "amens", "amuse", "arcus", "arles", "armer", "arums", "aures", "baler", "bales", "balms", "banes", "barer", "bares", "barms", "barns", "barre", "baser", "beams", "beans", "bears", "beaus", "bemas", "berms", "blame", "blams", "blare", "blase", "blear", "bluer", "blues", "blume", "blurs", "brace", "braes", "brans", "bream", "brens", "brume", "bunas", "buran", "buras", "burls", "burns", "burrs", "bursa", "burse", "caber", "cable", "calms", "camel", "cames", "caner", "canes", "carbs", "carer", "cares", "carle", "carls", "carns", "carrs", "carse", "cauls", "cause", "clams", "clans", "clean", "clear", "clubs", "clues", "crabs", "crams", "crane", "cream", "cruel", "crumb", "crura", "cruse", "cuber", "cubes", "culms", "curbs", "curer", "cures", "curls", "curns", "currs", "curse", "earls", "earns", "ecrus", "elans", "embar", "escar", "lacer", "laces", "lambs", "lamer", "lames", "lance", "lanes", "lares", "larum", "laser", "leans", "learn", "lears", "leman", "lemur", "lubes", "luces", "lucre", "lumas", "lumen", "lunar", "lunas", "lunes", "lurer", "lures", "mabes", "macer", "maces", "macle", "males", "manes", "manse", "manus", "marcs", "mares", "marls", "marse", "maser", "mauls", "meals", "means", "mensa", "menus", "mercs", "merls", "mules", "mural", "muras", "mures", "murra", "murre", "murrs", "musca", "muser", "nabes", "nacre", "namer", "names", "narcs", "nares", "nears", "nemas", "neums", "numbs", "nurls", "nurse", "racer", "races", "rales", "ramen", "ramus", "rance", "rares", "raser", "realm", "reals", "reams", "rearm", "rears", "rebar", "rebus", "recur", "reman", "renal", "reran", "rerun", "rubel", "rubes", "ruble", "ruers", "ruler", "rules", "rumba", "rumen", "runes", "rural", "saber", "sable", "sabre", "saner", "sauce", "scale", "scare", "scaur", "scena", "scram", "scrub", "scrum", "scuba", "sebum", "serac", "seral", "serum", "slurb", "smear", "snare", "snarl", "suber", "sucre", "sumac", "sural", "surer", "surra", "ulans", "ulcer", "ulema", "ulnae", "ulnar", "ulnas", "umbel", "umber", "umbra", "unarm", "unbar", "uncle", "urare", "urase", "urban", "ureal", "ureas", "ursae", "usnea", "able", "aces", "acme", "acne", "acre", "albs", "alec", "ales", "alme", "alms", "alum", "amen", "amus", "anes", "arbs", "arcs", "ares", "arms", "arum", "bale", "balm", "bals", "bams", "bane", "bans", "bare", "barm", "barn", "bars", "base", "beam", "bean", "bear", "beau", "bels", "bema", "bens", "berm", "blae", "blam", "blue", "blur", "brae", "bran", "bras", "bren", "bums", "buna", "buns", "bura", "burl", "burn", "burr", "burs", "cabs", "calm", "came", "cams", "cane", "cans", "carb", "care", "carl", "carn", "carr", "cars", "case", "caul", "cels", "clam", "clan", "club", "clue", "crab", "cram", "crus", "cube", "cubs", "cues", "culm", "curb", "cure", "curl", "curn", "curr", "curs", "earl", "earn", "ears", "ecru", "ecus", "elan", "elms", "emus", "eras", "erns", "errs", "labs", "lace", "lacs", "lamb", "lame", "lams", "lane", "lars", "lase", "lean", "lear", "leas", "lens", "lube", "luce", "lues", "luma", "lums", "luna", "lune", "lure", "mabe", "mace", "macs", "maes", "male", "mane", "mans", "marc", "mare", "marl", "mars", "maul", "maun", "meal", "mean", "mels", "menu", "merc", "merl", "mesa", "mule", "muns", "mura", "mure", "murr", "muse", "nabe", "nabs", "name", "narc", "near", "nebs", "nema", "neum", "nubs", "numb", "nurl", "race", "rale", "rams", "rare", "rase", "real", "ream", "rear", "rebs", "recs", "rems", "rube", "rubs", "ruer", "rues", "rule", "rums", "rune", "runs", "ruse", "sabe", "sale", "same", "sane", "saul", "scab", "scam", "scan", "scar", "scum", "seal", "seam", "sear", "sera", "slab", "slam", "slub", "slue", "slum", "slur", "snub", "suba", "suer", "sura", "sure", "ulan", "ulna", "unbe", "urbs", "urea", "urns", "ursa", "user", "abs", "ace", "alb", "ale", "als", "amu", "ane", "arb", "arc", "are", "arm", "ars", "bal", "bam", "ban", "bar", "bas", "bel", "ben", "bes", "bra", "brr", "bun", "bur", "bus", "cab", "cam", "can", "car", "cel", "cru", "cub", "cue", "cur", "ear", "eau", "ecu", "elm", "els", "ems", "emu", "ens", "era", "ern", "err", "ers", "lab", "lac", "lam", "lar", "las", "lea", "les", "leu", "lum", "mac", "mae", "man", "mar", "mas", "mel", "men", "mun", "mus", "nab", "nae", "nam", "neb", "nub", "nus", "ram", "ran", "ras", "reb", "rec", "rem", "res", "rub", "rue", "rum", "run", "sab", "sac", "sae", "sal", "sau", "sea", "sec", "sel", "sen", "ser", "sub", "sue", "sum", "sun", "uns", "urb", "urn", "use", "ab", "ae", "al", "am", "an", "ar", "as", "ba", "be", "el", "em", "en", "er", "es", "la", "ma", "me", "mu", "na", "ne", "nu", "re", "um", "un", "us"};

    private Map<String, List<String>> creatWordMap() {
        Map<String, List<String>> map = new HashMap<>();

        List<String> list1 = new ArrayList<>();
        Arrays.asList(SET_FOR_WORD1).forEach(v -> list1.add(v));
        List<String> list2 = new ArrayList<>();
        Arrays.asList(SET_FOR_WORD2).forEach(v -> list2.add(v));

        map.put(WORD1, list1);
        map.put(WORD2, list2);

        return map;
    }

    private void matchAllWords() {

        for (Map.Entry<String, List<String>> entry : creatWordMap().entrySet()) {
            String masterWord = entry.getKey();
            List<String> lines = entry.getValue();

            WordGenerator wordGenerator = new WordGenerator();
            List<String> result = wordGenerator.execute(masterWord, lines);

            assert lines.size() == result.size() : "matchAllWords did not pass";
        }
    }

    private void skipOneWord() {

        for (Map.Entry<String, List<String>> entry : creatWordMap().entrySet()) {
            String masterWord = entry.getKey();
            List<String> lines = entry.getValue();
            lines.add("aaaaaaaa");

            WordGenerator wordGenerator = new WordGenerator();
            List<String> result = wordGenerator.execute(masterWord, lines);

            assert lines.size() - 1 == result.size() : "skipOneWord did not pass";
        }
    }

    private void skipDuplicates() {

        for (Map.Entry<String, List<String>> entry : creatWordMap().entrySet()) {
            String masterWord = entry.getKey();
            List<String> lines = entry.getValue();

            int originalSize = lines.size();

            lines.add(lines.get(0));
            lines.add(lines.get(1));
            lines.add(lines.get(2));
            lines.add(lines.get(3));
            lines.add(lines.get(4));

            WordGenerator wordGenerator = new WordGenerator();
            List<String> result = wordGenerator.execute(masterWord, lines);

            assert originalSize == result.size() : "skipDuplicates did not pass";
        }
    }

    private void testLongDiffWords() {

        for (Map.Entry<String, List<String>> entry : creatWordMap().entrySet()) {
            String masterWord = entry.getKey() + "典";
            List<String> linesOriginal = entry.getValue();
            List<String> lines = new ArrayList<>();

            linesOriginal.forEach(l -> lines.add(l + "典"));

            WordGenerator wordGenerator = new WordGenerator();
            List<String> result = wordGenerator.execute(masterWord, lines);

            assert lines.size() == result.size() : "testLongDiffWords did not pass";
        }
    }

    private void testMulticore1() {

        for (Map.Entry<String, List<String>> entry : creatWordMap().entrySet()) {
            String masterWord = entry.getKey();
            List<String> lines = entry.getValue();

            WordGenerator wordGenerator = new WordGenerator();
            List<String> result = wordGenerator.executeUsingAllCores(masterWord, lines);

            assert lines.size() == result.size() : "testMulticore1 did not pass";
        }
    }

    private void test() {
        matchAllWords();
        skipOneWord();
        skipDuplicates();
        testLongDiffWords();
        testMulticore1();
    }

    public static void main(String[] args) throws Exception {
        (new WordGeneratorTest()).test();
    }

}
