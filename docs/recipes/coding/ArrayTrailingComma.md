# ArrayTrailingComma Recipe

The `ArrayTrailingComma` recipe fixes Checkstyle ArrayTrailingComma violations by adding a trailing comma after the last element of multi-line array initializers.

For more detailed information on this rule, please refer to the official [Checkstyle ArrayTrailingComma Documentation](https://checkstyle.org/checks/coding/arraytrailingcomma.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayTrailingComma"/>
  </module>
</module>
```

## Example

Below is an example showing how the `ArrayTrailingComma` recipe transforms your code.

### Input

The following array initializer does not end with a trailing comma, which violates the rule for multi-line arrays.

```java
int[] multiElementMultiLine = new int[] {
    1,
    2,
    3
};
```

### Output

After applying the recipe, a trailing comma is added after the last element.

```java
int[] multiElementMultiLine = new int[] {
    1,
    2,
    3,
};
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -3,3 +3,3 @@
     1,
     2,
-    3
+    3,
 };
```
