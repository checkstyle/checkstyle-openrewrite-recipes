# Available Recipes

## List of OpenRewrite Recipes
This page lists the currently available OpenRewrite recipes designed to fix Checkstyle violations.

### Annotation
- [AnnotationLocation](recipes/annotation/AnnotationLocation.md) - Ensures that annotations are correctly positioned relative to the annotated element.
- [AnnotationOnSameLine](recipes/annotation/AnnotationOnSameLine.md) - Ensures that an annotation is located on the same line as its target element.
- [MissingDeprecated](recipes/annotation/MissingDeprecated.md) - Ensures that both `@Deprecated` annotation and `@deprecated` Javadoc tag are present when either is used.
- [MissingOverride](recipes/annotation/MissingOverride.md) - Adds `@Override` annotation to methods that override a method from a superclass or interface.

### Coding
- [ArrayTrailingComma](recipes/coding/ArrayTrailingComma.md) - Fixes Checkstyle ArrayTrailingComma violations by adding a trailing comma after the last element of multi-line array initializers.
- [AvoidNoArgumentSuperConstructorCall](recipes/coding/AvoidNoArgumentSuperConstructorCall.md) - Removes unnecessary no-argument super constructor calls.
- [ConstructorsDeclarationGrouping](recipes/coding/ConstructorsDeclarationGrouping.md) - Groups all constructors together in a class.
- [EmptyStatement](recipes/coding/EmptyStatement.md) - Removes empty statements (standalone semicolons) from code.
- [FinalLocalVariable](recipes/coding/FinalLocalVariable.md) - Adds `final` modifier to local variables that are never reassigned.
- [MissingSwitchDefault](recipes/coding/MissingSwitchDefault.md) - Ensures that switch statements contain a `default` clause.
- [UnnecessaryParentheses](recipes/coding/UnnecessaryParentheses.md) - Removes unnecessary parentheses around expressions and identifiers.
- [UnusedLocalVariable](recipes/coding/UnusedLocalVariable.md) - Removes unused local variable declarations.
- [UseEnhancedSwitch](recipes/coding/UseEnhancedSwitch.md) - Converts traditional colon switch statements to enhanced switch with arrow syntax.

### Design
- [FinalClass](recipes/design/FinalClass.md) - Ensures that classes which only have private constructors are declared as final.

### Header
- [Header](recipes/header/Header.md) - Adds headers to Java source files when missing.

### Imports
- [AvoidStarImport](recipes/imports/AvoidStarImport.md) - Expands star imports into individual ones to avoid star imports.
- [RedundantImport](recipes/imports/RedundantImport.md) - Removes duplicate, java.lang, and same-package imports.
- [UnusedImports](recipes/imports/UnusedImports.md) - Removes unused imports.

### Javadoc
- [JavadocLeadingAsteriskAlign](recipes/javadoc/JavadocLeadingAsteriskAlign.md) - Fixes Checkstyle JavadocLeadingAsteriskAlign violations by aligning leading asterisks in Javadoc comments.

### Miscellaneous
- [HexLiteralCase](recipes/misc/HexLiteralCase.md) - Replaces lowercase hexadecimal letters (`a-f`) with uppercase letters (`A-F`).
- [NewlineAtEndOfFile](recipes/misc/NewlineAtEndOfFile.md) - Ensures that files end with a trailing newline.
- [NumericalPrefixesInfixesSuffixesCharacterCase](recipes/misc/NumericalPrefixesInfixesSuffixesCharacterCase.md) - Replaces uppercase numerical prefixes, infixes, and suffixes with lowercase characters.
- [UpperEll](recipes/misc/UpperEll.md) - Replaces lowercase 'l' suffix in long literals with uppercase 'L'.

### Whitespace
- [EmptyForInitializerPad](recipes/whitespace/EmptyForInitializerPad.md) - Fixes padding around empty for-loop initializers.
- [EmptyForIteratorPad](recipes/whitespace/EmptyForIteratorPad.md) - Fixes padding around empty for-loop iterators.
- [NoWhitespaceAfter](recipes/whitespace/NoWhitespaceAfter.md) - Removes forbidden whitespace following specific tokens.
