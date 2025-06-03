package org.checkstyle.autofix.recipe.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for reading test files in recipe tests
 */
public final class TestFileUtils {
    
    private static final String BASE_TEST_RESOURCES_PATH = "src/test/resources/org/checkstyle/autofix/recipe/";
    
    private TestFileUtils() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Reads test file content from the test resources directory
     * Ignores the package declaration line if present
     * @param filename the name of the file to read
     * @return the content of the file as a String without package declaration
     * @throws IOException if the file cannot be read
     */
    public static String readTestFile(String filename) throws IOException {
        Path filePath = Paths.get(BASE_TEST_RESOURCES_PATH + filename);
        String content = Files.readString(filePath);

        String[] lines = content.split("\n");
        StringBuilder result = new StringBuilder(128);

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (!trimmedLine.startsWith("package ") || trimmedLine.contains("{")) {
                result.append(line).append("\n");
            }
        }

        return result.toString();
    }
}