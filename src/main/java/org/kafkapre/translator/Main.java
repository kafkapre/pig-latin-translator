package org.kafkapre.translator;

import java.util.function.IntConsumer;


public class Main {

    private static final IntConsumer systemExitAction = new IntConsumer() {
        @Override
        public void accept(int i) {
            System.exit(i);
        }
    };

    public static void main(String[] args) {
        App app = new App(systemExitAction);
        String res = app.runApp(args);

        printTranslatedText(res);
    }

    private static void printTranslatedText(String res) {
        System.out.println("// ----------------------------------------");
        System.out.println("// Translated text:");
        System.out.println("// ----------------------------------------");
        System.out.println(res);
        System.out.println("// ----------------------------------------");
    }

}
