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
    public void simpleConsonantTest() {
        final String expected = "ohnjay";
        String actual = t.translateWord("john");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void simpleVowelTest() {
        final String expected = "ireneway";
        String actual = t.translateWord("irene");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void skipWordTest() {
        final String expected = "stairway";
        String actual = t.translateWord("stairway");
        assertThat(actual).isEqualTo(expected);
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
    public void punctuationTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("can’t", "antca’y");
        data.put("end.", "endway.");

        data.forEach((input, expected) -> {
            String actual = t.translateWord(input);
            assertThat(actual).isEqualTo(expected);
        });
    }


}