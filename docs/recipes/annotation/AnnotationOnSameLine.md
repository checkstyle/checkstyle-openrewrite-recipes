# AnnotationOnSameLine Recipe

The `AnnotationOnSameLine` recipe ensures that an annotation is located on the same line as its target element (e.g., class, method, or field).

For more detailed information on this rule, please refer to the official [Checkstyle AnnotationOnSameLine Documentation](https://checkstyle.org/checks/annotation/annotationonsameline.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationOnSameLine"/>
  </module>
</module>
```

## Example

Below is an example showing how the `AnnotationOnSameLine` recipe transforms your code.

### Input

The following class has an annotation on a separate line from the class declaration, which violates the rule.

```java
@Deprecated
public class ExampleClass {
}
```

### Output

After applying the recipe, the annotation is moved to the same line as the class declaration.

```java
@Deprecated public class ExampleClass {
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -1,3 +1,2 @@
-@Deprecated
-public class ExampleClass {
+@Deprecated public class ExampleClass {
 }
```
