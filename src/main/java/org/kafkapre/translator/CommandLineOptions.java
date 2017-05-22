package org.kafkapre.translator;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandLineOptions {

    public static final char[] DEFAULT_PUNCTUATION = new char[]{'.', '?', '!', ',', ':', ';', '"', '”', '“', '„', '`', '’', '\''};

    public static final String TEXT_OPTION_KEY = "t";
    public static final String FILE_OPTION_KEY = "f";
    public static final String OUTPUT_FILE_OPTION_KEY = "o";
    public static final String HELP_OPTION_KEY = "h";
    public static final String PUNCTUATION_OPTION_KEY = "p";

    private Options options = new Options();
    private CommandLineParser parser = new DefaultParser();
    private CommandLine commandLine = null;

    public CommandLineOptions() {
        this.options = new Options();
        this.parser = new DefaultParser();

        createOptions(this.options);
    }

    private void createOptions(Options options) {
        Option stringOption = Option.builder(TEXT_OPTION_KEY)
                .required(false)
                .hasArg()
                .desc(String.format("Translates <arg> into pig-latin. " +
                        "This option can't be used with option [-%s] simultaneously.", FILE_OPTION_KEY))
                .longOpt("text")
                .build();

        Option fileLocationOption = Option.builder(FILE_OPTION_KEY)
                .required(false)
                .hasArg().argName("file")
                .desc(String.format("Reads <file> and translates it into pig-latin. " +
                        "This option can't be used with option [-%s] simultaneously.", TEXT_OPTION_KEY))
                .longOpt("file")
                .build();

        Option outputFileLocationOption = Option.builder(OUTPUT_FILE_OPTION_KEY)
                .required(false)
                .hasArg().argName("file")
                .desc(String.format("Write translated text into <file>."))
                .longOpt("output")
                .build();

        Option punctuationOption = Option.builder(PUNCTUATION_OPTION_KEY)
                .required(false)
                .hasArg().argName("eg. \".,:\"")
                .desc(String.format(getPunctuationDescription()))
                .longOpt("punctuation")
                .build();

        Option helpOption = Option.builder(HELP_OPTION_KEY)
                .required(false)
                .desc("Prints help.")
                .longOpt("help")
                .build();

        options.addOption(stringOption);
        options.addOption(fileLocationOption);
        options.addOption(outputFileLocationOption);
        options.addOption(punctuationOption);
        options.addOption(helpOption);
    }

    public void parse(String[] args) throws ParseException {
        commandLine = parser.parse(options, args);
    }

    public Optional<String> getText() {
        if (commandLine == null || !commandLine.hasOption(TEXT_OPTION_KEY)) {
            return Optional.empty();
        }
        return Optional.ofNullable(commandLine.getOptionValue(TEXT_OPTION_KEY));
    }

    public Optional<String> getFileLocation() {
        if (commandLine == null || !commandLine.hasOption(FILE_OPTION_KEY)) {
            return Optional.empty();
        }
        return Optional.ofNullable(commandLine.getOptionValue(FILE_OPTION_KEY));
    }

    public Optional<String> getOutputFileLocation() {
        if (commandLine == null || !commandLine.hasOption(OUTPUT_FILE_OPTION_KEY)) {
            return Optional.empty();
        }
        return Optional.ofNullable(commandLine.getOptionValue(OUTPUT_FILE_OPTION_KEY));
    }

    public char[] getPunctuation(){
        if (commandLine == null || !commandLine.hasOption(PUNCTUATION_OPTION_KEY)) {
            return DEFAULT_PUNCTUATION;
        }
        String punctuationStr = commandLine.getOptionValue(PUNCTUATION_OPTION_KEY);
        return  (punctuationStr == null) ? DEFAULT_PUNCTUATION : punctuationStr.toCharArray();
    }

    public boolean isHelpRequired() {
        return (commandLine != null && commandLine.hasOption(HELP_OPTION_KEY));
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(getHelpDescription(), options);
    }

    private String getHelpDescription(){
        return String.format("This application translates an english input string (word, sentence, or paragraph) " +
                "into “pig-latin”. Input string can be passed as application argument or can be read " +
                "from file. Translation preserves relative punctuation position. As punctuation " +
                "characters are considered [%s] defaultly. In case, that you want to use own specific punctuation " +
                "characters then use parameter -%s.", defaultPunctuationToString(), PUNCTUATION_OPTION_KEY);
    }

    private String getPunctuationDescription(){
        return String.format("As punctuation characters are considered [%s] defaultly. In case, that you " +
                "want to use own specific punctuation characters then use argument [-%s]. For example if you run " +
                "application with argument -%s=\"^:&\" then characters: [^:&] will be consider as punctuation.",
                defaultPunctuationToString(), PUNCTUATION_OPTION_KEY, PUNCTUATION_OPTION_KEY);
    }

    private String defaultPunctuationToString(){
        return Stream.of(DEFAULT_PUNCTUATION)
                .map(ch -> String.valueOf(ch))
                .collect(Collectors.joining(","));
    }

}
