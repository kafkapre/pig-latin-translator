package org.kafkapre.translator;


import org.junit.Before;
import org.junit.Test;

import java.util.function.IntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kafkapre.translator.CommandLineOptions.PUNCTUATION_OPTION_KEY;

public class AppTest {

    private static class SystemExitException extends RuntimeException {
    }

    private static IntConsumer systemExitAction = i -> {
        throw new SystemExitException();
    };

    private App app;

    @Before
    public void setUp() {
        app = new App(systemExitAction);
    }

    @Test
    public void runAppWithHelpTest() throws Exception {
        app.runApp(new String[]{"-h"});
    }

    @Test
    public void runAppWithHelpThreeArgsTest() throws Exception {
        app.runApp(new String[]{"-f", "arg1", "--text", "arg2", "-h"});
    }

    @Test(expected = SystemExitException.class)
    public void runAppWithTwoArgsTest() throws Exception {
        app.runApp(new String[]{"-f ", "arg1", "-t", "arg2"});
    }

    @Test(expected = SystemExitException.class)
    public void runAppWithUnknownArgsTest() throws Exception {
        app.runApp(new String[]{"-d"});
    }

    @Test(expected = SystemExitException.class)
    public void runAppWithNoArgsTest() throws Exception {
        app.runApp(new String[]{"appName"});
    }

    @Test(expected = SystemExitException.class)
    public void runAppArgWithoutValueTest() throws Exception {
        app.runApp(new String[]{"-f"});
    }

    @Test(expected = SystemExitException.class)
    public void runAppFileNotFoundTest() throws Exception {
        app.runApp(new String[]{"-f=non/existing/path"});
    }

    @Test
    public void runAppEmptyTextTest() throws Exception {
        app.runApp(new String[]{"-t="});
    }

    @Test
    public void runAppEmptyFileTest() throws Exception {
        String path = AppTest.class.getClassLoader().getResource("empty.txt").getPath();
        app.runApp(new String[]{("-f=" + path)});
    }

    @Test
    public void runAppSimpleFileTest() throws Exception {
        String expected = "Ellohay appleway stairway antca’y endway. histay-hingtay Eachbay CcLoudmay.";
        String path = AppTest.class.getClassLoader().getResource("simple-text.txt").getPath();
        String actual = app.runApp(new String[]{("-f=" + path)});
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void runAppSimpleTextTest() throws Exception {
        String input = "Hello     apple stairway \ncan’t end. this-thing Beach McCloud. ";
        String expected = "Ellohay appleway stairway antca’y endway. histay-hingtay Eachbay CcLoudmay.";
        String actual = app.runApp(new String[]{"--text", input});
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void runAppWitchChangedPunctuationTest() throws Exception {
        String input = "Hello.";
        String expected = "Ello.hay";
        String actual = app.runApp(new String[]{"--text", input, punctuationArg("_")});
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void runAppWitchChangedPunctuationCorrectlyTest() throws Exception {
        String input = "Hello.";
        String expected = "Ellohay.";
        String actual = app.runApp(new String[]{"--text", input, punctuationArg(".")});
        assertThat(actual).isEqualTo(expected);
    }

    private String punctuationArg(String value){
        return "-" + PUNCTUATION_OPTION_KEY + "=" + value;
    }

}