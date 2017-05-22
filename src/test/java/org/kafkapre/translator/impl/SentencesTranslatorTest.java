package org.kafkapre.translator.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;
import static org.kafkapre.translator.impl.TranslatorsFactory.buildDefaultSentencesTranslator;

public class SentencesTranslatorTest {

    private SentencesTranslator sentencesTranslator;

    @Before
    public void setUp() {
        sentencesTranslator = buildDefaultSentencesTranslator(DEFAULT_PUNCTUATION);
    }

    @Test
    public void translateTest() throws Exception {
        String input = "Hello     apple stairway \ncan’t end. this-thing Beach McCloud. ";
        String expected = "Ellohay appleway stairway antca’y endway. histay-hingtay Eachbay CcLoudmay.";
        String actual = sentencesTranslator.translate(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void translateComplexTest() throws Exception {
        final Map<String, String> data = new HashMap<>();
        data.put("....", "....");
        data.put(".A.", ".Away.");
        data.put("N", "Nay");
        data.put(".N.", ".Nay.");
        data.put(".NA...", ".ANay...");
        data.put("....", "....");
        data.put("..NANANA..", "..ANANANay..");
        data.put(",", ",");
        data.put("", "");
        data.put("a-a b--B ", "away-away bay--Bay");
        data.put("..NANANA.. .N. N *aaa", "..ANANANay.. .Nay. Nay *aaa");

        data.forEach((input, expected) -> {
            String actual = sentencesTranslator.translate(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

}