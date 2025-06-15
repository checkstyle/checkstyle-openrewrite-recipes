package org.checkstyle.autofix.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 * Utility class for StAX parser routines.
 */
final class StaxUtils {

    /**
     * Private ctor, use static methods instead.
     */
    private StaxUtils() {

    }

    /**
     * Creates parser linked to the existing XML file.
     *
     * @param xmlFilename
     *        name of an XML report file.
     * @return StAX parser interface.
     * @throws FileNotFoundException
     *         on wrong filename.
     * @throws XMLStreamException
     *         on internal factory failure.
     */
    public static XMLEventReader createReader(Path xmlFilename)
            throws FileNotFoundException, XMLStreamException {
        final XMLEventReader result;

        if (xmlFilename == null) {
            result = new EmptyXmlEventReader();
        }
        else {
            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            final InputStream inputStream =
                    new FileInputStream(xmlFilename.toFile());
            result = inputFactory.createXMLEventReader(inputStream);
        }

        return result;
    }

}
