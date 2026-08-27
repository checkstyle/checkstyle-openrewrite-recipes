# AnnotationLocation Recipe

The `AnnotationLocation` recipe ensures that annotations are correctly positioned relative to the annotated element (e.g., class, method, or field). By default, it requires annotations to be located on a separate line from the target element.

For more detailed information on this rule, please refer to the official [Checkstyle AnnotationLocation Documentation](https://checkstyle.org/checks/annotation/annotationlocation.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation"/>
  </module>
</module>
```

## Example

Below is an example showing how the `AnnotationLocation` recipe transforms your code.

### Input

The following class has an annotation on the same line as the class declaration, which violates the rule.

```java
@Deprecated public class ExampleClass {
    int a = 1;
}
```

### Output

After applying the recipe, the annotation is moved to a separate line above the class declaration.

```java
@Deprecated
public class ExampleClass {
    int a = 1;
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -1,3 +1,4 @@
-@Deprecated public class ExampleClass {
+@Deprecated
+public class ExampleClass {
     int a = 1;
 }
```
