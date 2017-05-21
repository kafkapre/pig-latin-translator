package org.kafkapre.translator.impl;

import org.junit.Before;
import org.junit.Test;

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
    public void translate() throws Exception {
        String input = "Hello     apple stairway \ncan’t end. this-thing Beach McCloud. ";
        String expected = "Ellohay appleway stairway antca’y endway. histay-hingtay Eachbay CcLoudmay.";
        String actual = sentencesTranslator.translate(input);
        assertThat(actual).isEqualTo(expected);
    }

}