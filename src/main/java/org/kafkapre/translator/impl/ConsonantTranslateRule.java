package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.AbstractTranslateRule;

import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;

public class ConsonantTranslateRule extends AbstractTranslateRule {

    private static final int punctuationPreservationOffset = 3;
    private static final String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
    private static final String suffix = "ay";

    public ConsonantTranslateRule(char[] punctuation) {
        super(punctuation);
    }

    @Override
    protected String getRegexRule() {
        return ("^[" + String.valueOf(DEFAULT_PUNCTUATION) + "]*[" + consonants + "].*");
    }

    @Override
    protected int getPunctuationPreservationOffset() {
        return punctuationPreservationOffset;
    }

    @Override
    protected String applyRule(String word) {
        StringBuilder sb = new StringBuilder();
        sb.append(word.substring(1).toLowerCase());
        sb.append(word.substring(0, 1).toLowerCase());
        sb.append(suffix);

        return sb.toString();
    }

}
