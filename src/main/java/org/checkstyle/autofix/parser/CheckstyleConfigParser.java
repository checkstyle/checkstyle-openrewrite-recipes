package org.checkstyle.autofix.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Parser for Checkstyle configuration XML files that extracts enabled checks and their parameters.
 */
public class CheckstyleConfigParser {

    /** Constant for the module XML tag name. */
    private static final String MODULE_TAG = "module";

    /** Constant for the name attribute. */
    private static final String NAME_ATTRIBUTE = "name";

    /** Constant for the property XML tag name. */
    private static final String PROPERTY_TAG = "property";

    /** Constant for the value attribute. */
    private static final String VALUE_ATTRIBUTE = "value";

    /**
     * Parses a Checkstyle configuration XML file and returns a list of enabled checks.
     *
     * @param configFile The XML configuration file to parse
     * @return List of enabled checks with their parameters
     * @throws Exception if there is an error parsing the XML file
     */
    public List<Check> parseConfig(final File configFile) throws Exception {
        final List<Check> checks = new ArrayList<>();

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(configFile);

        final Element rootModule = document.getDocumentElement();
        processModule(rootModule, checks);

        return checks;
    }

    /**
     * Recursively processes a module element and its children to extract check information.
     *
     * @param module The module element to process
     * @param checks The list to add found checks to
     */
    private void processModule(final Element module, final List<Check> checks) {
        // Get all child modules
        final NodeList moduleNodes = module.getElementsByTagName(MODULE_TAG);

        for (int moduleIndex = 0; moduleIndex < moduleNodes.getLength(); moduleIndex++) {
            final Element moduleElement = (Element) moduleNodes.item(moduleIndex);
            final String moduleName = moduleElement.getAttribute(NAME_ATTRIBUTE);

            final NodeList childModules = moduleElement.getElementsByTagName(MODULE_TAG);
            if (childModules.getLength() == 0) {
                final Map<String, String> properties = getProperties(moduleElement);

                checks.add(new Check(moduleName, properties));
            }
            else {
                processModule(moduleElement, checks);
            }
        }
    }

    /**
     * Reads all properties from the given XML element and returns them as a map.
     *
     * @param moduleElement the XML element containing property elements
     * @return a map of property names and their values
     */
    private static Map<String, String> getProperties(Element moduleElement) {
        final Map<String, String> properties = new HashMap<>();

        final NodeList propertyNodes = moduleElement.getElementsByTagName(PROPERTY_TAG);
        for (int propIndex = 0; propIndex < propertyNodes.getLength(); propIndex++) {
            final Element propertyElement = (Element) propertyNodes.item(propIndex);
            final String name = propertyElement.getAttribute(NAME_ATTRIBUTE);
            final String value = propertyElement.getAttribute(VALUE_ATTRIBUTE);
            final String unescapedValue = value.replace("\\$", "$");
            properties.put(name, unescapedValue);
        }
        return properties;
    }

    /**
     * Represents a Checkstyle check with its name and configuration properties.
     * This class is immutable and thread-safe.
     */
    public static final class Check {
        /** The name of the check. */
        private final String name;
        /** The configuration properties of the check. */
        private final Map<String, String> properties;

        /**
         * Creates a new check instance.
         *
         * @param name The name of the check
         * @param properties The configuration properties of the check
         */
        public Check(final String name, final Map<String, String> properties) {
            this.name = name;
            this.properties = new HashMap<>(properties);
        }

        /**
         * Gets the name of the check.
         *
         * @return The check name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the configuration properties of the check.
         *
         * @return An unmodifiable map of property names to values
         */
        public Map<String, String> getProperties() {
            return new HashMap<>(properties);
        }

        @Override
        public String toString() {
            return String.format("%s %s", name, properties);
        }
    }
}
