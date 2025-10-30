///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2025 The Checkstyle OpenRewrite Recipes Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.autofix.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.junit.jupiter.api.Test;

public class GenerateDiffFilesTest {

    private static final Logger LOGGER = Logger.getLogger(GenerateDiffFilesTest.class.getName());
    private static final String RESOURCES_DIR = "src/test/resources/org/checkstyle/autofix/recipe";

    @Test
    void generateDiffs() throws IOException, InterruptedException {
        LOGGER.info("Generating diffs for recipe tests....");

        try (Stream<Path> pathStream = Files.walk(Paths.get(RESOURCES_DIR))) {
            final List<Path> inputFiles = pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("Input"))
                    .toList();

            for (Path inputFile : inputFiles) {
                createDiff(inputFile);
            }
        }
        LOGGER.info("Files generated successfully.");
    }

    private void createDiff(Path inputFile) throws IOException, InterruptedException {
        final String inputFileName = inputFile.getFileName().toString();
        final String suffix = inputFileName.substring(5);

        final Path outputFile = inputFile.getParent().resolve("Output" + suffix);
        final String diffFileName = "Diff" + suffix.replaceFirst("\\.[^.]*$", ".diff");
        final Path diffFile = inputFile.getParent().resolve(diffFileName);

        if (!Files.exists(outputFile)) {
            throw new IOException("Output file does not exist: " + outputFile);
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final String oldPath = inputFile.toString().replace('\\', '/');
        final String newPath = outputFile.toString().replace('\\', '/');
        out.write(String.format("--- %s%n", oldPath).getBytes());
        out.write(String.format("+++ %s%n", newPath).getBytes());

        try (DiffFormatter formatter = new DiffFormatter(out)) {

            final RawText oldText = new RawText(Files.readAllBytes(inputFile));
            final RawText newText = new RawText(Files.readAllBytes(outputFile));

            formatter.format(
                    new HistogramDiff().diff(RawTextComparator.DEFAULT, oldText, newText),
                    oldText, newText);
        }

        final String diff = out.toString();
        final String normalizedDiff = diff.replaceAll("\\r\\n?", "\n");
        Files.writeString(diffFile, normalizedDiff);
    }
}
