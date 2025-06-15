package org.checkstyle.autofix.parser;

import java.util.NoSuchElementException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Simple {@link XMLEventReader} with no elements.
 *
 * @author Richard Veach
 */
public final class EmptyXmlEventReader implements XMLEventReader {
    @Override
    public Object next() {
        throw new NoSuchElementException();
    }

    @Override
    public XMLEvent nextEvent() {
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public XMLEvent peek() {
        return null;
    }

    @Override
    public String getElementText() {
        return null;
    }

    @Override
    public XMLEvent nextTag() {
        return null;
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void close() {
    }
}
