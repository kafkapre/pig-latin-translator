package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.AbstractTranslateRule;

public class VowelTranslateRule extends AbstractTranslateRule {

    private static final String vowels = "aeiouAEIOU";
    private static final String suffix = "way";

    public VowelTranslateRule(char[] punctuation){
        super(punctuation);
    }

    @Override
    protected String getRegexRule() {
        return ("^[" + vowels + "].*");
    }

    @Override
    protected int getPunctuationPreservationOffset() {
        return 3;
    }

    @Override
    protected String applyRule(String word) {
        String res = word + suffix;
        return res;
    }
}
