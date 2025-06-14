package org.checkstyle.autofix.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

class CheckstyleReportParserTest {

    @Test
    void shouldParseCheckstyleReport() throws Exception {
        final URL resourceUrl = getClass().getResource("/checkstyle-report.xml");
        assertThat(resourceUrl).isNotNull();
        final File reportFile = new File(resourceUrl.toURI());

        final CheckstyleReportParser parser = new CheckstyleReportParser();
        final List<CheckstyleReportParser.Violation> violations = parser.parseReport(reportFile);

        assertThat(violations).hasSize(3);

        final CheckstyleReportParser.Violation firstViolation = violations.get(0);
        assertThat(firstViolation.getFile())
                .isEqualTo("src/main/java/org/example/Test.java");
        assertThat(firstViolation.getLine())
                .isEqualTo(10);
        assertThat(firstViolation.getColumn())
                .isEqualTo(5);
        assertThat(firstViolation.getSeverity())
                .isEqualTo("error");
        assertThat(firstViolation.getMessage())
                .isEqualTo("'if' construct must use '{}'s.");
        assertThat(firstViolation.getSource())
                .isEqualTo("com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck");

        // Check second violation
        final CheckstyleReportParser.Violation secondViolation = violations.get(1);
        assertThat(secondViolation.getFile())
                .isEqualTo("src/main/java/org/example/Test.java");
        assertThat(secondViolation.getLine())
                .isEqualTo(15);
        assertThat(secondViolation.getColumn())
                .isEqualTo(20);
        assertThat(secondViolation.getSeverity())
                .isEqualTo("error");
        assertThat(secondViolation.getMessage())
                .isEqualTo("Line is longer than 100 characters.");
        assertThat(secondViolation.getSource())
                .isEqualTo("com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck");

        // Check third violation
        final CheckstyleReportParser.Violation thirdViolation = violations.get(2);
        assertThat(thirdViolation.getFile())
                .isEqualTo("src/main/java/org/example/AnotherTest.java");
        assertThat(thirdViolation.getLine())
                .isEqualTo(5);
        assertThat(thirdViolation.getColumn())
                .isEqualTo(10);
        assertThat(thirdViolation.getSeverity())
                .isEqualTo("error");
        assertThat(thirdViolation.getMessage())
                .isEqualTo("Missing a Javadoc comment.");
        assertThat(thirdViolation.getSource())
                .isEqualTo("com.puppycrawl.tools.checkstyle.checks.javadoc"
                        + ".JavadocMethodCheck");
    }
}
