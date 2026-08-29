# Available Recipes

## List of OpenRewrite Recipes
This page lists the currently available OpenRewrite recipes designed to fix Checkstyle violations.

### Design
- [FinalClass](recipes/design/FinalClass.md) - Ensures that classes which only have private constructors are declared as final.

### Annotation
- [AnnotationLocation](recipes/annotation/AnnotationLocation.md) - Ensures that annotations are correctly positioned relative to the annotated element.
- [AnnotationOnSameLine](recipes/annotation/AnnotationOnSameLine.md) - Ensures that an annotation is located on the same line as its target element.

### Coding
- [AvoidNoArgumentSuperConstructorCall](recipes/coding/AvoidNoArgumentSuperConstructorCall.md) - Removes unnecessary no-argument super constructor calls.
