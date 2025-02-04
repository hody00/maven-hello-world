package com.myapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppTest {

    private static final String PROPERTIES_FILE = "app.properties";
    private static boolean isLastTest = false;

    @Before
    public void setUp() {
        if (!isLastTest) {
            deletePropertiesFile();
        }
    }

    @After
    public void tearDown() {
        if (!isLastTest) {
            deletePropertiesFile();
        }
    }

    private void deletePropertiesFile() {
        File file = new File(PROPERTIES_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createPropertiesFile(String content) throws IOException {
        FileWriter writer = new FileWriter(PROPERTIES_FILE);
        writer.write(content);
        writer.close();
    }

    private String runApp(String... args) throws IOException {
        String jarFilePath = System.getProperty("jarFilePath", "target/myapp-1.1.1.jar");
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", jarFilePath);
        if (args.length > 0) {
            for (String arg : args) {
                builder.command().add(arg);
            }
        }

        Process process = builder.start();
        Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @Test
    public void testWithoutPropertiesFile() throws IOException {
        String output = runApp();
        assertTrue(output.contains("Hello World! from: Hodaya"));
    }

    @Test
    public void testWithoutPropertiesFileWithCliArgs() throws IOException {
        String output = runApp("test1", "test2");
        assertTrue(output.contains("Hello World! from: Hodaya"));
        assertTrue(output.contains("Hello World! from: test1 test2"));
    }

    @Test
    public void testWithInvalidPropertiesFile() throws IOException {
        createPropertiesFile("test.message=Test1 Test2");
        String output = runApp();
        assertTrue(output.contains("Hello World! from: Hodaya"));
    }

    @Test
    public void testWithValidPropertiesFileAndCliArgs() throws IOException {
        createPropertiesFile("app.message=Test1 Test2");
        String output = runApp("cli1", "cli2");
        assertTrue(output.contains("Hello World! from: Hodaya"));
        assertTrue(output.contains("Hello World! from: Test1 Test2"));
        assertTrue(output.contains("Hello World! from: cli1 cli2"));
    }

    @Test
    public void restorePropertiesFile() throws IOException {
        isLastTest = true;
        createPropertiesFile("app.message=App Properties");
    }
}
