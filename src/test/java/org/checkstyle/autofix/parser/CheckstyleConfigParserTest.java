package org.checkstyle.autofix.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CheckstyleConfigParserTest {

    @Test
    void shouldParseCheckstyleConfig() throws Exception {
        final URL resourceUrl = getClass().getResource("/checkstyle-config.xml");
        assertThat(resourceUrl).isNotNull();
        final File configFile = new File(resourceUrl.toURI());

        final CheckstyleConfigParser parser = new CheckstyleConfigParser();
        final List<CheckstyleConfigParser.Check> checks = parser.parseConfig(configFile);

        assertThat(checks).isNotEmpty();

        boolean foundLineLength = false;
        boolean foundNeedBraces = false;

        for (CheckstyleConfigParser.Check check : checks) {
            if ("LineLength".equals(check.getName())) {
                foundLineLength = true;
                final Map<String, String> properties = check.getProperties();
                assertThat(properties).containsEntry("max", "100");
                assertThat(properties).containsEntry("ignorePattern", "^.*\\$.*$");
            }
            else if ("NeedBraces".equals(check.getName())) {
                foundNeedBraces = true;
                final Map<String, String> properties = check.getProperties();
                assertThat(properties).containsEntry("allowSingleLineStatement", "true");
            }
        }

        assertThat(foundLineLength).isTrue();
        assertThat(foundNeedBraces).isTrue();
    }
}
