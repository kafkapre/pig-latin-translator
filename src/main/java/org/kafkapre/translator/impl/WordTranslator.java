package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.TranslateRule;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordTranslator {

    private final List<TranslateRule> rules;

    public WordTranslator(List<TranslateRule> rules) {
        this.rules = rules;
    }

    public String translateWord(String word) {
        String[] words = word.split("-");   // split word because of hyphens rule
        if (words.length > 1) {
            for (int i = 0; i < words.length; i++) {
                words[i] = applyRules(words[i]);
            }
            word = Arrays.stream(words).collect(Collectors.joining("-"));
        } else {
            word = applyRules(word);
        }
        return word;
    }

    private String applyRules(String word) {
        for (TranslateRule rule : rules) {
            if (rule.doTranslate(word)) {
                word = rule.translate(word);
                break;
            }
        }
        return word;
    }

}
