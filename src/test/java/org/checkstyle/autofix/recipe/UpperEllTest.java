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

package org.checkstyle.autofix.recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.checkstyle.autofix.parser.CheckConfiguration;
import org.checkstyle.autofix.parser.CheckstyleReportParser;
import org.checkstyle.autofix.parser.CheckstyleViolation;
import org.junit.jupiter.api.Test;
import org.openrewrite.Recipe;

public class UpperEllTest extends AbstractRecipeTest {

    @Override
    protected String getSubpackage() {
        return "upperell";
    }

    @Override
    protected Recipe createRecipe(List<CheckstyleViolation> violations,
                                  CheckConfiguration checkConfigs) {

        return new UpperEll(violations);
    }

    @Test
    void hexOctalLiteral() throws Exception {
        verify("HexOctalLiteral");
    }

    @Test
    void complexLongLiterals() throws Exception {
        verify("ComplexLongLiterals");
    }

    @Test
    void stringAndComments() throws Exception {
        verify("StringAndComments");
    }
}
