package org.kafkapre.translator.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;
import static org.kafkapre.translator.impl.TranslatorsFactory.buildDefaultTranslatorRules;

public class WordTranslatorTest {

    private WordTranslator t;

    @Before
    public void setUp() {
        t = new WordTranslator(buildDefaultTranslatorRules(DEFAULT_PUNCTUATION));
    }

    @Test
    public void simpleEmptyTest() {
        final String expected = "";
        String actual = t.translateWord("");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void anyRuleDoesNotFitTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("_Beach", "_Beach");
        data.put("$MccloUd", "$MccloUd");
        data.put("]can’t", "]can’t");
        data.put("(end.", "(end.");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void simpleConsonantTest() {
        final String expected = "ohnjay";
        String actual = t.translateWord("john");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void complexConsonantTest() {
        final Map<String, String> data = new HashMap<>();
        data.put(",naNa", ",anAnay");
        data.put("nANa", "aNAnay");
        data.put("NAna", "ANanay");
        data.put("N", "Nay");
        data.put("", "");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void simpleVowelTest() {
        final String expected = "ireneway";
        String actual = t.translateWord("irene");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void complexVovelTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("aNa", "aNaway");
        data.put("Ana", "Anaway");
        data.put("ANA", "ANAway");
        data.put("ANa", "ANaway");
        data.put("A", "Away");
        data.put("", "");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void skipWordTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("stairway", "stairway");
        data.put("stairway.", "stairway.");
        data.put("stairway..", "stairway..");
        data.put("stairway,", "stairway,");
        data.put("stairway,,", "stairway,,");
        data.put("stairway.,", "stairway.,");
        data.put("stairway'.,", "stairway'.,");


        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void punctuationAtWordBeginTest() {
        final Map<String, String> data = new HashMap<>();
        data.put(".ana", ".anaway");
        data.put("..ana", "..anaway");
        data.put(",ana", ",anaway");
        data.put(",,ana", ",,anaway");

        data.put(".Ana", ".Anaway");
        data.put("..anA", "..anAway");
        data.put(",aNa", ",aNaway");
        data.put(",,ANa", ",,ANaway");

        data.put(".nana", ".ananay");
        data.put("..nana", "..ananay");
        data.put(",nana", ",ananay");
        data.put(",,nana", ",,ananay");

        data.put(".Nana", ".Ananay");
        data.put("..nAna", "..aNanay");
        data.put(",naNa", ",anAnay");
        data.put(",,NAna", ",,ANanay");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void edgeCasesTest() {
        final Map<String, String> data = new HashMap<>();
        data.put(".a.", ".away.");
        data.put(".A.", ".Away.");
        data.put("N", "Nay");
        data.put(".N.", ".Nay.");
        data.put(".NA...", ".ANay...");
        data.put("....", "....");
        data.put("..NANANA..", "..ANANANay..");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void wordWithHyphensTest() {
        final String expected = "histay-hingtay";
        String actual = t.translateWord("this-thing");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void wordWithDoubleHyphensTest() {
        final String expected = "histay--hingtay";
        String actual = t.translateWord("this--thing");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void wordWithTripleHyphensTest() {
        final String expected = "histay---hingtay";
        String actual = t.translateWord("this---thing");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void wordWithFourHyphensTest() {
        final String expected = "histay----hingtay";
        String actual = t.translateWord("this----thing");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void capitalizedWordTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("Beach", "Eachbay");
        data.put("MccloUd", "CclouDmay");
        data.put("McCloud", "CcLoudmay");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void punctuationSimpleTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("can’t", "antca’y");
        data.put("end.", "endway.");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test
    public void punctuationComplexTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("can’T", "antcA’y");
        data.put("end.E", "endeWa.y");
        data.put("E.E.", "EeWa.y.");
        data.put("e.E.", "eeWa.y.");
        data.put("Ce.E.", "EecA.y.");
        data.put("..Ce.E.", "..EecA.y.");
        data.put(":e.E.", ":eeWa.y.");
        data.put("b'bBbB", "bbb'bBay");
        data.put(".b'bBbB.", ".bbb'bBay.");
        data.put("b'bBbB.", "bbb'bBay.");
        data.put(":b'bBbB;.", ":bbb'bBay;.");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }
}