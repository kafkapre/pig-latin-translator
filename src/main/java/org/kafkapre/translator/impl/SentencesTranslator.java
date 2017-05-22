package org.kafkapre.translator.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SentencesTranslator {

    final WordTranslator wordTranslator;

    public SentencesTranslator(WordTranslator wordTranslator) {
        this.wordTranslator = wordTranslator;
    }

    public String translate(String text) {
        String[] words = text.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            words[i]= wordTranslator.translateWord(words[i]);
        }

        String res = Arrays.stream(words).collect(Collectors.joining(" "));
        return res;
    }

}
