package com.adventofcode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testExampleOne() throws IOException {
        var path = Paths.get("src","test","resources","example01.txt").toFile().getAbsolutePath();

        App.main(new String[]{"1", path});

        var content = outContent.toString().split("\n");
        var answer = Arrays.stream(content)
                .filter(x -> x.startsWith("Answer is:"))
                .findFirst()
                .orElse("");

        if(answer.isEmpty()) fail("Could not find answer.");

        assertEquals("8", answer.substring(11));
    }

    @Test
    public void testProblemOne() throws IOException {
        var path = Paths.get("src","test","resources","input01.txt").toFile().getAbsolutePath();

        App.main(new String[]{"1", path});

        var content = outContent.toString().split("\n");
        var answer = Arrays.stream(content)
                .filter(x -> x.startsWith("Answer is:"))
                .findFirst()
                .orElse("");

        if(answer.isEmpty()) fail("Could not find answer.");

        assertEquals("54239", answer.substring(11));
    }

    @Test
    public void testExampleTwo() throws IOException {
        var path = Paths.get("src","test","resources","example02.txt").toFile().getAbsolutePath();

        App.main(new String[]{"2", path});

        var content = outContent.toString().split("\n");
        var answer = Arrays.stream(content)
                .filter(x -> x.startsWith("Answer is:"))
                .findFirst()
                .orElse("");

        if(answer.isEmpty()) fail("Could not find answer.");

        assertEquals("281", answer.substring(11));
    }

    @Test
    public void testPartTwo() throws IOException {
        var path = Paths.get("src","test","resources","input01.txt").toFile().getAbsolutePath();

        App.main(new String[]{"2", path});

        var content = outContent.toString().split("\n");
        var answer = Arrays.stream(content)
                .filter(x -> x.startsWith("Answer is:"))
                .findFirst()
                .orElse("");

        if(answer.isEmpty()) fail("Could not find answer.");

        assertEquals("55343", answer.substring(11));
    }
}