package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.AbstractTranslateRule;

import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;

public class VowelTranslateRule extends AbstractTranslateRule {

    private static final int punctuationPreservationOffset = 3;
    private static final String vowels = "aeiouAEIOU";
    private static final String suffix = "way";

    public VowelTranslateRule(char[] punctuation){
        super(punctuation);
    }

    @Override
    protected String getRegexRule() {
        return ("^[" + String.valueOf(DEFAULT_PUNCTUATION) + "]*[" + vowels + "].*");
    }

    @Override
    protected int getPunctuationPreservationOffset() {
        return punctuationPreservationOffset;
    }

    @Override
    protected String applyRule(String word) {
        String res = word + suffix;
        return res.toLowerCase();
    }
}
