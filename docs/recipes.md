# Available Recipes

## List of OpenRewrite Recipes
This page lists the currently available OpenRewrite recipes designed to fix Checkstyle violations.

### Annotation
- [AnnotationLocation](recipes/annotation/AnnotationLocation.md) - Ensures that annotations are correctly positioned relative to the annotated element.
- [AnnotationOnSameLine](recipes/annotation/AnnotationOnSameLine.md) - Ensures that an annotation is located on the same line as its target element.

### Coding
- [ArrayTrailingComma](recipes/coding/ArrayTrailingComma.md) - Fixes Checkstyle ArrayTrailingComma violations by adding a trailing comma after the last element of multi-line array initializers.
- [AvoidNoArgumentSuperConstructorCall](recipes/coding/AvoidNoArgumentSuperConstructorCall.md) - Removes unnecessary no-argument super constructor calls.

### Design
- [FinalClass](recipes/design/FinalClass.md) - Ensures that classes which only have private constructors are declared as final.

### Imports
- [AvoidStarImport](recipes/imports/AvoidStarImport.md) - Expands star imports into individual ones to avoid star imports.
