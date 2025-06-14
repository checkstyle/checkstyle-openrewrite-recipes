package org.checkstyle.autofix.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Parser for Checkstyle XML reports that extracts violation information.
 */
public class CheckstyleReportParser {

    /**
     * Parses a Checkstyle XML report file and returns a list of violations.
     *
     * @param reportFile The XML report file to parse
     * @return List of violations found in the report
     * @throws Exception if there is an error parsing the XML file
     */
    public List<Violation> parseReport(final File reportFile) throws Exception {
        final List<Violation> violations = new ArrayList<>();

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(reportFile);

        final NodeList fileNodes = document.getElementsByTagName("file");

        for (int fileIndex = 0; fileIndex < fileNodes.getLength(); fileIndex++) {
            final Element fileElement = (Element) fileNodes.item(fileIndex);
            final String fileName = fileElement.getAttribute("name");

            final NodeList errorNodes = fileElement.getElementsByTagName("error");

            for (int errorIndex = 0; errorIndex < errorNodes.getLength(); errorIndex++) {
                final Element errorElement = (Element) errorNodes.item(errorIndex);

                final int line = Integer.parseInt(errorElement.getAttribute("line"));
                final int column = Integer.parseInt(errorElement.getAttribute("column"));
                final String message = errorElement.getAttribute("message");
                final String source = errorElement.getAttribute("source");
                final String severity = errorElement.getAttribute("severity");

                violations.add(new Violation(fileName, line, column, message, source, severity));
            }
        }

        return violations;
    }

    /**
     * Represents a Checkstyle violation with file, line, column, and message information.
     * This class is immutable and thread-safe.
     */
    public static final class Violation {
        /** The file path where the violation occurred. */
        private final String file;
        /** The line number where the violation occurred. */
        private final int line;
        /** The column number where the violation occurred. */
        private final int column;
        /** The violation message. */
        private final String message;
        /** The source check that reported the violation. */
        private final String source;
        /** The severity level of the violation (error/warning). */
        private final String severity;

        /**
         * Creates a new violation instance.
         *
         * @param file The file path where the violation occurred
         * @param line The line number where the violation occurred
         * @param column The column number where the violation occurred
         * @param message The violation message
         * @param source The source check that reported the violation
         * @param severity The severity level of the violation
         */
        public Violation(final String file, final int line, final int column,
                         final String message, final String source, final String severity) {
            this.file = file;
            this.line = line;
            this.column = column;
            this.message = message;
            this.source = source;
            this.severity = severity;
        }

        /**
         * Gets the file path where the violation occurred.
         *
         * @return The file path
         */
        public String getFile() {
            return file;
        }

        /**
         * Gets the line number where the violation occurred.
         *
         * @return The line number
         */
        public int getLine() {
            return line;
        }

        /**
         * Gets the column number where the violation occurred.
         *
         * @return The column number
         */
        public int getColumn() {
            return column;
        }

        /**
         * Gets the violation message.
         *
         * @return The violation message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Gets the source check that reported the violation.
         *
         * @return The source check
         */
        public String getSource() {
            return source;
        }

        /**
         * Gets the severity level of the violation.
         *
         * @return The severity level
         */
        public String getSeverity() {
            return severity;
        }

        @Override
        public String toString() {
            return String.format("%s:%d:%d: %s [%s]", file, line, column, message, source);
        }
    }
}
