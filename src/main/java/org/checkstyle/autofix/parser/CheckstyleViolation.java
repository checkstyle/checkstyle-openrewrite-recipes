///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle-openrewrite-recipes: Automatically fix Checkstyle violations with OpenRewrite.
// Copyright (C) 2026 The Checkstyle OpenRewrite Recipes Authors
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

import java.nio.file.Path;

import org.checkstyle.autofix.CheckstyleCheck;

public final class CheckstyleViolation {

    private final int line;

    private final int column;

    private final CheckstyleCheck source;

    private final String message;

    private final Path filePath;

    public CheckstyleViolation(int line, CheckstyleCheck source, String message, Path filePath) {
        this(line, -1, source, message, filePath);
    }

    public CheckstyleViolation(int line, int column, CheckstyleCheck source,
                               String message, Path filePath) {
        this.line = line;
        this.column = column;
        this.source = source;
        this.message = message;
        this.filePath = filePath;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }

    public CheckstyleCheck getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

    public Path getFilePath() {
        return filePath;
    }

}
