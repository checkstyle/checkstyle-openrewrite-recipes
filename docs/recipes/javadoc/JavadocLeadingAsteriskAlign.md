# JavadocLeadingAsteriskAlign Recipe

The `JavadocLeadingAsteriskAlign` recipe fixes Checkstyle JavadocLeadingAsteriskAlign violations by aligning the leading asterisks on each line of a Javadoc comment under the first asterisk of the opening tag (`/**`), as well as aligning the closing tag (`*/`).

For more detailed information on this rule, please refer to the official [Checkstyle JavadocLeadingAsteriskAlign Documentation](https://checkstyle.org/checks/javadoc/javadocleadingasteriskalign.html).

## Usage

To configure this Checkstyle rule, add the following module to your Checkstyle configuration file (`checkstyle.xml`):

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign"/>
  </module>
</module>
```

### Options

| Property | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `tabWidth` | `int` | `8` | Number of expanded spaces for a tab character (`\t`). |

```xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocLeadingAsteriskAlign">
      <property name="tabWidth" value="4"/>
    </module>
  </module>
</module>
```

## Example

Below is an example showing how the `JavadocLeadingAsteriskAlign` recipe transforms your code.

### Input

In the following class, the second line of the Javadoc comment and the closing tag have misaligned asterisks.

```java
public class ExampleClass {
    /**
     * Line 1
      * Line 2
    */
    public void test() {
    }
}
```

### Output

After applying the recipe, the leading asterisks and the closing tag are aligned with the first asterisk of the opening tag.

```java
public class ExampleClass {
    /**
     * Line 1
     * Line 2
     */
    public void test() {
    }
}
```

### Diff

This diff highlights the exact changes made by OpenRewrite:

```diff
--- a/ExampleClass.java
+++ b/ExampleClass.java
@@ -3,4 +3,4 @@
     /**
      * Line 1
-      * Line 2
-    */
+     * Line 2
+     */
```
