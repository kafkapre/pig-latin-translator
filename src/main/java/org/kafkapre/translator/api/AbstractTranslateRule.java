package org.kafkapre.translator.api;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.kafkapre.translator.CommandLineOptions.DEFAULT_PUNCTUATION;

public abstract class AbstractTranslateRule implements TranslateRule {

    protected final char[] punctuation;
    protected final String endsWithWAYRegex;
    protected final String startsWithPunctuationRegex;
    protected final String endsWithPunctuationRegex;

    protected abstract String applyRule(String word);

    protected abstract String getRegexRule();

    protected abstract int getPunctuationPreservationOffset();

    protected AbstractTranslateRule(char[] punctuation) {
        this.punctuation = punctuation;
        this.endsWithWAYRegex = "(.*way$)|(.*way[" + String.valueOf(DEFAULT_PUNCTUATION) + "]+$)";
        this.startsWithPunctuationRegex = "^[" + String.valueOf(DEFAULT_PUNCTUATION) + "]+";
        this.endsWithPunctuationRegex = "[" + String.valueOf(DEFAULT_PUNCTUATION) + "]+$";
    }

    @Override
    public boolean doTranslate(String word) {
        if (word == null || word.isEmpty() || word.matches(endsWithWAYRegex)) {
            return false;
        }
        return word.matches(getRegexRule());
    }

    @Override
    public String translate(String word) {
        Optional<String> startPunctuation = getFirsPunctuation(word);
        if (startPunctuation.isPresent()) {
            word = word.replaceFirst(startPunctuation.get(), "");
        }

        Optional<String> endPunctuation = getEndPunctuation(word);
        if (endPunctuation.isPresent()) {
            // removes punctuation from end of word
            word = word.substring(0, word.length() - endPunctuation.get().length());
        }

        List<Integer> indexes = findCapitalCharIndexes(word);

        String res = applyRule(word);
        res = applyPunctuationPreservationOffset(res, getPunctuationPreservationOffset());
        res = preserveCapitalCharsPosition(res, indexes);
        return joinRemovedPunctuation(startPunctuation, endPunctuation, res);
    }

    /*
    @Note: This approach is not the fastest solution to preserve upper case chars relative
    position, but I choose it because it is simple solution which will be understood by
    everyone. Another pros of this approach is that it is general solution, so
    another TranslateRules can be added.
     */
    private String preserveCapitalCharsPosition(String word, List<Integer> indexes) {
        StringBuilder sb = new StringBuilder(word);
        for (Integer i : indexes) {
            sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
        }
        return sb.toString();
    }

    private List<Integer> findCapitalCharIndexes(String str) {
        List<Integer> resIndexes = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                resIndexes.add(i);
            }
        }
        return resIndexes;
    }

    private String applyPunctuationPreservationOffset(String word, int offset) {
        String res = word;
        try {
            res = preservePunctuationRelativePosition(res, offset, punctuation);
        } catch (SwapStringCharsException ex) {
            String msg = String.format("Preservation of punctuation relative position failed." +
                    "Punctuation will not be preserved. Detailed info: %s", ex.getMessage());
            System.err.println(msg);
        }
        return res;
    }

    private Optional<String> getFirsPunctuation(String word) {
        Pattern pattern = Pattern.compile(startsWithPunctuationRegex);
        Matcher matcher = pattern.matcher(word);
        while (matcher.find()) {
            return Optional.of(matcher.group());
        }
        return Optional.empty();
    }

    private Optional<String> getEndPunctuation(String word) {
        Pattern pattern = Pattern.compile(endsWithPunctuationRegex);
        Matcher matcher = pattern.matcher(word);
        while (matcher.find()) {
            return Optional.of(matcher.group());
        }
        return Optional.empty();
    }

    private String joinRemovedPunctuation(Optional<String> startPunctuation,
                                          Optional<String> endPunctuation,
                                          String word) {
        String start = (startPunctuation.isPresent()) ? startPunctuation.get() : "";
        String end = (endPunctuation.isPresent()) ? endPunctuation.get() : "";
        return (start + word + end);
    }

    /*
     Preserves punctuation position which was not be at begin or end of word
     */
    static String preservePunctuationRelativePosition(String word, int offset, char[] punctuation) {
        StringBuilder builder = new StringBuilder(word);
        for (int i = word.length() - 1; i >= 0; i--) {
            char ch = builder.charAt(i);
            if (isPunctuation(ch, punctuation)) {
                for (int j = 0; j < offset; j++) {
                    swapCharacters(builder, i, j);
                }
            }
        }
        return builder.toString();
    }

    private static void swapCharacters(StringBuilder builder, int i, int j) {
        try {
            char tmp = builder.charAt(i + j);
            builder.setCharAt(i + j, builder.charAt(i + j + 1));
            builder.setCharAt(i + j + 1, tmp);
        } catch (RuntimeException ex) {
            throw new SwapStringCharsException(builder.length(), i, j, ex);
        }
    }

    private static boolean isPunctuation(char inputChar, char[] punctuation) {
        for (Character punctChar : punctuation) {
            if (punctChar == inputChar) {
                return true;
            }
        }
        return false;
    }

}
