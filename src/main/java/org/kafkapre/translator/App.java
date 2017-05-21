package org.kafkapre.translator;


import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.Validate;
import org.kafkapre.translator.impl.SentencesTranslator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.IntConsumer;

import static org.kafkapre.translator.CommandLineOptions.FILE_OPTION_KEY;
import static org.kafkapre.translator.CommandLineOptions.TEXT_OPTION_KEY;
import static org.kafkapre.translator.impl.TranslatorsFactory.buildDefaultSentencesTranslator;

public class App {

    private final IntConsumer systemExitAction;

    public App(IntConsumer systemExitAction) {
        Validate.notNull(systemExitAction);
        this.systemExitAction = systemExitAction;
    }

    public String runApp(String[] args) {
        String res = "";
        CommandLineOptions options = new CommandLineOptions();
        try {
            options.parse(args);
        } catch (ParseException ex) {
            exitWithMessage(ex.toString(), options);
        }

        if (options.isHelpRequired()) {
            options.printHelp();
        } else {
            checkOptions_IfFailExit(options);
            try {
                res = processText(options);
            } catch (IOException e) {
                System.err.println(e.toString());
                systemExitAction.accept(1);
            }
        }

        if (options.getOutputFileLocation().isPresent()) {
            writeFile(options.getOutputFileLocation().get(), res);
        }

        return res;
    }

    private void checkOptions_IfFailExit(CommandLineOptions options) {
        if (options.getText().isPresent() && options.getFileLocation().isPresent()) {
            String msg = String.format("You cannot pass both arguments [-%s/-%s]", FILE_OPTION_KEY, TEXT_OPTION_KEY);
            exitWithMessage(msg, options);
        } else if (!options.getText().isPresent() && !options.getFileLocation().isPresent()) {
            String msg = String.format("You must pass one of arguments [-%s/-%s]", FILE_OPTION_KEY, TEXT_OPTION_KEY);
            exitWithMessage(msg, options);
        }
    }

    private String processText(CommandLineOptions options) throws IOException {
        String text = "";
        if (options.getText().isPresent()) {
            text = options.getText().get();
        } else if (options.getFileLocation().isPresent()) {
            text = readFile(options);
        }

        SentencesTranslator sentencesTranslator = buildDefaultSentencesTranslator(options.getPunctuation());
        String res = sentencesTranslator.translate(text);

        return res;
    }

    private String readFile(CommandLineOptions options) throws IOException {
        try {
            return readFile(options.getFileLocation().get());
        } catch (IOException ex) {
            String msg = String.format("File [%s] load failed.", options.getFileLocation().get());
            throw new IOException(msg, ex);
        }
    }

    private String readFile(String fileLocation) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
    }

    private void writeFile(String fileLocation, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation))) {
            bw.write(content);
            System.out.println(String.format("Translated text was written into file [%s].", fileLocation));
        } catch (IOException e) {
            String msg = String.format("Write into file [%s] failed. %s", fileLocation, e.getMessage());
            System.err.println(msg);
            systemExitAction.accept(1);
        }
    }

    private void exitWithMessage(String msg, CommandLineOptions options) {
        System.err.println(msg + "\n");
        options.printHelp();
        systemExitAction.accept(1);
    }

}
