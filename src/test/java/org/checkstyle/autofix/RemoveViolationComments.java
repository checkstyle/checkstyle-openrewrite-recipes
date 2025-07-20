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

package org.checkstyle.autofix;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TextComment;

public class RemoveViolationComments extends Recipe {

    @Override
    public String getDisplayName() {
        return "Remove violation comments";
    }

    @Override
    public String getDescription() {
        return "Removes comments that match the pattern '//violation *'.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new ViolationCommentRemover();
    }

    private static final class ViolationCommentRemover extends JavaIsoVisitor<ExecutionContext> {
        @Override
        public Space visitSpace(Space space, Space.Location loc, ExecutionContext ctx) {
            final StringBuilder suffixAccumulator = new StringBuilder();

            final List<Comment> filteredComments = space.getComments().stream()
                    .map(comment -> {
                        Comment result = comment;
                        if (!comment.isMultiline() && comment instanceof TextComment) {
                            final TextComment textComment = (TextComment) comment;
                            if (textComment.getText().startsWith("violation")) {
                                suffixAccumulator.append(textComment.getSuffix());
                                result = null;
                            }
                        }
                        return result; })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Space result;

            if (filteredComments.size() == space.getComments().size()) {
                result = space;
            }
            else {
                result = space.withComments(filteredComments);
                if (!suffixAccumulator.isEmpty()) {
                    result = result.withWhitespace(suffixAccumulator.toString());
                }
            }
            return super.visitSpace(result, loc, ctx);
        }
    }
}
