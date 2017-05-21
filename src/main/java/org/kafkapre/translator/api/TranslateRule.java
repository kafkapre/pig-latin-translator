package org.kafkapre.translator.api;


public interface TranslateRule {

    boolean doTranslate(String word);
    String translate(String word);

}
