package org.checkstyle.autofix.data;

import java.util.Arrays;
import java.util.List;

import org.thymeleaf.util.StringUtils;

/**
 * POJO, maps into single "error" tag of the XML.
 */
public final class CheckstyleRecord {

    /**
     * Predefined severities for sorting. All other severities has lower priority
     * and will be arranged in the default order for strings.
     */
    private static final List<String> PREDEFINED_SEVERITIES =
            Arrays.asList("info", "warning", "error");
    /**
     * Record line index.
     */
    private final int line;

    /**
     * Record column index.
     */
    private final int column;

    /**
     * Severity of this record.
     */
    private final String severity;

    /**
     * Name of the check that generated this record.
     */
    private final String source;

    /**
     * The message.
     */
    private final String message;

    /**
     * The xref.
     */
    private String xref;

    /**
     * POJO ctor.
     *
     * @param line
     *        line number.
     * @param column
     *        column number.
     * @param severity
     *        record severity level.
     * @param source
     *        name of check that generated record.
     * @param xref
     *        external file reference.
     * @param message
     *        error message.
     */
    public CheckstyleRecord(int line, int column,
                            String severity, String source, String message, String xref) {
        this.line = line;
        this.column = column;
        this.severity = severity;
        this.source = source;
        this.message = message;
        this.xref = xref;
    }

    /**
     * Returns the line index.
     *
     * @return the line index
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the column index.
     *
     * @return the column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the name of the check that generated this record.
     *
     * @return the name of the check
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the message in HTML format.
     *
     * @return the message in HTML format
     */
    public String getMessageHtml() {
        return StringUtils.escapeXml(message).replace("\n", "<br />\n");
    }

    /**
     * Returns the record xref.
     *
     * @return the record xref
     */
    public String getXref() {
        return xref;
    }

    /**
     * Setter for the record xref.
     *
     * @param xref the new xref
     */
    public void setXref(String xref) {
        this.xref = xref;
    }

    /**
     * Returns the record severity.
     *
     * @return the record severity
     */
    public String getSeverity() {
        return severity;
    }

}
