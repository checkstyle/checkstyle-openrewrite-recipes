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

package org.checkstyle.autofix.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public final class CheckstyleReportsParser {

    private static final String FILE_TAG = "file";

    private static final String ERROR_TAG = "error";

    private static final String FILENAME_ATTR = "name";

    private static final String LINE_ATTR = "line";

    private static final String COLUMN_ATTR = "column";

    private static final String SEVERITY_ATTR = "severity";

    private static final String MESSAGE_ATTR = "message";

    private static final String SOURCE_ATTR = "source";

    private CheckstyleReportsParser() {

    }

    public static List<CheckstyleViolation> parse(Path xmlPath)
            throws IOException, XMLStreamException {

        final List<CheckstyleViolation> result = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(xmlPath.toFile())) {

            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            final XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);

            try {
                String filename = null;

                while (reader.hasNext()) {
                    final XMLEvent event = reader.nextEvent();
                    if (event.isStartElement()) {
                        final StartElement startElement = event.asStartElement();
                        final String startElementName = startElement.getName().getLocalPart();

                        if (FILE_TAG.equals(startElementName)) {
                            filename = parseFileTag(startElement);
                        }
                        else if (ERROR_TAG.equals(startElementName)) {
                            Objects.requireNonNull(filename, "File name can not be null");
                            result.add(parseErrorTag(startElement, filename));
                        }
                    }
                }
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        return result;
    }

    private static String parseFileTag(StartElement startElement) {
        String fileName = null;
        final Iterator<Attribute> attributes = startElement.getAttributes();
        while (attributes.hasNext()) {
            final Attribute attribute = attributes.next();
            if (FILENAME_ATTR.equals(attribute.getName().getLocalPart())) {
                fileName = attribute.getValue();
                break;
            }
        }
        return fileName;
    }

    private static CheckstyleViolation parseErrorTag(StartElement startElement, String filename) {
        Integer line = null;
        Integer column = null;
        String source = null;
        String message = null;
        String severity = null;
        final Iterator<Attribute> attributes = startElement
                .getAttributes();
        while (attributes.hasNext()) {
            final Attribute attribute = attributes.next();
            final String attrName = attribute.getName().getLocalPart();
            switch (attrName) {
                case LINE_ATTR:
                    line = Integer.valueOf(attribute.getValue());
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
        return new CheckstyleViolation(
                line, column, severity, source, message, filename);

    }
}
