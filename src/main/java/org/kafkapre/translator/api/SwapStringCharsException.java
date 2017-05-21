package org.kafkapre.translator.api;


public class SwapStringCharsException extends RuntimeException{

    private static final long serialVersionUID = 2716477074748219977L;

    public SwapStringCharsException(int wordLength, int i, int j, Throwable throwable){
        super(composeMessage(wordLength,i,j), throwable);
    }

    private static String composeMessage(int wordLength, int i, int j){
        return String.format("You cannot swap characters with indexes [%d, %d] because they are out of " +
                "range of word with %d characters.", i, j, wordLength);
    }

}
