package org.kafkapre.translator.api;


public abstract class AbstractTranslateRule implements TranslateRule {

    protected final char[] punctuation;

    protected abstract String applyRule(String word);

    protected abstract String getRegexRule();

    protected abstract int getPunctuationPreservationOffset();

    protected AbstractTranslateRule(char[] punctuation) {
        this.punctuation = punctuation;
    }

    public boolean doTranslate(String word) {
        if (word == null || word.isEmpty() || word.endsWith("way")) {
            return false;
        }
        return word.matches(getRegexRule());
    }

    @Override
    public String translate(String word) {
        String res = applyRule(word);
        try {
            int offset = getPunctuationPreservationOffset();
            res = preservePunctuationRelativePosition(res, offset, punctuation);
        } catch (SwapStringCharsException ex) {
            String msg = String.format("Preservation of punctuation relative position failed." +
                    "Punctuation will not be preserved. Detailed info: %s", ex.getMessage());
            System.err.println(msg);
        }
        return res;
    }

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
