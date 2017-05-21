package org.kafkapre.translator.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;


public class AbstractTranslateRuleTest {
    
    @Test
    public void preservePunctuationRelativePositionTest() {
        final Map<String, String> data = new HashMap<>();
        data.put("end.way", "endway.");
        data.put("can’tona", "canton’a");
        data.put("n!oway", "nowa!y");
        data.put("?woolway", "woo?lway");

        data.forEach((input, expected) -> {
            String actual = preservePunctuationRelativePosition(input);
            assertThat(actual).isEqualTo(expected);
        });
    }

    @Test(expected = SwapStringCharsException.class)
    public void punctuationCannotBeMovedToRelativePosition_1_Test() {
        final String input = "end.";
        preservePunctuationRelativePosition(input);
    }

    @Test(expected = SwapStringCharsException.class)
    public void punctuationCannotBeMovedToRelativePosition_2_Test() {
        final String input = "end.a";
        preservePunctuationRelativePosition(input);
    }

    @Test(expected = SwapStringCharsException.class)
    public void punctuationCannotBeMovedToRelativePosition_3_Test() {
        final String input = "end.aa";
        preservePunctuationRelativePosition(input);
    }

    private String preservePunctuationRelativePosition(String input) {
        return AbstractTranslateRule.preservePunctuationRelativePosition(input, 3, DEFAULT_PUNCTUATION);
    }

}