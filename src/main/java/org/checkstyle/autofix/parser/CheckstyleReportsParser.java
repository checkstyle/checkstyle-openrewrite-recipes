package org.checkstyle.autofix.parser;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.checkstyle.autofix.data.CheckReport;
import org.checkstyle.autofix.data.CheckstyleRecord;

/**
 * Contains logics of the StaX parser for the checkstyle xml reports.
 * If its scheme is changed, this class should be the first one to fix.
 *
 */
public final class CheckstyleReportsParser {

    /**
     * String value for "file" tag.
     */
    private static final String FILE_TAG = "file";

    /**
     * String value for "error" tag.
     */
    private static final String ERROR_TAG = "error";

    /**
     * String value for "name" attribute.
     */
    private static final String FILENAME_ATTR = "name";

    /**
     * String value for "line" attribute.
     */
    private static final String LINE_ATTR = "line";

    /**
     * String value for "column" attribute.
     */
    private static final String COLUMN_ATTR = "column";

    /**
     * String value for "severity" attribute.
     */
    private static final String SEVERITY_ATTR = "severity";

    /**
     * String value for "message" attribute.
     */
    private static final String MESSAGE_ATTR = "message";

    /**
     * String value for "source" attribute.
     */
    private static final String SOURCE_ATTR = "source";

    /**
     * Private ctor, see parse method.
     */
    private CheckstyleReportsParser() {

    }

    /**
     * Parses input XML files: creates 2 parsers
     * which process their XML files in rotation and try
     * to write their results to the ParsedContent class
     * inner map, where they are eagerly compared.
     *
     * @param xmlPath
     *        path to patch XML file.
     * @return parsed content.
     * @throws FileNotFoundException
     *         if files not found.
     * @throws XMLStreamException
     *         on internal parser error.
     */
    public static CheckReport parse(Path xmlPath)
            throws FileNotFoundException, XMLStreamException {
        final CheckReport content = new CheckReport();
        final XMLEventReader reader = StaxUtils.createReader(xmlPath);
        while (reader.hasNext()) {
            parseXmlPortion(content, reader);
        }
        return content;
    }

    /**
     * Parses portion of the XML report.
     *
     * @param checkReport
     *        container for parsed data.
     * @param reader
     *        StAX parser interface.
     * @throws XMLStreamException
     *         on internal parser error.
     */
    private static void parseXmlPortion(CheckReport checkReport,
                                        XMLEventReader reader)
            throws XMLStreamException {

        String filename = null;
        List<CheckstyleRecord> records = null;
        while (reader.hasNext()) {
            final XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                final StartElement startElement = event.asStartElement();
                final String startElementName = startElement.getName()
                        .getLocalPart();
                // file tag encounter
                if (startElementName.equals(FILE_TAG)) {
                    final Iterator<Attribute> attributes = startElement
                            .getAttributes();
                    while (attributes.hasNext()) {
                        final Attribute attribute = attributes.next();
                        if (attribute.getName().toString()
                                .equals(FILENAME_ATTR)) {
                            filename = attribute.getValue();
                        }
                    }
                    records = new ArrayList<>();
                }
                // error tag encounter
                else if (startElementName.equals(ERROR_TAG)) {
                    records.add(parseErrorTag(startElement,
                            filename));
                }
            }
            if (event.isEndElement()) {
                final EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals(FILE_TAG)) {
                    checkReport.addRecords(records, filename);
                }
            }
        }
    }

    /**
     * Parses "error" XML tag.
     *
     * @param startElement
     *        cursor of StAX parser pointed on the tag.
     * @param filename
     *        file name.
     * @return parsed data as CheckstyleRecord instance.
     */
    private static CheckstyleRecord parseErrorTag(StartElement startElement, String filename) {
        int line = -1;
        int column = -1;
        String source = null;
        String message = null;
        String severity = null;
        final Iterator<Attribute> attributes = startElement
                .getAttributes();
        while (attributes.hasNext()) {
            final Attribute attribute = attributes.next();
            final String attrName = attribute.getName().toString();
            switch (attrName) {
                case LINE_ATTR:
                    line = Integer.parseInt(attribute.getValue());
                    break;
                case COLUMN_ATTR:
                    column = Integer.parseInt(attribute.getValue());
                    break;
                case SEVERITY_ATTR:
                    severity = attribute.getValue();
                    break;
                case MESSAGE_ATTR:
                    message = attribute.getValue();
                    break;
                case SOURCE_ATTR:
                    source = attribute.getValue();
                    break;
                default:
                    break;
            }
        }
        return new CheckstyleRecord(
                line, column, severity, source, message, filename);

    }

}
