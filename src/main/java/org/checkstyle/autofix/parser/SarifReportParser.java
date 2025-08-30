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

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.checkstyle.autofix.CheckstyleCheck;

import de.jcup.sarif_2_1_0.SarifSchema210ImportExportSupport;
import de.jcup.sarif_2_1_0.model.PhysicalLocation;
import de.jcup.sarif_2_1_0.model.Region;
import de.jcup.sarif_2_1_0.model.Result;
import de.jcup.sarif_2_1_0.model.Run;
import de.jcup.sarif_2_1_0.model.SarifSchema210;

public class SarifReportParser implements ReportParser {

    private static final String FILE_PREFIX = "file:";

    private final SarifSchema210ImportExportSupport parser;

    public SarifReportParser() {
        this.parser = new SarifSchema210ImportExportSupport();
    }

    @Override
    public List<CheckstyleViolation> parse(Path reportPath) {
        final SarifSchema210 report;
        try {
            report = parser.fromFile(reportPath);
        }
        catch (IOException exception) {
            throw new IllegalArgumentException("Failed to parse report: " + reportPath, exception);
        }
        final List<CheckstyleViolation> result = new ArrayList<>();
        for (final Run run: report.getRuns()) {
            if (run.getResults() != null) {
                run.getResults().forEach(resultEntry -> {
                    CheckstyleCheck.fromSource(resultEntry.getRuleId()).ifPresent(check -> {
                        final CheckstyleViolation violation = createViolation(check, resultEntry);
                        result.add(violation);
                    });
                });
            }
        }
        return result;
    }

    private CheckstyleViolation createViolation(CheckstyleCheck check, Result result) {
        final String severity = result.getLevel().name();
        final String message = result.getMessage().getText();
        final PhysicalLocation location = result.getLocations().get(0).getPhysicalLocation();
        final Path filePath = getFilePath(location);
        final Region region = location.getRegion();
        final int line = region.getStartLine();
        final Optional<Integer> columnMaybe = Optional.ofNullable(region.getStartColumn());
        return columnMaybe.map(column -> {
            return new CheckstyleViolation(line, column, severity, check, message, filePath);
        }).orElse(new CheckstyleViolation(line, severity, check, message, filePath));
    }

    private Path getFilePath(PhysicalLocation location) {
        final Path result;
        final String uri = location.getArtifactLocation().getUri();
        if (uri.startsWith(FILE_PREFIX)) {
            result = Paths.get(URI.create(uri));
        }
        else {
            result = Path.of(uri);
        }
        return result;
    }
}
