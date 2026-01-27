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
import java.util.Optional;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.checkstyle.autofix.CheckFullName;
import org.checkstyle.autofix.CheckstyleCheck;

public class XmlReportParser implements ReportParser {

    private static final String FILE_TAG = "file";

    private static final String ERROR_TAG = "error";

    private static final String FILENAME_ATTR = "name";

    private static final String LINE_ATTR = "line";

    private static final String COLUMN_ATTR = "column";

    private static final String SEVERITY_ATTR = "severity";

    private static final String MESSAGE_ATTR = "message";

    private static final String SOURCE_ATTR = "source";

    @Override
    public List<CheckstyleViolation> parse(Path xmlPath) {

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
                            parseErrorTag(startElement, filename).ifPresent(result::add);
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
        catch (IOException | XMLStreamException exception) {
            throw new IllegalArgumentException("Failed to parse checkstyle XML report from: "
                    + xmlPath, exception);
        }

        return result;
    }

    private String parseFileTag(StartElement startElement) {
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

    private Optional<CheckstyleViolation> parseErrorTag(StartElement startElement,
                                                               String filename) {
        int line = -1;
        int column = -1;
        String message = null;
        String severity = null;
        CheckstyleViolation violation = null;
        Optional<CheckFullName> checkName = Optional.empty();
        String id = null;

        final Iterator<Attribute> attributes = startElement.getAttributes();
        while (attributes.hasNext()) {
            final Attribute attribute = attributes.next();
            final String attrName = attribute.getName().getLocalPart();
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
                    final String attrValue = attribute.getValue();
                    checkName = CheckFullName.fromSource(attrValue);
                    final int index = attrValue.indexOf('#');
                    if (index >= 0) {
                        id = attrValue.substring(index + 1);
                    }
                    break;
                default:
                    break;
            }
        }
        if (checkName.isPresent()) {
            violation = new CheckstyleViolation(line, column, severity,
                    new CheckstyleCheck(checkName.get(), id), message, Path.of(filename));
        }
        return Optional.ofNullable(violation);

    }
}
