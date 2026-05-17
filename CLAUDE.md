# CLAUDE.md

## Project Overview

OpenRewrite recipes that auto-fix Checkstyle violations. Java 21, Maven (`./mvnw`). Published as `com.puppycrawl.tools:checkstyle-openrewrite-recipes`. Pipeline: user runs `mvn checkstyle:check` → produces XML/SARIF report → `mvn rewrite:run` invokes `CheckstyleAutoFix` → it dispatches to per-check sub-recipes.

## Build & Test

- Build: `./mvnw clean install` (CI adds `-e --no-transfer-progress` for stack traces and quieter logs)
- All tests: `./mvnw test`
- Single test class: `./mvnw test -Dtest=UpperEllTest`
- Single test method: `./mvnw test -Dtest=UpperEllTest#hexOctalLiteral` (note: `@RecipeTest` is parameterized, runs once per parser)
- Mutation testing: `./.ci/pitest.sh`
- Regenerate test diff files after fixture edits: run `GenerateDiffFilesTest#generateDiffs`

CI runs `clean install` then `git diff --exit-code`. The rewrite plugin runs in the `verify` phase against this repo's own sources — if a recipe still fixes anything on a clean checkout, CI fails. Run `./mvnw clean verify` locally; either commit the diff or fix the recipe so it stops mutating clean code.

## Architecture Rules

- Entry recipe `CheckstyleAutoFix` (`src/main/java/org/checkstyle/autofix/`) reads violation report + Checkstyle config, then `CheckstyleRecipeRegistry.getRecipes(...)` groups violations by check source and returns sub-recipes.
- Two factory maps in `CheckstyleRecipeRegistry`: `RECIPE_MAP` for recipes needing only violations, `RECIPE_MAP_WITH_CONFIG` for recipes that also need `CheckConfiguration` (currently `Header`, `NewlineAtEndOfFile`).
- Per-check recipes live in `org.checkstyle.autofix.recipe.*`, extend `org.openrewrite.Recipe`, take `List<CheckstyleViolation>` (and optionally `CheckConfiguration`) via constructor.
- Recipes must only mutate AST nodes whose line/column matches a reported violation — use `PositionHelper.computeLinePosition` / `computeColumnPosition`. Follow the pattern in `UpperEll.UpperEllVisitor#isAtViolationLocation`.
- Match source path with `violation.getFilePath().toAbsolutePath().endsWith(sourcePath)`, not equality.

## Adding a new per-check recipe

1. Add the Checkstyle check's fully-qualified class name to `CheckFullName` enum.
2. Create the recipe under `src/main/java/org/checkstyle/autofix/recipe/`.
3. Register the constructor in `CheckstyleRecipeRegistry`'s static initializer (right map).
4. Add test class extending `AbstractRecipeTestSupport`, override `getSubpackage()`, use `@RecipeTest`.
5. Add `Input{Name}.java` + `Output{Name}.java` fixtures under `src/test/resources/org/checkstyle/autofix/recipe/{subpackage}/{lowercase-name}/`. The Input file must start with a `/*xml ... */` inline Checkstyle config consumed by `InlineConfigParser`.
6. Run `GenerateDiffFilesTest#generateDiffs` to refresh `Diff*.diff` files.
7. Update the coverage table in `README.md` to link the new recipe.

## Code Style

- Apache-2.0 header from `config/header.txt` on every Java file; preserve the `/////...` delimiter lines exactly.
- Checkstyle config is pulled from upstream at `checkstyle-${checkstyle.version}`; local overrides live in `config/checkstyle.properties` and `config/suppressions.xml`.
- The rewrite plugin applies `OpenRewriteRecipeBestPractices` (see `rewrite.yml`) at build time: nullability annotations, `executionContext` parameter name, no static imports for `Collectors`/`Collections`, removed `@NlsRewrite.*` annotations, operator wrapping `NL`.

## Coding Rules

- **Single exit point per method.** Assign to a `result` local, mutate in branches, return once. See `CheckstyleAutoFix#createReportParser`, `CheckstyleRecipeRegistry#createRecipe`, every `visit*` method. Don't write multiple `return`s in branches.
- **`final` on every local variable.** Checkstyle's `FinalLocalVariable` enforces this — code that looks correct fails the build otherwise. Reassigned locals (the `result` accumulator) are the only exception.
- **Prefer imports over fully-qualified names.** Only use FQNs to disambiguate a real name clash (e.g., `org.openrewrite.Tree.randomId()` in `FinalClass` because `Tree` would clash). Don't inline `java.util.List<...>`, `org.openrewrite.Recipe`, etc. in method bodies.
- **No `var`.** This codebase uses explicit types throughout. Don't introduce inference.
- **`@Override` on every override.** `MissingOverride` check is on; the recipe also auto-adds them, so missing ones cause CI diff failures.
- **Visitors are `private final class XxxVisitor extends JavaIsoVisitor<ExecutionContext>` nested inside the recipe class.** Don't extract them to top-level files or anonymous classes. See every recipe under `recipe/`.
- **Method references over lambdas where equivalent.** `UpperEll::new`, not `v -> new UpperEll(v)`.
- **No `System.out.println` / `System.err.println`.** `RemoveSystemOutPrintln` strips them; use `java.util.logging.Logger` (see `GenerateDiffFilesTest`) if you really need output.
- **No `test` / `should` prefix on test methods.** `RemoveTestPrefix` rewrites them. Name methods after the scenario: `hexOctalLiteral()`, not `testHexOctalLiteral()`.
- **One recipe = one Checkstyle check = one focused concern.** Don't bundle unrelated fixes into one visitor. If a method does two things, split it — most methods in this repo are <20 lines and named after exactly what they do (`shouldProcessLongAndIntLiteral`, `getInsertPosition`, `hasFinalModifier`).
- **Don't catch broad `Exception`.** Catch the specific type, or re-throw wrapped in an `IOException`/`IllegalStateException` with context (see `AbstractRecipeTestSupport#writeModule`).

## Workflow Rules

- Use `@RecipeTest` (not `@Test`) for recipe tests — runs each method against both XML and SARIF parsers.
- Use `// violation 'msg'` inline markers in Input files; they're consumed by `verifyWithInlineConfigParser` and stripped by `RemoveViolationComments` before assertion.
- Pitest: surviving mutations are compared to `config/pitest-suppressions.xml` by `.ci/pitest-survival-check.groovy`. Add a suppression only when the survivor truly cannot be killed (document why); delete stale suppressions when the surviving mutation goes away.
