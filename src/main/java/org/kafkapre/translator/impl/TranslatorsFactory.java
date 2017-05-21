package org.kafkapre.translator.impl;

import org.kafkapre.translator.api.TranslateRule;

import java.util.ArrayList;
import java.util.List;

public class TranslatorsFactory {

    public static SentencesTranslator buildDefaultSentencesTranslator(char[] punctuation){
        List<TranslateRule> rules = buildDefaultTranslatorRules(punctuation);
        return new SentencesTranslator(new WordTranslator(rules));
    }

    public static List<TranslateRule> buildDefaultTranslatorRules(char[] punctuation){
        List<TranslateRule> rules = new ArrayList<>();
        rules.add(new ConsonantTranslateRule(punctuation));
        rules.add(new VowelTranslateRule(punctuation));
        return rules;
    }

}
