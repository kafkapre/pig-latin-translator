package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.AbstractTranslateRule;

public class ConsonantTranslateRule extends AbstractTranslateRule {

    private static final String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
    private static final String suffix = "ay";

    public ConsonantTranslateRule(char[] punctuation) {
        super(punctuation);
    }

    @Override
    protected String getRegexRule() {
        return ("^[" + consonants + "].*");
    }

    @Override
    protected int getPunctuationPreservationOffset() {
        return 3;
    }

    @Override
    protected String applyRule(String word) {
        String res = preserveCapitalCharPosition(word);
        res = res.substring(1) + res.substring(0, 1).toLowerCase() + suffix;
        return res;
    }

    private String preserveCapitalCharPosition(String word) {
        StringBuilder strBuilder = new StringBuilder(word);
        for (int i = 0; i < word.length() - 1; i++) {
            char ch = word.charAt(i);
            if (Character.isUpperCase(ch)) {
                char prevChar = strBuilder.charAt(i + 1);
                strBuilder.setCharAt(i, Character.toLowerCase(ch));
                strBuilder.setCharAt(i + 1, Character.toUpperCase(prevChar));
            }
        }
        return strBuilder.toString();
    }

}
